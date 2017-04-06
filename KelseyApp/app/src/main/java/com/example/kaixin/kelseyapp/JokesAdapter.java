package com.example.kaixin.kelseyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.kaixin.kelseyapp.bean.JokesBean;

import java.util.List;

/**
 * Created by kaixin on 2017/3/21.
 */

public class JokesAdapter extends BaseAdapter {
    private List<JokesBean> mlist;
    private LayoutInflater mInflater;

    public JokesAdapter(Context context, List<JokesBean> data) {
        mlist = data;
        mInflater = LayoutInflater.from(context);
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
    public long getItemId(int positoin) {
        return positoin;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_jokes,null);
            viewHolder.ptime = (TextView)convertView.findViewById(R.id.ptime);
            viewHolder.content = (TextView)convertView.findViewById(R.id.content);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        viewHolder.ptime.setText(mlist.get(position).jokesPtime);
        viewHolder.content.setText(mlist.get(position).jokesContent);
        return convertView;

    }

    class ViewHolder {
        public TextView content;
        public TextView ptime;
    }
}
