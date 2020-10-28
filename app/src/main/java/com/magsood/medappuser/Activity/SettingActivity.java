package com.magsood.medappuser.Activity;

import android.os.Bundle;
import android.view.View;

import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.Logout;
import com.magsood.medappuser.Service.SettingService;
import com.magsood.medappuser.Utils.ToolbarClass;

public class SettingActivity extends ToolbarClass {
    SettingService settingService ;


    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_setting, "الضبط");

        settingService = new SettingService();
        settingService.getDate(this);
    }

    public void logout(View view) {
        Logout logout = new Logout();
        logout.logOut(this);

    }
}