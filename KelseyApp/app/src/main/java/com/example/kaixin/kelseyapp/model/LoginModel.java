package com.example.kaixin.kelseyapp.model;

import com.example.kaixin.kelseyapp.presenter.LoginPresenter;

/**
 * Created by kaixin on 2017/3/28.
 */

public interface LoginModel {
    void login(String name, String password, LoginPresenter loginPresenter);
}
