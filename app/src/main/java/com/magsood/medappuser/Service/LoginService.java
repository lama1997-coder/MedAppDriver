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
import com.magsood.medappuser.Activity.MainActivity;
import com.magsood.medappuser.Activity.PinCode;
import com.magsood.medappuser.Constants;
import com.magsood.medappuser.R;
import com.magsood.medappuser.SharedPrefrense.UserPreferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import es.dmoral.toasty.Toasty;

public class LoginService {


    String phoneNumber,password;
    String TAG ="RESPONSE";
    UserPreferences userPreferences;
    String message;


    public void sendDate(Activity activity) {


        phoneNumber = ((EditText) activity.findViewById(R.id.phoneNumber)).getText().toString();
        password = ((EditText) activity.findViewById(R.id.password)).getText().toString();
        userPreferences = new UserPreferences(activity);




        if (TextUtils.isEmpty(password)) {
            ((EditText) activity.findViewById(R.id.password)).setError("ادخل كلمة السر");
            ((EditText) activity.findViewById(R.id.password)).requestFocus();
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            ((EditText) activity.findViewById(R.id.phoneNumber)).setError("ادخل رقم الهاتف");
            ((EditText) activity.findViewById(R.id.phoneNumber)).requestFocus();
            return;
        }





        Map<String, String> params = new HashMap<>();
if('0' == phoneNumber.charAt(0)){
}
if(phoneNumber.charAt(0)!='0')
   phoneNumber="0"+phoneNumber;
if (phoneNumber.length() != 10) {
            ((EditText) activity.findViewById(R.id.phoneNumber)).setError("رقم الهاتف يجب ان يكون 10 ارقام");
            ((EditText) activity.findViewById(R.id.phoneNumber)).requestFocus();
            return;
        }

        params.put("phoneNumber",phoneNumber);
        params.put("password", password);

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
                Constants.LOGIN_URL, new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();

                        Log.d(TAG, response.toString());


                        try {
                            if(response.toString().contains("error")){
                                try {
                                    Toast.makeText(activity,response.getString("error"),Toast.LENGTH_SHORT).show();
                                    if(response.getString("error").equals("الرجاء تأكيد الحساب")){
                                        Intent intent = new Intent(activity, PinCode.class);
                                        userPreferences.setPhoneNumber(phoneNumber);
                                        VerificationNumber verificationNumber = new VerificationNumber();
                                        verificationNumber.verificationNumber(activity);
                                        activity.startActivity(intent);


                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            else {
                                JSONObject userInfo = response.getJSONObject("data");
                                userPreferences.setUserId(userInfo.getString("userID"));
                                userPreferences.setToken(response.getString("token"));
                                userPreferences.setUserName(userInfo.getString("fullName"));
                                userPreferences.setPhoneNumber(phoneNumber);

                                Toast.makeText(activity, "تم تسجيل الدخول بنجاح", Toast.LENGTH_LONG).show();
                                Intent intent = new Intent(activity, MainActivity.class);
                                activity.startActivity(intent);

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
                        message = "كلمة السر او رقم الهاتف غير صحيحين";
                       // Toast.makeText(activity,"Phone number or password wrong",Toast.LENGTH_SHORT).show();
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
                    NetworkResponse responseFailr = error.networkResponse;
                    String res = null;
                    try {
                        res = new String(responseFailr.data,
                                HttpHeaderParser.parseCharset(response.headers, "utf-8"));
                        JSONObject obj = new JSONObject(res);
                        Log.e("responseError",obj.toString());
                        message = obj.getString("error");

                        if(obj.getString("error").equals("الرجاء تأكيد الحساب")){
                            Intent intent = new Intent(activity, PinCode.class);
                            userPreferences.setPhoneNumber(phoneNumber);
                            VerificationNumber verificationNumber = new VerificationNumber();
                            verificationNumber.verificationNumber(activity);
                            activity.startActivity(intent);


                        }



                    } catch (UnsupportedEncodingException | JSONException e) {
                        e.printStackTrace();
                    }
                    // Now you can use any deserializer to make sense of data
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
