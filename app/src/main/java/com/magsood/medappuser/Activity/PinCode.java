package com.magsood.medappuser.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.VerificationNumber;
import com.magsood.medappuser.Utils.ToolbarClass;

public class PinCode extends AppCompatActivity {


ImageView sendCode;
VerificationNumber verificationNumber;
EditText code;
TextView resendCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_code);
        sendCode = findViewById(R.id.img_send);
        code = findViewById(R.id.code);
        verificationNumber = new VerificationNumber();
        resendCode = findViewById(R.id.resend_code);

        resendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              resend();
            }
        });

        sendCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (TextUtils.isEmpty(code.getText().toString())) {
                   code.setError("ادخل رمز التاكيد");
                    code.requestFocus();
                }
                else
                    send(code.getText().toString());


            }
        });




    }

    private void resend() {
        verificationNumber.verificationNumber(this);
    }

    private void send(String code) {
        verificationNumber.sendCode(this,code);
    }


}