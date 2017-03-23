package com.example.kaixin.kelseyapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kaixin on 2017/3/21.
 */

public class NewsAdapter extends BaseAdapter{
    private List<NewsBean> mlist;
    private LayoutInflater minflater;

    public NewsAdapter(Context context, List<NewsBean> data) {
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
        ViewHolder viewHolder= null;
        if (convertView == null){
            viewHolder = new ViewHolder();
            convertView = minflater.inflate(R.layout.item_news,null);
            viewHolder.iconimage = (ImageView)convertView.findViewById(R.id.newsImg);
            viewHolder.title = (TextView)convertView.findViewById(R.id.newsTitle);
            viewHolder.content = (TextView)convertView.findViewById(R.id.newsDetails);
            convertView.setTag(viewHolder);
        }else{
            viewHolder = (ViewHolder)convertView.getTag();
        }
        //viewHolder.iconimage.setBackgroundResource(R.mipmap.ic_launcher);
        viewHolder.title.setText(mlist.get(position).newsTitle);
        viewHolder.content.setText(mlist.get(position).newsContnent);
        //通过setTag方法让iv上存储当前图片的下载网址
        viewHolder.iconimage.setTag(mlist.get(position).newsIconUrl);
        viewHolder.iconimage.setImageResource(R.mipmap.ic_launcher);
        new ImageTask(viewHolder.iconimage, mlist.get(position).newsIconUrl).execute(mlist.get(position).newsIconUrl);

        return convertView;
    }
    class  ViewHolder{
        public TextView title;
        public ImageView iconimage;
        public TextView content;
    }

}
