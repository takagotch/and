package jp.co.se.androidkakin.Chapter04;

/*
 * ラケットを表現するクラス
 * 
 */
public class Racket {
	private float x; // ラケットのX座標
	private float y; // ラケットのY座標
	private float dx; // ラケットの幅
	private float dy; // ラケットの高さ
	
	public Racket(float x, float y, float dx, float dy) {
		this.x = x;
		this.y = y;
		this.dx = dx;
		this.dy = dy;
	}
	
	public float getX() {
		return this.x;
	}
	
	public float getY() {
		return this.y;
	}
	
	public void setX(float x) {
		this.x = x;
	}
	
	public void setY(float y) {
		this.y = y;
	}
	
	public float getDx() {
		return this.dx;
	}
	
	public void setDx(float dx) {
		this.dx = dx;
	}
	
	public float getDy() {
		return this.dy;
	}
	
	public void setDy(float dy) {
		this.dy = dy;
	}
	
	public void powerUp() {
		this.dx = dx * 2.0f;
	}

	// ラケットがボールと接触しているかを判定する
	public boolean isContactWithBall (Ball ball)
	{
		// ボールがラケットの幅、高さに収まっていればTRUEを返す
		if ((this.getX() < ball.getX() && ball.getX() < this.getX()+this.getDx())
				&& (ball.getY() < this.getY() && ball.getY() > this.getY()-(10*ball.getSpeed()))) {
			return true;
		}
		return false;
	}
}
