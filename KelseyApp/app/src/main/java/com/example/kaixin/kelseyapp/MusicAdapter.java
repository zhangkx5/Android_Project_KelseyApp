package com.example.kaixin.kelseyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kaixin.kelseyapp.bean.MusicBean;

import java.util.List;

/**
 * Created by kaixin on 2017/3/30.
 */

public class MusicAdapter extends BaseAdapter {
        private List<MusicBean> list;
        private LayoutInflater minflater;
        public MusicAdapter(Context context, List<MusicBean> list) {
            this.list = list;
            minflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int i) {
            return list.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            MusicAdapter.ViewHolder viewHolder;
            if (convertView == null){
                viewHolder = new MusicAdapter.ViewHolder();
                convertView = minflater.inflate(R.layout.item_music,null);
                viewHolder.id = (TextView) convertView.findViewById(R.id.id);
                viewHolder.title = (TextView)convertView.findViewById(R.id.title);
                viewHolder.artist = (TextView)convertView.findViewById(R.id.artist);
                viewHolder.duration = (TextView)convertView.findViewById(R.id.duration);
                viewHolder.size = (TextView)convertView.findViewById(R.id.size);
                viewHolder.url = (TextView)convertView.findViewById(R.id.url);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (MusicAdapter.ViewHolder)convertView.getTag();
            }
            viewHolder.id.setText(""+list.get(position).getId());
            viewHolder.title.setText(list.get(position).getTitle());
            viewHolder.artist.setText(list.get(position).getArtist());
            viewHolder.duration.setText(""+list.get(position).getDuration());
            viewHolder.size.setText(""+list.get(position).getSize());
            viewHolder.url.setText(list.get(position).getUrl());
            return convertView;
        }
        class ViewHolder{
            TextView id;
            TextView title;
            TextView artist;
            TextView duration;
            TextView size;
            TextView url;
        }
}
