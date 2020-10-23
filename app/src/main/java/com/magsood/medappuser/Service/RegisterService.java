package com.magsood.medappuser.Service;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.magsood.medappuser.Constants;
import com.magsood.medappuser.R;


import es.dmoral.toasty.Toasty;

public class RegisterService {
    String fullName , email,location,phoneNumber,gender,password,confPassword;




    public void sendDate(Activity activity) {

        fullName = ((EditText) activity.findViewById(R.id.fullName)).getText().toString();
        email = ((EditText) activity.findViewById(R.id.email)).getText().toString();
        location = ((EditText) activity.findViewById(R.id.location)).getText().toString();
        phoneNumber = ((EditText) activity.findViewById(R.id.phoneNumber)).getText().toString();
        gender = ((EditText) activity.findViewById(R.id.gender)).getText().toString();
        password = ((EditText) activity.findViewById(R.id.password)).getText().toString();
        confPassword = email = ((EditText) activity.findViewById(R.id.confirm_pass)).getText().toString();



            Ion.with(activity)
                    .load("POST", "https://www.google.com/")
//                    .addHeader("Content-Type", "text/plain")
//                    .setBodyParameter("fullName",fullName )
//                    .setBodyParameter("password", password)
//                    .setBodyParameter("location",location)
//                    .setBodyParameter("email",email)
//                    .setBodyParameter("gender",gender)
                   .asJsonObject()

                    .setCallback(new FutureCallback<JsonObject>() {
                                     @Override
                                     public void onCompleted(Exception e, JsonObject response) {

                                        // Log.d("login_response", response.getRequest().toString());
                                         //Toasty.error(getApplicationContext(),""+response,Toast.LENGTH_LONG).show();


                                         if ((e != null)) {
                                             Toasty.warning(activity, "تحقق من اتصال الانترنت", Toast.LENGTH_LONG).show();

                                         } else {
                                             Log.d("login_response", response.toString());

                                             if (true) {




//
//
//                                                     Toasty.success(register, "تم تسجيل الدخول بنجاح", Toast.LENGTH_LONG).show();
//
//
//                                                     JSONArray jsonArray = jsonObject.getJSONArray("data");
//                                                     JSONObject jsonObject1 = jsonArray.getJSONObject(0);
//
//                                                     //Save The Token Key In Prefrence
//                                                     SharedPreferences.Editor editor = preferences.edit();
//                                                     editor.putString("id", jsonObject1.getString("id"));
//                                                     editor.putString("us_name", jsonObject1.getString("us_name"));
//                                                     editor.putString("mem_phone", jsonObject1.getString("mem_phone"));
//                                                     editor.putString("mem_address", jsonObject1.getString("mem_address"));
//                                                     editor.putString("mem_email", jsonObject1.getString("mem_email"));
//                                                     editor.putString("acc_type", jsonObject1.getString("acc_type"));
//
//
//                                                     editor.apply();
//                                                     Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                                                     startActivity(intent);
//
//                                                     //altayeb
//                                                     finish();
//
//
                                             } else {
                                                 Toasty.warning(activity, "تحقق من البيانات", Toast.LENGTH_LONG).show();
//
//


                                             }

                                         }


                                     }
                                 }
                    );
                    }


}



