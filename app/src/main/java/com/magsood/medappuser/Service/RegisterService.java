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
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
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
    String fullName, email, location, phoneNumber, gender, password, confPassword;
String TAG = "RESPONSE";

    public void sendDate(Activity activity) {

        fullName = ((EditText) activity.findViewById(R.id.fullName)).getText().toString();
        email = ((EditText) activity.findViewById(R.id.email)).getText().toString();
        location = ((EditText) activity.findViewById(R.id.location)).getText().toString();
        phoneNumber = ((EditText) activity.findViewById(R.id.phoneNumber)).getText().toString();
        gender = ((EditText) activity.findViewById(R.id.gender)).getText().toString();
        password = ((EditText) activity.findViewById(R.id.password)).getText().toString();
        confPassword = ((EditText) activity.findViewById(R.id.confirm_pass)).getText().toString();

        if (TextUtils.isEmpty(fullName)) {
           ((EditText) activity.findViewById(R.id.fullName)).setError("Please enter username");
            ((EditText) activity.findViewById(R.id.fullName)).requestFocus();
            return;
        }

        if (TextUtils.isEmpty(email)) {
            ((EditText) activity.findViewById(R.id.email)).setError("Please enter your email");
            ((EditText) activity.findViewById(R.id.email)).requestFocus();
            return;
        }

//        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
//            ((EditText) activity.findViewById(R.id.email)).setError("Enter a valid email");
//            ((EditText) activity.findViewById(R.id.email)).requestFocus();
//            return;
//        }

        if (TextUtils.isEmpty(password)) {
            ((EditText) activity.findViewById(R.id.password)).setError("Enter a password");
            ((EditText) activity.findViewById(R.id.password)).requestFocus();
            return;
        }
        if (TextUtils.isEmpty(location)) {
            ((EditText) activity.findViewById(R.id.location)).setError("Enter a Location");
            ((EditText) activity.findViewById(R.id.location)).requestFocus();
            return;
        }
        if (TextUtils.isEmpty(phoneNumber)) {
            ((EditText) activity.findViewById(R.id.phoneNumber)).setError("Enter a phone number");
            ((EditText) activity.findViewById(R.id.phoneNumber)).requestFocus();
            return;
        }
        if (TextUtils.isEmpty(gender)) {
            ((EditText) activity.findViewById(R.id.gender)).setError("Enter a gender");
            ((EditText) activity.findViewById(R.id.gender)).requestFocus();
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
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.REGISTRATION_URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d(TAG, response.toString());

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
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




