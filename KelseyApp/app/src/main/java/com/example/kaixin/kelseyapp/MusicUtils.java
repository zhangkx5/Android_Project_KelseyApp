package com.example.kaixin.kelseyapp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import com.example.kaixin.kelseyapp.activity.MainActivity;
import com.example.kaixin.kelseyapp.bean.MusicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaixin on 2017/3/30.
 */

public class MusicUtils {
    private static final Uri albumArtUri = Uri.parse("content://media/external/audio/albumart");
    public static List<MusicBean> getMusicList(Context context) {
        Cursor cursor = MainActivity.getAppContext().getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null, null,
                MediaStore.Audio.Media.DEFAULT_SORT_ORDER);
        List<MusicBean> musicBeanList = new ArrayList<MusicBean>();
        for (int i = 0; i < cursor.getCount(); i++) {
            MusicBean musicBean = new MusicBean();
            cursor.moveToNext();
            long id = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media._ID));
            String title = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.TITLE));
            String artist = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST));
            long duration = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
            long size = cursor.getLong(cursor.getColumnIndex(MediaStore.Audio.Media.SIZE));
            String url = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.DATA));
            int isMusic = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.IS_MUSIC));

            if (isMusic != 0) {
                musicBean.setId(id);
                musicBean.setTitle(title);
                musicBean.setArtist(artist);
                musicBean.setDuration(duration);
                musicBean.setSize(size);
                musicBean.setUrl(url);
                musicBeanList.add(musicBean);
            }
        }
        return musicBeanList;
    }
}