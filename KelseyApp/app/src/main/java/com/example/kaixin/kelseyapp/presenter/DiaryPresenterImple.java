package com.example.kaixin.kelseyapp.presenter;

import com.example.kaixin.kelseyapp.activity.DiaryActivity;
import com.example.kaixin.kelseyapp.model.DiaryModel;
import com.example.kaixin.kelseyapp.model.DiaryModelImple;
import com.example.kaixin.kelseyapp.view.DiaryView;

/**
 * Created by kaixin on 2017/3/30.
 */

public class DiaryPresenterImple {
    private DiaryModel diaryModel;
    private DiaryView diaryView;

    public DiaryPresenterImple(DiaryView diaryView) {
        this.diaryView = diaryView;
        diaryModel = new DiaryModelImple();
    }

    public void addDiary(boolean Update, String filename, String strDate, String strCity, String strWeather, String strContent) {
        if (strContent.equals("")) {
            diaryView.showToast("亲，你还没写日记哦..");
        } else {
            diaryModel.openDiaryFile(filename, strContent);
            if (!Update) {
                diaryModel.addDiaryDB(filename, strDate, strCity, strWeather);
            }
        }
    }

}
