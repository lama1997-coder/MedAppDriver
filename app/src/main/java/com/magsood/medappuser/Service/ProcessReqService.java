package com.magsood.medappuser.Service;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

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
import com.magsood.medappuser.Adapter.AdapterProgressRequests;
import com.magsood.medappuser.Adapter.AdapterSearchResult;
import com.magsood.medappuser.Constants;
import com.magsood.medappuser.Model.ModelProcessOrder;
import com.magsood.medappuser.R;
import com.magsood.medappuser.SharedPrefrense.UserPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class ProcessReqService {


    RecyclerView recyclerView;
    ArrayList<ModelProcessOrder> modelPreocessReqArrayList;
    AdapterProgressRequests adapterProcessReq;

    UserPreferences userPreferences;
    String TAG = "RESPONSE";
    String message;



    public void progressOrder(Activity activity) {



        userPreferences = new UserPreferences(activity);

        recyclerView = activity.findViewById(R.id.progress_order);




        final KProgressHUD progressDialog;// Validation
        progressDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("جاري جلب الطلبات السابقه")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Constants.PREV_ORDERS+"?token="+userPreferences.getToken(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.d(TAG, response.toString());

                        Log.e("response", String.valueOf(response));

                        try {
                            if(response.getString("success").equals("true")){

                                Toast.makeText(activity,response.getString("message"),Toast.LENGTH_SHORT).show();

                                Log.e("responce",response.getString("data"));
                                JSONObject d = new JSONObject(response.getString("data"));
                                modelPreocessReqArrayList = new ArrayList<>();
                                Iterator<?> iterator = d.keys();
                                while (iterator.hasNext()) {
                                    String key = (String)iterator.next();
                                    Log.e("responce",key);
                                    JSONArray jsonArray = new JSONArray(d.getString(key));
                                    Log.e("responce",jsonArray.toString());

                                    ArrayList<String>pharmacyName = new ArrayList<>();
                                    ArrayList<String>orderStage = new ArrayList<>();
                                    JSONObject data = jsonArray.getJSONObject(0);
                                    Log.e("responce",data.getString("captainName"));

                                    ModelProcessOrder modelProcess = new ModelProcessOrder();
                                    modelProcess.setId(data.getString("orderCollectionID"));

                                    modelProcess.setName_of_captin(data.getString("captainName"));
                                    modelProcess.setPhoneNumber(data.getString("captainPhone"));
                                    modelProcess.setOrder_number(key);
                                    modelProcess.setPrice(data.getString("medicineTotalPrice"));

                                    for (int i = 0;i<jsonArray.length();i++
                                    ){
                                        JSONObject arrayJSONObject = jsonArray.getJSONObject(i);


                                        pharmacyName.add(arrayJSONObject.getString("name"));
                                        orderStage.add(arrayJSONObject.getString("userStage"));

                                    }
                                    modelProcess.setPharmacy_name(pharmacyName);
                                    modelProcess.setPharmacy_stage(orderStage);
                                    modelPreocessReqArrayList.add(modelProcess);


                                    //do what you want with the key.
                                }
                                adapterProcessReq = new AdapterProgressRequests(activity, modelPreocessReqArrayList);

                                if(modelPreocessReqArrayList.size()>0)
                                    recyclerView.setAdapter(adapterProcessReq);

//                                JSONArray jsonArray = new JSONArray(response.getString("data"));
//                                Log.e("responce",jsonArray.toString());
//                                modelPreocessReqArrayList = new ArrayList<>();
//                                for (int i = 0;i<jsonArray.length();i++
//                                ){
//                                    JSONObject data = jsonArray.getJSONObject(i);
//                                    JSONObject med = new JSONObject(data.getString("medicine"));
//                                    ModelProcessOrder modelProcess = new ModelProcessOrder();
//                                    modelProcess.setId(data.getString("orderCollectionID"));
//
//                                    modelProcess.setName_of_captin(data.getString("captainName"));
//                                    modelProcess.setPhone_number(data.getString("captainPhone"));
//                                    modelProcess.setOrder_number(data.getString("orderCollectionID"));
//                                    modelProcess.setPharmacy_name(data.getString("phoneNumber"));
//                                    modelProcess.setPharmacy_stage(data.getString("userStage"));
//
//                                    modelPreocessReqArrayList.add(modelProcess);
//                                }
//                                adapterProcessReq = new AdapterProgressRequests(activity, modelPreocessReqArrayList);
//
//                                if(modelPreocessReqArrayList.size()>0)
//                                    recyclerView.setAdapter(adapterProcessReq);
//





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

}
