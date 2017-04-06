package com.example.kaixin.kelseyapp.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kaixin.kelseyapp.NewsAdapter;
import com.example.kaixin.kelseyapp.R;
import com.example.kaixin.kelseyapp.activity.MainActivity;
import com.example.kaixin.kelseyapp.activity.NewsDetailsActivity;
import com.example.kaixin.kelseyapp.bean.NewsBean;

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
import java.util.List;


/**
 * Created by kaixin on 2017/3/19.
 */

public class NewsSubFragment extends Fragment {

    public static final int NEWS_TYPE_TOP = 0;
    public static final int NEWS_TYPE_AMUSE = 1;
    public static final int NEWS_TYPE_MILITARY = 2;
    public static final int NEWS_TYPE_TECHNOLOGY = 3;
    public static final int NEWS_TYPE_ECONOMICS = 4;

    private ListView listView;
    private NewsAdapter newsAdapter;
    String TAG = "abace";
    private int NewsType = 0;
    private static int i = 0;
    private String URLs ="http://api.dagoogle.cn/news/get-news?tableNum=1&pagesize=10";
    //private String URLs =" http://www.imooc.com/api/teacher?type=4&num=30";

    public static NewsSubFragment newInstance(int type) {
        Bundle args = new Bundle();
        NewsSubFragment fragment = new NewsSubFragment();
        args.putInt("type", type);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewsType = getArguments().getInt("type");
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // TODO Auto-generated method stub  
        View view = inflater.inflate(R.layout.subfragment_jokes, null);
        listView = (ListView)view.findViewById(R.id.newslv);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                NewsBean news = newsAdapter.getItem(i);
                Intent intent = new Intent(MainActivity.getAppContext(), NewsDetailsActivity.class);
                intent.putExtra("news", news);
                startActivity(intent);
            }
        });
        switch(NewsType) {
            case NEWS_TYPE_TOP:
                URLs ="http://api.dagoogle.cn/news/get-news?tableNum=1&pagesize=10";
                break;
            case NEWS_TYPE_AMUSE:
                URLs ="http://api.dagoogle.cn/news/get-news?tableNum=2&pagesize=10";
                break;
            case NEWS_TYPE_MILITARY:
                URLs ="http://api.dagoogle.cn/news/get-news?tableNum=3&pagesize=10";
                break;
            case NEWS_TYPE_TECHNOLOGY:
                URLs ="http://api.dagoogle.cn/news/get-news?tableNum=4&pagesize=10";
                break;
            case NEWS_TYPE_ECONOMICS:
                URLs ="http://api.dagoogle.cn/news/get-news?tableNum=5&pagesize=10";
                break;
            default:
                URLs ="http://api.dagoogle.cn/news/get-news?tableNum=1&pagesize=10";
                break;
        }
        //i++;
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
                    newsBean.setNewsId(jsonObject.getString("news_id"));
                    newsBean.setNewsTitle(jsonObject.getString("title"));
                    newsBean.setTopImg(jsonObject.getString("top_image"));
                    newsBean.setTextImg0(jsonObject.getString("text_image0"));
                    newsBean.setTextImg1(jsonObject.getString("text_image1"));
                    newsBean.setNewsSource(jsonObject.getString("source"));
                    newsBean.setNewsContent(jsonObject.getString("content"));
                    newsBean.setNewsDigest(jsonObject.getString("digest"));
                    newsBean.setNewsTime(jsonObject.getString("edit_time"));
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
            newsAdapter = new NewsAdapter(NewsSubFragment.this.getActivity(),nesBeanList);
            listView.setAdapter(newsAdapter);
        }
    }
}
