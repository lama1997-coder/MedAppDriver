package com.magsood.medappuser.Activity;

import android.os.Bundle;
import android.view.View;

import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.LoginService;
import com.magsood.medappuser.Utils.ToolbarClass;

public class Login extends ToolbarClass {
    LoginService loginService = new LoginService();


    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_login, "تسجيل الدخول");
    }
    public void login(View view) {

        loginService.sendDate(this);


    }
}