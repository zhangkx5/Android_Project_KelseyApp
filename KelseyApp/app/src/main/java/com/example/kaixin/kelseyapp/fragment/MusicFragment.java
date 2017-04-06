package com.example.kaixin.kelseyapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.kaixin.kelseyapp.MusicActivity;
import com.example.kaixin.kelseyapp.MusicAdapter;
import com.example.kaixin.kelseyapp.MusicUtils;
import com.example.kaixin.kelseyapp.R;
import com.example.kaixin.kelseyapp.activity.MainActivity;
import com.example.kaixin.kelseyapp.bean.MusicBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kaixin on 2017/3/19.
 */

public class MusicFragment extends Fragment {
    private List<MusicBean> musicBeanList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_music, container, false);
        ListView musiclv = (ListView)view.findViewById(R.id.musiclv);
        musicBeanList = new ArrayList<>();
        musicBeanList = MusicUtils.getMusicList(MainActivity.getAppContext());
        MusicAdapter musicAdapter = new MusicAdapter(MainActivity.getAppContext(), musicBeanList);
        musiclv.setAdapter(musicAdapter);
        Button play = (Button)view.findViewById(R.id.btn_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.getAppContext(), MusicActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
