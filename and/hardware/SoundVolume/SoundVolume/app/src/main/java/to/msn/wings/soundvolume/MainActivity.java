package to.msn.wings.soundvolume;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnPlay;
    AudioManager am;
    MediaPlayer mp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        am = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        am.setStreamVolume(AudioManager.STREAM_MUSIC, 5, 0);

        SeekBar sb = (SeekBar) findViewById(R.id.seek);
        sb.setOnSeekBarChangeListener(
                new SeekBar.OnSeekBarChangeListener() {
                    public void onProgressChanged(SeekBar sb, int progress,
                        boolean fromUser) {
                        am.setStreamVolume(AudioManager.STREAM_MUSIC, progress, 0);
                    }

                    public void onStartTrackingTouch(SeekBar sb) { }

                    public void onStopTrackingTouch(SeekBar sb) { }
                }
        );

        btnPlay = (Button) findViewById(R.id.btnPlay);
        mp = MediaPlayer.create(this, R.raw.sound);
        mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            public void onCompletion(MediaPlayer mp) {
                btnPlay.setText("再生");
            }
        });
    }

    public void btnPlay_onClick(View view) {
        if (!mp.isPlaying()) {
            mp.start();
            btnPlay.setText("停止");
        } else {
            try {
                mp.stop();
                mp.prepare();
            } catch (IllegalStateException | IOException e) {
                e.printStackTrace();
            }
            btnPlay.setText("再生");
        }
    }
}
