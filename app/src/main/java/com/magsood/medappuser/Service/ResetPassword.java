package com.magsood.medappuser.Service;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
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
import com.kaopiz.kprogresshud.KProgressHUD;
import com.magsood.medappuser.Activity.Login;
import com.magsood.medappuser.Constants;
import com.magsood.medappuser.R;
import com.magsood.medappuser.SharedPrefrense.UserPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

public class ResetPassword {

    String message = "";






    String phoneNumber;

    UserPreferences userPreferences;




    public void forgetPassword(Activity activity) {


        phoneNumber = ((EditText) activity.findViewById(R.id.phoneNumber)).getText().toString();


        if (TextUtils.isEmpty(phoneNumber)) {
            ((EditText) activity.findViewById(R.id.phoneNumber)).setError("ادخل رقم الهاتف");
            ((EditText) activity.findViewById(R.id.phoneNumber)).requestFocus();
            return;
        }


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("phoneNumber",phoneNumber);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final KProgressHUD progressDialog;// Validation
        progressDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("سيتم ارسال كلمة مرور جديده")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.RESET_PASSWORD, jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        Log.e("response", String.valueOf(response));

                        try {
                            if(response.getString("success").equals("true")){

                                Toast.makeText(activity,response.getString("message"),Toast.LENGTH_SHORT).show();







                            }
                            else if(response.getString("success").equals("false")){

                                Toast.makeText(activity,response.getString("error"),Toast.LENGTH_SHORT).show();



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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
                } else if (error instanceof AuthFailureError) {
                    message="الرجاء التاكد من الانترنت";
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



    public void reset(Activity activity) {
        String password;
        userPreferences = new UserPreferences(activity);



        password = ((EditText) activity.findViewById(R.id.new_password)).getText().toString();


        if (TextUtils.isEmpty(password)) {
            ((EditText) activity.findViewById(R.id.new_password)).setError("ادخل كلمة المرور");
            ((EditText) activity.findViewById(R.id.new_password)).requestFocus();
            return;
        }


        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("newPassword",password);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        final KProgressHUD progressDialog;// Validation
        progressDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("جاري ارسال كلمة المرور الجديده")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.NEW_PASSWORD+"?token="+userPreferences.getToken(), jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        Log.e("response", String.valueOf(response));

                        try {
                            if(response.getString("success").equals("true")){

                                Toast.makeText(activity,response.getString("message"),Toast.LENGTH_SHORT).show();







                            }
                            else if(response.getString("success").equals("false")){

                                Toast.makeText(activity,response.getString("error"),Toast.LENGTH_SHORT).show();



                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


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
                    String res = null;
                    try {
                        res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("responseError",obj.toString());
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                    // Now you can use any deserializer to make sense of data

                    message="الرجاء التاكد من الانترنت";
                } else if (error instanceof AuthFailureError) {
                    String res = null;
                    try {
                        res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("responseError",obj.toString());
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                    message="الرجاء التاكد من الانترنت";
                } else if (error instanceof ParseError) {
                    String res = null;
                    try {
                        res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("responseError",obj.toString());
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                    message="الرجاء التاكد من الانترنت";
                } else if (error instanceof NoConnectionError) {
                    String res = null;
                    try {
                        res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("responseError",obj.toString());
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                    message="الرجاء التاكد من الانترنت";
                } else if (error instanceof TimeoutError) {
                    String res = null;
                    try {
                        res = new String(response.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("responseError",obj.toString());
                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
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
