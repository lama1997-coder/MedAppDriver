package com.magsood.medappuser.Activity;

import android.os.Bundle;
import android.view.View;

import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.RegisterService;
import com.magsood.medappuser.Utils.ToolbarClass;

public class Register extends ToolbarClass {


    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_register, "تسجيل جديد");


    }


    public void register(View view) {
        RegisterService registerService = new RegisterService();

        registerService.sendDate(this);
    }
}