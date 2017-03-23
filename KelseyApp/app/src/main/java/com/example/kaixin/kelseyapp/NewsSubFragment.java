package com.example.kaixin.kelseyapp;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by kaixin on 2017/3/19.
 */

public class NewsSubFragment extends Fragment {
    private ListView listView;
    String TAG = "abace";
    private String URLs ="http://api.dagoogle.cn/news/get-news?tableNum=1&pagesize=10";
    //private String URLs =" http://www.imooc.com/api/teacher?type=4&num=30";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub  
        View view = inflater.inflate(R.layout.subfragment_jokes, null);
        listView = (ListView)view.findViewById(R.id.newslv);

        //NewsAdapter newsAdapter = new NewsAdapter(getActivity(),getJsonData(URLs));
        //listView.setAdapter(newsAdapter);
        new NewsAsyncTask().execute(URLs);
        return view;
    }
    private List<NewsBean> getJsonData(String url){
        List<NewsBean> nesBeanList = new ArrayList<>();

        try {
            String jsonString = readStream(new URL(url).openConnection().getInputStream());
            Log.d(TAG, jsonString);
            JSONObject jsonObject;
            NewsBean newsBean;
            try {
                jsonObject = new JSONObject(jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0 ; i <jsonArray.length(); i++ ){
                    jsonObject = jsonArray.getJSONObject(i);
                    newsBean = new NewsBean();
                    newsBean.newsIconUrl = jsonObject.getString("top_image");
                    newsBean.newsTitle = jsonObject.getString("title");
                    newsBean.newsContnent = jsonObject.getString("source");
                    /*newsBean.newsIconUrl = jsonObject.getString("picSmall");
                    newsBean.newsTitle = jsonObject.getString("name");
                    newsBean.newsContnent = jsonObject.getString("description");*/
                    nesBeanList.add(newsBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return nesBeanList;
    }
    private String readStream(InputStream is){
        InputStreamReader isr;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is,"utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line=br.readLine()) != null ){
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    class NewsAsyncTask extends AsyncTask<String,Void,List<NewsBean>>{
        @Override
        protected List<NewsBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }
        @Override
        protected void onPostExecute(List<NewsBean>nesBeanList) {
            super.onPostExecute(nesBeanList);
            NewsAdapter newsAdapter = new NewsAdapter(getActivity(),nesBeanList);
            listView.setAdapter(newsAdapter);
        }
    }
}
