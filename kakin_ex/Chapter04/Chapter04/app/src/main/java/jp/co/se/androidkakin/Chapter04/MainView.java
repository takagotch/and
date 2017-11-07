package jp.co.se.androidkakin.Chapter04;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/*
 * ゲーム画面を表現するSurfaceView
 * 
 */
public class MainView extends SurfaceView implements SurfaceHolder.Callback, Runnable {
	private Context context;
	private Paint paint;
	private SurfaceHolder holder;
	private Thread thread;

	// 画面サイズ
	private int screen_width;
	private int screen_height;

	private String player_status = "";

	// ball
	private Ball ball = null;
	
	// racket
	private Racket racket = null;

	// blocks
	private BlockTable blocks = null;

	// players
	private int players;
	
	// speedup
	private int speedUpNum;

	// powerup
	private int powerUpNum;
	
	// pattern
	private boolean pattern[];
	
	// コンストラクタ
	public MainView(Context context, boolean pattern[], int players, int speedUpNum, int powerUpNum) {
		super(context);
		
		this.context = context;
		holder = null;
		thread = null;
		paint = new Paint();
		paint.setAntiAlias(true);

		this.players = players;
		this.speedUpNum = speedUpNum;
		this.powerUpNum = powerUpNum;
		this.pattern = pattern;
		
		// get to the SurfaceHolder, and regist to the callback.
		getHolder().addCallback(this);
	}

	@Override
	public void run() {
		Canvas canvas = null;
		while (thread != null) {
			canvas = holder.lockCanvas();
			if (canvas == null) break;

			// draw canvas
			canvas.drawColor(Color.argb(Utils.CANVAS_COLOR_A, Utils.CANVAS_COLOR_R, Utils.CANVAS_COLOR_G, Utils.CANVAS_COLOR_B));
			
			if (ball.getStatus() == Utils.BALL_STATUS_START) {

				// ボールを移動させる
				ball.move(screen_width, screen_height);

				// ラケットがボールと接触した場合
				if (racket.isContactWithBall(ball)) {
					if (ball.getDirectionY() == Utils.BALL_DIRECTION_TOP) {
						ball.setDirectionY(Utils.BALL_DIRECTION_BOTTOM);
					} else {
						ball.setDirectionY(Utils.BALL_DIRECTION_TOP);
					}

				// ボールを取り損ねた場合
				} else if (ball.getY() > racket.getY()+Utils.GAMEOVER_BORDER_LINE) {
					// ボールを停止する
					ball.setStatus(Utils.BALL_STATUS_STOP);
					
					// 残機を減らす
					if (players > 0) {
						players--;
					}
				}

				// ブロックテーブルにボール情報を反映させる
				blocks.setBall(ball);
				
				paint.setColor(Color.argb(Utils.BALL_COLOR_A, Utils.BALL_COLOR_R, Utils.BALL_COLOR_G, Utils.BALL_COLOR_B));
				canvas.drawCircle(ball.getX(), ball.getY(), Utils.BALL_RADIUS, paint);
			}
			
			drawRacket(canvas);
			drawBlocks(canvas);
			drawPlayerStatus(canvas);
			
			holder.unlockCanvasAndPost(canvas);
		}
	}
	
	// プレイヤー状態を表示する
	public void drawPlayerStatus(Canvas canvas) {
		paint.setTextSize(24);
		paint.setColor(Color.argb(255, 255, 255, 255));

		// ステージクリア判定
		if (blocks.getAvailableBlockNum() == 0) {
			player_status = "STAGE CLEAR!";
		// ゲームオーバー判定
		} else if (players > 0) {
			player_status = "PLAYERS: " + players + "  " + "SPEED UP: " + speedUpNum + "   " + "POWER UP: " + powerUpNum; 
		} else {
			player_status = "GAMEOVER";
		}

		canvas.drawText(player_status, 0, 32, paint);
	}
	
	// ラケットを表示する
	public void drawRacket(Canvas canvas) {
		paint.setColor(Color.argb(Utils.RACKET_COLOR_A, Utils.RACKET_COLOR_R, Utils.RACKET_COLOR_G, Utils.RACKET_COLOR_B));
		
		canvas.drawRect(racket.getX(), racket.getY(), racket.getX()+racket.getDx(), racket.getY()+racket.getDy(), paint);
	}
	
	// ブロックを表示する
	public void drawBlocks(Canvas canvas) {
		for (int i=0; i<blocks.getSize(); i++) {
			Block block = blocks.getBlock(i);
			if (block.getStatus() == true) {
				paint.setColor(Color.argb(Utils.BLOCK_COLOR_A, Utils.BLOCK_COLOR_R, Utils.BLOCK_COLOR_G, Utils.BLOCK_COLOR_B));
				canvas.drawRect(block.getX(), block.getY(), block.getDx(), block.getDy(), paint);
			}
		}
	}

	public void speedUp() {
		if (ball != null && speedUpNum > 0) {
			ball.setSpeed(Utils.BALL_SPEED_TIMES);
			speedUpNum--;
		}
	}
	
	public void powerUp() {
		if (racket != null && powerUpNum > 0) {
			racket.powerUp();
			powerUpNum--;
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,	int height) {
		if (thread != null) {
			this.screen_width = width;
			this.screen_height = height;

			// ラケットの初期位置
			int rect_left = width/2 - Utils.RACKET_WIDTH/2;
			int rect_top = height - Utils.RACKET_INIT_POS_Y - Utils.RACKET_HEIGHT/2;
			racket = new Racket(rect_left, rect_top, Utils.RACKET_WIDTH, Utils.RACKET_HEIGHT);

			blocks = new BlockTable(pattern, width, height);
			
			ball = new Ball(racket.getX()+Utils.RACKET_WIDTH/2, racket.getY()+Utils.BALL_INIT_POS_Y);

			thread.start();
		}
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		this.holder = holder;
		thread = new Thread(this);
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if (thread != null) {
			thread = null;

			// 補助アイテムごとの購入数をデータベースに反映
			Database db = new Database(this.context);
	        db.updatePurchasedItem("item_001", speedUpNum);
	        db.updatePurchasedItem("item_002", powerUpNum);
			db.updatePurchasedItem("item_003", players);
	        db.close();
		}
	}
	
	public boolean onTouchEvent(MotionEvent event) {
		// タッチパネルを押したとき
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			if (ball.getStatus() == Utils.BALL_STATUS_STOP && players > Utils.GAMEOVER_PLAYERS_NUM) {
				ball.reset(racket.getX()+Utils.RACKET_WIDTH/2, racket.getY()+Utils.BALL_INIT_POS_Y);
				ball.setStatus(Utils.BALL_STATUS_START);
			}

		// タッチパネルを移動したとき
		} else if (event.getAction() == MotionEvent.ACTION_MOVE) {
			// ラケットを移動する
			racket.setX(event.getX() - racket.getDx()/2);
		}
		
		return true;
	}
}
