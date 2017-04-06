package com.example.kaixin.kelseyapp.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kaixin.kelseyapp.R;
import com.example.kaixin.kelseyapp.presenter.LoginPresenterImple;
import com.example.kaixin.kelseyapp.view.LoginView;

/**
 * Created by kaixin on 2017/3/28.
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, LoginView{
    private EditText et_name;
    private EditText et_password;
    private Button btn_login;
    private Button btn_clean;

    private LoginPresenterImple loginPresenterImple;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initViews();
        loginPresenterImple = new LoginPresenterImple(this);
    }

    public static Context getAppContext() {
        return mContext;
    }

    private void initViews() {
        et_name = (EditText)findViewById(R.id.name);
        et_password = (EditText)findViewById(R.id.password);
        btn_clean = (Button)findViewById(R.id.clear);
        btn_login = (Button)findViewById(R.id.ok);
        btn_login.setOnClickListener(this);
        btn_clean.setOnClickListener(this);
        mContext = getApplicationContext();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ok:
                loginPresenterImple.login();
                break;
            case R.id.clear:
                break;
            default:
                break;
        }
    }
    @Override
    public void moveToIndex() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
    @Override
    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
    @Override
    public String getName() {
        return et_name.getText().toString();
    }
    @Override
    public String getPassword() {
        return et_password.getText().toString();
    }
}
