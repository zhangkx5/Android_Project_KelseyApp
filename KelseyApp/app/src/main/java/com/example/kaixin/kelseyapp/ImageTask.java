package com.example.kaixin.kelseyapp;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by kaixin on 2017/3/22.
 */

public class ImageTask extends AsyncTask<String, Void, Bitmap> {
    private ImageView imageView;
    private String url;
    public ImageTask(ImageView mimageView, String murl) {
        url = murl;
        imageView = mimageView;
    }
    @Override
    protected Bitmap doInBackground(String... params) {
        return getBitmapFromURL(params[0]);
    }
    @Override
    protected void onPostExecute(Bitmap bitmap) {
        super.onPostExecute(bitmap);
        if (imageView.getTag() != null && imageView.getTag().equals(url)) {
            imageView.setImageBitmap(bitmap);
        }
    }

    public Bitmap getBitmapFromURL(String stringUrl) {

        Bitmap bitmap = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        HttpURLConnection connection = null;
        URL url1 = null;
        try {
            url1 = new URL(stringUrl);
            connection = (HttpURLConnection) url1.openConnection();
            is = connection.getInputStream();
            bis = new BufferedInputStream(is);
            bitmap = BitmapFactory.decodeStream(bis);
            connection.disconnect();
            return bitmap;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
