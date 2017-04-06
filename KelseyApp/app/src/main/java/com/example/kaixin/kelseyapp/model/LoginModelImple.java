package com.example.kaixin.kelseyapp.model;

import android.content.SharedPreferences;

import com.example.kaixin.kelseyapp.activity.LoginActivity;
import com.example.kaixin.kelseyapp.presenter.LoginPresenter;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by kaixin on 2017/3/28.
 */

public class LoginModelImple implements LoginModel {

    SharedPreferences pref;

    @Override
    public void login(String name, String password, final LoginPresenter loginPresenter) {
        pref = LoginActivity.getAppContext().getSharedPreferences("Login", MODE_PRIVATE);
        String userName = pref.getString("userName", "zhangkx");
        String userPassword = pref.getString("userPassword", "zhangkx");
        if (!userName.equals(name)) {
            loginPresenter.onNameError();
        } else if (!userPassword.equals(password)) {
            loginPresenter.onPasswordError();
        } else {
            loginPresenter.onSuccess();
        }
    }

}
