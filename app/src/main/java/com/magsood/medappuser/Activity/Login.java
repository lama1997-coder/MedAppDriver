package com.magsood.medappuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.LoginService;
import com.magsood.medappuser.Service.ResetPassword;
import com.magsood.medappuser.Utils.ToolbarClass;

public class Login extends ToolbarClass {
    TextView forgetPassword;
    LoginService loginService = new LoginService();
    ResetPassword resetPassword = new ResetPassword();


    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_login, "تسجيل الدخول");

        forgetPassword = findViewById(R.id.forget_password);


        forgetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                resetPassword.forgetPassword(Login.this);



            }
        });
    }


    public void login(View view) {

        loginService.sendDate(this);


    }

    public void toReg(View view) {
        Intent intent = new Intent(getApplicationContext(),Register.class);
        startActivity(intent);
    }
}