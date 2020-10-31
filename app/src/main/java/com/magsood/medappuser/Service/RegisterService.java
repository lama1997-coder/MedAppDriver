package com.magsood.medappuser.Service;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.magsood.medappuser.Activity.Login;
import com.magsood.medappuser.Constants;
import com.magsood.medappuser.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import es.dmoral.toasty.Toasty;

public class RegisterService {
    String fullName, email, location, phoneNumber, password, confPassword;
String TAG = "RESPONSE";
String message;
    public void sendDate(Activity activity,String gender) {

        fullName = ((EditText) activity.findViewById(R.id.fullName)).getText().toString();
        email = ((EditText) activity.findViewById(R.id.email)).getText().toString();
        location = ((EditText) activity.findViewById(R.id.location)).getText().toString();
        phoneNumber = ((EditText) activity.findViewById(R.id.phoneNumber)).getText().toString();
        gender = gender;
        password = ((EditText) activity.findViewById(R.id.password)).getText().toString();
        confPassword = ((EditText) activity.findViewById(R.id.confirm_pass)).getText().toString();

        if (TextUtils.isEmpty(fullName)) {
           ((EditText) activity.findViewById(R.id.fullName)).setError("ادخل الاسم");
            ((EditText) activity.findViewById(R.id.fullName)).requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            ((EditText) activity.findViewById(R.id.email)).setError("ادخل الايميل");
            ((EditText) activity.findViewById(R.id.email)).requestFocus();
            return;
        }

        if (!password.equals(confPassword)) {
            ((EditText) activity.findViewById(R.id.password)).setError("كلمة السر غير متطابقة");
            ((EditText) activity.findViewById(R.id.password)).requestFocus();
            return;
        }

        if (TextUtils.isEmpty(password)) {
            ((EditText) activity.findViewById(R.id.password)).setError("ادخل كلمة السر");
            ((EditText) activity.findViewById(R.id.password)).requestFocus();
            return;
        }
        if (TextUtils.isEmpty(location)) {
            ((EditText) activity.findViewById(R.id.location)).setError("ادخل الموقع");
            ((EditText) activity.findViewById(R.id.location)).requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            ((EditText) activity.findViewById(R.id.phoneNumber)).setError("ادخل رقم الهاتف");
            ((EditText) activity.findViewById(R.id.phoneNumber)).requestFocus();
            return;
        }




        Map<String, String> params = new HashMap<>();
        params.put("fullName", fullName);
        params.put("email", email);
        params.put("password", password);
        params.put("gender", gender);
        params.put("location",location);
        params.put("phoneNumber",phoneNumber);
        Log.e("response", String.valueOf(params));
        final KProgressHUD progressDialog;// Validation
        progressDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("الرجاء الانتظار")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.REGISTRATION_URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d(TAG, response.toString());
                        Toast.makeText(activity,"Register Complete  ",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(activity, Login.class);
                        activity.startActivity(intent);


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                // As of f605da3 the following should work
                NetworkResponse response = error.networkResponse;
                if (error instanceof ServerError && response != null) {
                    try {
                        String res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        // Now you can use any deserializer to make sense of data
                        JSONObject obj = new JSONObject(res);

                        Log.e("responseError",obj.toString());
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
                if (error instanceof NetworkError) {
                    message="الرجاء التاكد من الانترنت";
                } else if (error instanceof ServerError) {
                    message = "الخادم غير موجود";
                } else if (error instanceof AuthFailureError) {
                    message = "رقم الهاتف موجود مسبقا ";
                } else if (error instanceof ParseError) {
                    message="الرجاء التاكد من الانترنت";
                } else if (error instanceof NoConnectionError) {
                    message="الرجاء التاكد من الانترنت";
                } else if (error instanceof TimeoutError) {
                    message="الرجاء التاكد من الانترنت";
                }
                Toast.makeText(activity,message,Toast.LENGTH_SHORT).show();

            }
        }) {




            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                // Removed this line if you dont need it or Use application/json
                params.put("Content-Type", "application/json");
                return params;
            }


        };
//
        VolleySingleton.getInstance(activity).addToRequestQueue(jsonObjReq);

    }
    }





