package com.example.kaixin.kelseyapp.presenter;

import com.example.kaixin.kelseyapp.model.LoginModel;
import com.example.kaixin.kelseyapp.model.LoginModelImple;
import com.example.kaixin.kelseyapp.view.LoginView;

/**
 * Created by kaixin on 2017/3/28.
 */

public class LoginPresenterImple implements LoginPresenter {
    private LoginModel loginModel;
    private LoginView loginView;

    public LoginPresenterImple(LoginView loginView) {
        this.loginView = loginView;
        loginModel = new LoginModelImple();
    }
    public void login() {
        String name = loginView.getName();
        String password = loginView.getPassword();
        loginModel.login(name, password, this);
    }
    @Override
    public void onNameError() {
        loginView.showToast("用户名错误");
    }
    @Override
    public void onPasswordError() {
        loginView.showToast("密码错误");
    }
    @Override
    public void onSuccess() {
        loginView.showToast("登录成功");
        loginView.moveToIndex();
    }
}
