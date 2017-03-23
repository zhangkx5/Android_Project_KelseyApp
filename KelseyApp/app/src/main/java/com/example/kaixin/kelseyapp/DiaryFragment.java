package com.example.kaixin.kelseyapp;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by kaixin on 2017/3/19.
 */

public class DiaryFragment extends Fragment {
    String url = "http://japi.juhe.cn/joke/content/text.from";
    String apikey = "e0e928f4afcc0997f574aece6c351bde";
    private ListView diarylv;
    private Context mContext;
    private ArrayList<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_diary, container, false);
        diarylv = (ListView)view.findViewById(R.id.diarylv);
        new JokesAsyncTask().execute(url);
        return view;
    }
    private List<JokesBean> getJsonData(String url) {
        List<JokesBean> jokesBeanList = new ArrayList<>();
        Map params = new HashMap();
            params.put("page", "");
            params.put("pagesize", "15");
            params.put("key", apikey);
        url = url+"?"+urlencode(params);
        try {
            String jsonString = readStream(new URL(url).openStream());
            JSONObject jsonObject;
            JokesBean jokesBean;
            try {
                jsonObject = new JSONObject(jsonString);
                jsonObject = jsonObject.getJSONObject("result");
                Log.d("JOKES", jsonString);
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    jsonObject = jsonArray.getJSONObject(i);
                    jokesBean = new JokesBean();
                    jokesBean.jokesPtime = jsonObject.getString("updatetime");
                    jokesBean.jokesContent = jsonObject.getString("content");
                    jokesBeanList.add(jokesBean);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jokesBeanList;
    }
    private String readStream(InputStream is) {
        InputStreamReader isr;
        String result = "";
        try {
            String line = "";
            isr = new InputStreamReader(is, "utf-8");
            BufferedReader br = new BufferedReader(isr);
            while ((line = br.readLine()) != null) {
                result += line;
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String urlencode(Map<String, Object> data) {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry i : data.entrySet()) {
            try {
                sb.append(i.getKey()).append("=")
                        .append(URLEncoder.encode(i.getValue()+"", "UTF-8")).append("&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString();
    }

    class JokesAsyncTask extends AsyncTask<String, Void, List<JokesBean>> {
        @Override
        protected List<JokesBean> doInBackground(String... params) {
            return getJsonData(params[0]);
        }

        @Override
        protected void onPostExecute(List<JokesBean> jokesBeanList) {
            super.onPostExecute(jokesBeanList);
            JokesAdapter jokesAdapter = new JokesAdapter(DiaryFragment.this.getActivity().getApplicationContext(), jokesBeanList);
            diarylv.setAdapter(jokesAdapter);
        }
    }
}
