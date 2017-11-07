package jp.co.se.androidkakin.Chapter04;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/*
 * ゲームプレイ画面を表現するクラス
 * 
 */
public class PlayGame extends AppCompatActivity {
	private MainView view;
	private boolean[] pattern;
	private int players;
	private int speedUp;
	private int powerUp;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent i = getIntent();
		pattern = i.getBooleanArrayExtra("PATTERN");
		players = i.getIntExtra("PLAYERS", 0);
		speedUp = i.getIntExtra("SPEEDUP", 0);
		powerUp = i.getIntExtra("POWERUP", 0);

		view = new MainView(this, pattern, players, speedUp, powerUp);
		setContentView(view);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.item1:
			view.speedUp();
			break;

		case R.id.item2:
			view.powerUp();
			break;

		default:
			break;

		}
		return super.onOptionsItemSelected(item);
	}
}