package com.magsood.medappuser.Service;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
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
import com.magsood.medappuser.Activity.MainActivity;
import com.magsood.medappuser.Constants;
import com.magsood.medappuser.SharedPrefrense.UserPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class RequestService {




    UserPreferences userPreferences;
    String TAG = "RESPONSE";
    String message;

    public void sendRequest(Activity activity,JSONObject jsonObject) {



        userPreferences = new UserPreferences(activity);





//        Map<String, String> params = new HashMap<>();
//        params.put("amount", amount);
//        params.put("medicineID", medicineID);
//        params.put("dropLng", lang);
//        params.put("dropLat", lat);
        final KProgressHUD progressDialog;// Validation
        progressDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("الرجاء الانتظار")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.SEND_REQUEST_URL+"?token="+userPreferences.getToken(),jsonObject,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        try {
                            if(response.getString("success").equals("true")){

                                Toast.makeText(activity,response.getString("message"),Toast.LENGTH_SHORT).show();

                                activity.deleteDatabase("easylife.db");

                            }
                            else if(response.getString("success").equals("false")){

                                Toast.makeText(activity,response.getString("error"),Toast.LENGTH_SHORT).show();



                            }
                        } catch (JSONException e) {
                           e.printStackTrace();
                        }
                        Log.d(TAG, response.toString());
                       // userPreferences.removeSharedPrefrenceData();
                        Log.e("response", String.valueOf(response));


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
                        Log.e("response",userPreferences.getToken());
                    } catch (UnsupportedEncodingException e1) {
                        // Couldn't properly decode data to string
                        e1.printStackTrace();
                    } catch (JSONException e2) {
                        // returned data is not JSONObject?
                        e2.printStackTrace();
                    }
                }
                if (error instanceof NetworkError) {
                    message="Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof ServerError) {
                    message = "The server could not be found. Please try again after some time!!";
                } else if (error instanceof AuthFailureError) {
                    message = "Phone number or password wrong";
                } else if (error instanceof ParseError) {
                    message = "Parsing error! Please try again after some time!!";
                } else if (error instanceof NoConnectionError) {
                    message = "Cannot connect to Internet...Please check your connection!";
                } else if (error instanceof TimeoutError) {
                    message = "Connection TimeOut! Please check your internet connection.";
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
