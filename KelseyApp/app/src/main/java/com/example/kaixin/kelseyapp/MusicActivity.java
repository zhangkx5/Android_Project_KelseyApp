package com.example.kaixin.kelseyapp;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;

public class MusicActivity extends AppCompatActivity {

    private Button btn_play, btn_stop,btn_quit;
    private TextView state, cTime, tTime;
    private ImageView cover;
    private SeekBar seekBar;
    private Thread mThread;
    private boolean rotate;
    private SimpleDateFormat time = new SimpleDateFormat("mm:ss");
    private MusicService ms;
    private ServiceConnection sc = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            ms = ((MusicService.MyBinder)service).getService();
        }
        @Override
        public void onServiceDisconnected(ComponentName name) {
            ms = null;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music);

        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, sc, BIND_AUTO_CREATE);

        btn_play = (Button)findViewById(R.id.btn_play);
        btn_stop = (Button)findViewById(R.id.btn_stop);
        btn_quit = (Button)findViewById(R.id.btn_quit);
        state = (TextView)findViewById(R.id.state);
        cover = (ImageView)findViewById(R.id.cover);
        cTime = (TextView)findViewById(R.id.cTime);
        tTime = (TextView)findViewById(R.id.tTime);
        seekBar = (SeekBar)findViewById(R.id.seekbar);

        final ObjectAnimator animator = ObjectAnimator.ofFloat(cover, "rotation", 0f, 360, 0f);
        animator.setDuration(36000);
        animator.setInterpolator(new LinearInterpolator());
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.RESTART);
        rotate = false;
        seekBar.setProgress(0);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ms != null) {
                    ms.play();
                    tTime.setText(time.format(ms.mp.getDuration()));
                    seekBar.setMax(ms.mp.getDuration());
                    if (ms.mp.isPlaying()) {
                        btn_play.setText("PAUSE");
                        state.setText("Playing");
                        if (animator.isPaused()) {
                            animator.resume();
                        } else {
                            animator.start();
                        }
                        mThread = new Thread(mRunnable);
                        mThread.start();
                    } else {
                        btn_play.setText("PLAY");
                        state.setText("Pause");
                        animator.pause();
                        mThread = null;
                    }
                }
            }
        });
        btn_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ms != null) {
                    ms.stop();
                    rotate = false;
                    state.setText("Stop");
                    btn_play.setText("PLAY");
                    tTime.setText("00:00");
                    cTime.setText("00:00");
                    animator.end();
                    mThread = null;
                }
            }
        });
        btn_quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.removeCallbacks(mRunnable);
                unbindService(sc);
                try {
                    MusicActivity.this.finish();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                int newPos = seekBar.getProgress();
                ms.mp.seekTo(newPos);
                cTime.setText(time.format(ms.mp.getCurrentPosition()));
            }
        });
    }
    Handler mHandler = new Handler();
    Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            while (seekBar.getProgress() < ms.mp.getDuration()-1) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                mHandler.post(new Runnable() {
                    @Override
                    public void run() {
                        seekBar.setProgress(ms.mp.getCurrentPosition());
                        cTime.setText(time.format(ms.mp.getCurrentPosition()));;
                    }
                });
            }
        }
    };
}
