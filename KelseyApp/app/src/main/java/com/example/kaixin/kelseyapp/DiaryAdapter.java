package com.example.kaixin.kelseyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kaixin.kelseyapp.bean.DiaryBean;

import java.util.List;

/**
 * Created by kaixin on 2017/3/29.
 */

public class DiaryAdapter extends BaseAdapter{

    private List<DiaryBean> mlist;
    private LayoutInflater minflater;

    public DiaryAdapter(Context context, List<DiaryBean> data) {
        mlist = data;
        minflater = LayoutInflater.from(context);
    }
    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }
    @Override
    public int getCount() {
        return mlist.size();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        DiaryAdapter.ViewHolder viewHolder;
        if (convertView == null){
            viewHolder = new DiaryAdapter.ViewHolder();
            convertView = minflater.inflate(R.layout.item_diary,null);
            viewHolder.content = (TextView) convertView.findViewById(R.id.diary_content);
            viewHolder.time = (TextView)convertView.findViewById(R.id.diary_time);
            viewHolder.city = (TextView)convertView.findViewById(R.id.diary_city);
            viewHolder.weather = (TextView)convertView.findViewById(R.id.diary_weather);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (DiaryAdapter.ViewHolder)convertView.getTag();
        }
        viewHolder.content.setText(mlist.get(position).getContent());
        viewHolder.time.setText(mlist.get(position).getTime());
        viewHolder.city.setText(mlist.get(position).getCity());
        viewHolder.weather.setText(mlist.get(position).getWeather());

        return convertView;
    }
    class  ViewHolder{
        public TextView content;
        public TextView time;
        public TextView city;
        public TextView weather;
    }
}
