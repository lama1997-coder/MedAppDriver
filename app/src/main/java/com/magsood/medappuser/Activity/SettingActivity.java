package com.magsood.medappuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.Logout;
import com.magsood.medappuser.Service.ResetPassword;
import com.magsood.medappuser.Service.SettingService;
import com.magsood.medappuser.Utils.ToolbarClass;

public class SettingActivity extends ToolbarClass {
    SettingService settingService ;
    LinearLayout changePassword;


    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_setting, "الضبط");

        changePassword = findViewById(R.id.changePassword);

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ResetPasswordActivity.class);
                startActivity(intent);
            }
        });

        settingService = new SettingService();
        settingService.getDate(this);
    }
//
//    public void logout(View view) {
//        Logout logout = new Logout();
//        logout.logOut(this);
//
//    }
}