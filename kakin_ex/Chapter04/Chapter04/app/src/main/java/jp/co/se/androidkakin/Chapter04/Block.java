package jp.co.se.androidkakin.Chapter04;

/*
 * ブロックを表現するクラス
 * 
 */
public class Block {
	private float x;
	private float y;
	private float dx;
	private float dy;
	private boolean status;

	// コンストラクタ
	public Block(float x, float y, float dx, float dy, int cr, int cb, int cg, boolean status) {
		this.x = x;
		this.y = y;
		this.dx = x + dx;
		this.dy = y + dy;
		this.status = status;
	}

	// ブロックの座標位置を設定する
	public void setCoordinate(float x, float y) {
		this.x = x;
		this.y = y;
	}

	// ブロックの状態を設定する
	public void setStatus(boolean status) {
		this.status = status;
	}

	// ブロックの状態を取得する
	public boolean getStatus() {
		return this.status;
	}

	// ブロックのX座標を取得する
	public float getX() {
		return this.x;
	}
	
	// ブロックのY座標を取得する
	public float getY() {
		return this.y;
	}
	
	// ブロックの横幅を取得する
	public float getDx() {
		return this.dx;
	}
	
	// ブロックの高さを取得する
	public float getDy() {
		return this.dy;
	}
}
