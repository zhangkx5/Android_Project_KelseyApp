package com.example.kaixin.kelseyapp.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.kaixin.kelseyapp.R;
import com.example.kaixin.kelseyapp.bean.NewsBean;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by kaixin on 2017/4/6.
 */

public class NewsDetailsActivity extends Activity {
    private TextView newsTitle;
    private ImageView newsTopImg, newsTextImg0, newsTextImg1;
    private TextView newsContent;
    private TextView newsTime;
    private TextView newsSource;
    private NewsBean newsBean;
    private String newsbody = "";
    private Toolbar toolbar;
    private ImageButton ib_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        toolbar = (Toolbar)findViewById(R.id.toolbar);

        ib_back = (ImageButton) findViewById(R.id.ib_back);
        ib_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NewsDetailsActivity.this.finish();
            }
        });
        Intent intent = getIntent();
        newsBean = (NewsBean)intent.getSerializableExtra("news");
        newsTitle = (TextView)findViewById(R.id.title);
        newsTopImg = (ImageView)findViewById(R.id.topimg);
        newsTime = (TextView)findViewById(R.id.time);
        newsTextImg0 = (ImageView)findViewById(R.id.textimg0);
        newsContent = (TextView)findViewById(R.id.content);
        newsTextImg1 = (ImageView)findViewById(R.id.textimg1);
        newsSource = (TextView)findViewById(R.id.source);

        newsTitle.setText(newsBean.getNewsTitle());

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int l = Integer.parseInt(newsBean.getNewsTime());
        Date curDate = new Date(System.currentTimeMillis());
        String strDate = simpleDateFormat.format(curDate);
        newsTime.setText(strDate);
        newsSource.setText(newsBean.getNewsSource());
        Glide.with(this).load(newsBean.getTopImg()).asBitmap().into(newsTopImg);
        Glide.with(this).load(newsBean.getTextImg0()).asBitmap().into(newsTextImg0);
        Glide.with(this).load(newsBean.getTextImg1()).asBitmap().into(newsTextImg1);

        String regexbr = "<br/>|<!--(.*)-->|(&hellip)";
        String regexbf = "<b>|</b>|<font>|</font>|<strong>|</strong>";
        String regeximg = "(&nbsp;)*<img (.*)/>";
        String regexnbsp = "(&nbsp;&nbsp;&nbsp;&nbsp;)";
        String regex = "<b>|</b>";
        Pattern patbr = Pattern.compile(regexbr);
        Pattern patbf = Pattern.compile(regexbf);
        Pattern patimg = Pattern.compile(regeximg);
        Pattern patnbsp = Pattern.compile(regexnbsp);
        newsbody = newsBean.getNewsContent();
        Matcher matcherbr = patbr.matcher(newsbody);
        newsbody = matcherbr.replaceAll("");
        Matcher matcherbf = patbf.matcher(newsbody);
        newsbody = matcherbf.replaceAll("");
        Matcher matcherimg = patimg.matcher(newsbody);
        newsbody = matcherimg.replaceAll("");
        Matcher matchernbsp = patnbsp.matcher(newsbody);
        newsbody = matchernbsp.replaceAll("\n");

        newsContent.setText(newsbody);
    }
}
