package com.example.kaixin.kelseyapp;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;

public class MusicService extends Service {

    private String src = "/data/data/K.Will-Melt.mp3";
    public static MediaPlayer mp = new MediaPlayer();
    public final IBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        MusicService getService() {
            return MusicService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    public MusicService() {
        try {
            mp.setDataSource(src);
            mp.prepare();
            mp.setLooping(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void onCreate() {
        super.onCreate();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mp != null) {
            mp.stop();
            mp.release();
        }
    }
    public void play() {
        if (mp != null) {
            if (mp.isPlaying()) {
                mp.pause();
            } else {
                mp.start();
            }
        } else {
            try {
                mp.setDataSource("/data/K.Will-Melt.mp3");
                mp.prepare();
                mp.seekTo(0);
                mp.setLooping(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public void stop() {
        if (mp != null) {
            mp.stop();
            try {
                mp.reset();
                mp.setDataSource("/data/K.Will-Melt.mp3");
                mp.prepare();
                mp.seekTo(0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
