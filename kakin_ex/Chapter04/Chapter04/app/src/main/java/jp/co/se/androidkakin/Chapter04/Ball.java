package jp.co.se.androidkakin.Chapter04;

/*
 * ボールを表現するクラス
 * 
 */
public class Ball {
	private float x;
	private float y;
	private float speed;
	private int dir_x;
	private int dir_y;
	private int status;

	// コンストラクタ
	public Ball(float x, float y) {
		this.x = x;
		this.y = y;
		this.dir_x = Utils.BALL_DIRECTION_RIGHT;
		this.dir_y = Utils.BALL_DIRECTION_TOP;
		this.speed = 1.0f;
		this.status = Utils.BALL_STATUS_STOP;
	}
	
	// ボールがX軸に進む方向を取得する
    public int getDirectionX() {
		return this.dir_x;
	}
	
	// ボールがX軸に進む方向を設定する
    public void setDirectionX(int dir_x)
    {
		this.dir_x = dir_x;
	}
	
	// ボールがY軸に進む方向を取得する
    public int getDirectionY() {
		return this.dir_y;
	}

	// ボールがY軸に進む方向を設定する
    public void setDirectionY(int dir_y) {
		this.dir_y = dir_y;
	}
	
	// ボールのX座標値を取得する
    public float getX() {
		return this.x;
	}
	
	// ボールのX座標値を設定する
    public void setX(float dx) {
		this.x += dx * speed;
	}

	// ボールのY座標値を取得する
    public float getY() {
		return this.y;
	}
	
	// ボールのY座標値を設定する
    public void setY(float dy) {
		this.y += dy * speed;
	}
	
	// ボールの移動スピードを取得する
    public float getSpeed() {
		return this.speed;
	}
	
	// ボールの移動スピードを設定する
    public void setSpeed(float speed) {
		this.speed = speed;
	}
	
	// ボールの状態を取得する
    public int getStatus() {
		return status;
	}
	
	// ボールの状態を設定する
    public void setStatus(int status) {
		this.status = status;
	}

    // ボールを移動させる
	public boolean move(int width, int height)
	{
		// 引数チェック
        if (width <= 0 || height <= 0) {
            return false;
        }

		/*
		 * ボールの進行方向を設定する(X軸方向)
		 */
		// ボールの現在位置が左端の場合、ボールの進行方向を右向きに設定する
		if (this.getX() <= 0) {
			this.setDirectionX(Utils.BALL_DIRECTION_RIGHT);

		// ボールの現在位置が右端にあれば、ボールの進行方向を左向きに設定する
		} else if (this.getX() >= width) {
			this.setDirectionX(Utils.BALL_DIRECTION_LEFT);

		} else {
			// do nothing
		}

		/*
		 * ボールを移動させる(X軸方向)
		 */
		// ボールの進行方向が右向きの場合、X軸方向に移動量を加算する
		if (this.getDirectionX() == Utils.BALL_DIRECTION_RIGHT) {
			this.setX(Utils.BALL_DX);

		// ボールの進行方向が左向きの場合、X軸方向に移動量を減算する
		} else if (this.getDirectionX() == Utils.BALL_DIRECTION_LEFT) {
			this.setX(-Utils.BALL_DX);

		} else {
			// do nothing
		}

		/*
		 * ボールの進行方向を設定する(X軸方向)
		 */
		// ボールの現在位置が一番上の場合、ボールの進行方向を下向きに設定する
		if (this.getY() <= 0) {
			this.setDirectionY(Utils.BALL_DIRECTION_BOTTOM); // to bottom

		// ボールの現在位置が一番下の場合、ボールの進行方向を上向きに設定する
		} else if (this.getY() >= height) {
			this.setDirectionY(Utils.BALL_DIRECTION_TOP); // to top

		} else {
			// do nothing
		}

		/*
		 * ボールを移動させる(Y軸方向)
		 */
		// ボールの進行方向が下向きの場合、Y軸方向に移動量を加算する
		if (this.getDirectionY() == Utils.BALL_DIRECTION_BOTTOM) {
			this.setY(Utils.BALL_DY);

		// ボールの進行方向が上向きの場合、Y軸方向に移動量を減算する
		} else if (this.getDirectionY() == Utils.BALL_DIRECTION_TOP) {
			this.setY(-Utils.BALL_DY);

		} else {
			// do nothing
		}

        return true;
	}
	
	// ボールを初期状態にする
	public void reset(float x, float y) {
		this.x = x;
		this.y = y;
		this.dir_x = Utils.BALL_DIRECTION_RIGHT;
		this.dir_y = Utils.BALL_DIRECTION_TOP;
	}
}
