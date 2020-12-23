package com.magsood.medappuser.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magsood.medappuser.R;
import com.magsood.medappuser.Service.PrevOrdersService;
import com.magsood.medappuser.Service.ProcessReqService;
import com.magsood.medappuser.Utils.ToolbarClass;

public class OnProgressRequest extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_on_progress_request);

        init();
        ( (LinearLayout)(findViewById(R.id.ic_back))).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext() ,MainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void init() {
        ProcessReqService prevOrdersService = new ProcessReqService();
        prevOrdersService.progressOrder(this);

    }

}