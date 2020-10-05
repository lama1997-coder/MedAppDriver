package com.magsood.medappuser.Activity;

import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.magsood.medappuser.R;
import com.magsood.medappuser.Utils.ToolbarClass;

public class OnProgressRequest extends ToolbarClass {

    CardView cardViewShowDetails;
    TextView txtShowDet;
    ImageView imgHideDet;
    LinearLayout layDetails;
    protected final void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        super.onCreate(R.layout.activity_on_progress_request, "الطلب الحالي");
        init();
    }

    private void init() {
        cardViewShowDetails = findViewById(R.id.showDetails);
        txtShowDet = findViewById(R.id.txtShowDet);
        imgHideDet = findViewById(R.id.imgHideDet);
        layDetails = findViewById(R.id.layDetails);
        cardViewShowDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layDetails.getVisibility()==View.VISIBLE){
                    txtShowDet.setText("تفاصيل");
                    imgHideDet.setVisibility(View.GONE);
                    layDetails.setVisibility(View.GONE);
                }else{
                    txtShowDet.setText("تفاصيل اقل");
                    imgHideDet.setVisibility(View.VISIBLE);
                    layDetails.setVisibility(View.VISIBLE);
                }
            }
        });
    }
}