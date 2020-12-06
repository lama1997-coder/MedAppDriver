package com.magsood.medappuser.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;

import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.ResetPassword;

public class ResetPasswordActivity extends AppCompatActivity {

    ResetPassword resetPassword = new ResetPassword();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_passwrod);
    }

    public void reset_password(View view) {
        resetPassword.reset(ResetPasswordActivity.this);



    }
}