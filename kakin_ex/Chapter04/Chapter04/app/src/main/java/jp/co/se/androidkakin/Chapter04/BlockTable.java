package jp.co.se.androidkakin.Chapter04;

import java.util.ArrayList;

/*
 * ブロックテーブルを表現するクラス
 */
public class BlockTable {
	// ブロックテーブルを表現するArrayList
	private ArrayList<Block> blocks;
	
	// ブロックテーブル上に表示されるブロック数
	private int availableBlockNum;
	
	// コンストラクタ
	public BlockTable(boolean pattern[], int width, int height) {
		blocks = new ArrayList<Block>();
		availableBlockNum = 0;
		
		int block_width = width / (Utils.BLOCK_COL_NUM + 2); // ブロック1つ分の横幅を計算する
		int block_height = height / 15; // ブロック1つ分の高さを計算する
		
		for (int i=0; i<Utils.BLOCK_ROW_NUM; i++) {
			for (int j=0; j<Utils.BLOCK_COL_NUM; j++) {
				blocks.add(new Block(block_width+(j*block_width), block_height+(i*block_height), block_width, block_height, 0, 255, 0, pattern[i*Utils.BLOCK_COL_NUM+j]));
				if (pattern[i*Utils.BLOCK_COL_NUM+j]) {
					availableBlockNum++; // ブロックテーブル上に表示されるブロック数を数える
				}
			}
		}
	}
	
	// ブロックを取得する
	public Block getBlock(int number) {
		return blocks.get(number);
	}
	
	// ブロック数を取得する
	public int getSize() {
		return blocks.size();
	}

	// ボールを設定する
	public void setBall(Ball ball) {
		for (int j=0; j<blocks.size(); j++) {
			Block block = blocks.get(j);
			
			if (block.getStatus() == false) {
				continue;
			}
			
			// ボールがブロックの底辺に当たった場合
			if ((block.getX() < ball.getX() && ball.getX() < block.getDx()) && ((ball.getY() < block.getDy()+10) && (ball.getY() > block.getDy()))) {
				// ボールのY軸進行方向が上向きだった場合は下向きに設定する
				if (ball.getDirectionY() == Utils.BALL_DIRECTION_TOP) {
					ball.setDirectionY(Utils.BALL_DIRECTION_BOTTOM);
				// ボールのY軸進行方向が下向きだった場合は上向きに設定する
				} else {
					ball.setDirectionY(Utils.BALL_DIRECTION_TOP);
				}

				// ブロックを非表示にする
				block.setStatus(false);

				// ブロックの有効数を1つ減らす
				availableBlockNum--;
			}
			// ボールがブロックの上辺に当たった場合
			else if ((block.getX() < ball.getX() && ball.getX() < block.getDx()) && ((ball.getY() > block.getY()-10) && (ball.getY() < block.getY()))) {
				// ボールのY軸進行方向が上向きだった場合は下向きに設定する
				if (ball.getDirectionY() == Utils.BALL_DIRECTION_TOP) {
					ball.setDirectionY(Utils.BALL_DIRECTION_BOTTOM);
				// ボールのY軸進行方向が下向きだった場合は上向きに設定する
				} else {
					ball.setDirectionY(Utils.BALL_DIRECTION_TOP);
				}

				// ブロックを非表示にする
				block.setStatus(false);

				// ブロックの有効数を1つ減らす
				availableBlockNum--;
			}
			// ボールがブロックの右辺に当たった場合
			else if ((block.getDx() < ball.getX() && ball.getX() < block.getDx()+10) && ((ball.getY() > block.getY()) && (ball.getY() < block.getDy()))) {
				// ボールのX軸進行方向が右向きだった場合は左向きに設定する
				if (ball.getDirectionX() == Utils.BALL_DIRECTION_RIGHT) {
					ball.setDirectionX(Utils.BALL_DIRECTION_LEFT);
				// ボールのX軸進行方向が左向きだった場合は右向きに設定する
				} else {
					ball.setDirectionX(Utils.BALL_DIRECTION_RIGHT);
				}

				// ブロックを非表示にする
				block.setStatus(false);

				// ブロックの有効数を1つ減らす
				availableBlockNum--;
			}
			// ボールがブロックの左辺に当たった場合
			else if ((block.getX()-10 < ball.getX() && ball.getX() < block.getX()) && ((ball.getY() > block.getY()) && (ball.getY() < block.getDy()))) {
				// ボールのX軸進行方向が右向きだった場合は左向きに設定する
				if (ball.getDirectionX() == Utils.BALL_DIRECTION_RIGHT) {
					ball.setDirectionX(Utils.BALL_DIRECTION_LEFT);
				// ボールのX軸進行方向が左向きだった場合は右向きに設定する
				} else {
					ball.setDirectionX(Utils.BALL_DIRECTION_RIGHT);
				}

				// ブロックを非表示にする
				block.setStatus(false);

				// ブロックの有効数を1つ減らす
				availableBlockNum--;
			}
		}
	}
	
	// 残っているブロック数を取得する
	public int getAvailableBlockNum() {
		return availableBlockNum;
	}
}
