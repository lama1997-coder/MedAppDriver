package com.magsood.medappuser.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.RegisterService;
import com.magsood.medappuser.Utils.ToolbarClass;

import java.util.ArrayList;
import java.util.List;

public class Register extends ToolbarClass implements AdapterView.OnItemSelectedListener {

    String gender = "";

    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_register, "تسجيل جديد");

        Spinner spinner = (Spinner) findViewById(R.id.gender);

        // Spinner click listener
        spinner.setOnItemSelectedListener(this);

        // Spinner Drop down elements
        List<String> gender = new ArrayList<String>();
        gender.add(0,"اختر النــــوع");
        gender.add("ذكر");
        gender.add("انثى");

        // Creating adapter for spinner
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, gender);

        // Drop down layout style - list view with radio button
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        // attaching data adapter to spinner
        spinner.setAdapter(dataAdapter);
    }
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        // On selecting a spinner item


            String item = parent.getItemAtPosition(position).toString();
            gender = item;

        // Showing selected spinner item

    }
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }


    public void register(View view) {
        RegisterService registerService = new RegisterService();

        registerService.sendDate(this,gender);
    }
}