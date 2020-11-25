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
import com.magsood.medappuser.Adapter.AdapterMyOrder;
import com.magsood.medappuser.Adapter.AdapterSearchResult;
import com.magsood.medappuser.Constants;
import com.magsood.medappuser.Model.ModelMyOrder;
import com.magsood.medappuser.Model.ModelSearch;
import com.magsood.medappuser.R;
import com.magsood.medappuser.SharedPrefrense.UserPreferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PrevOrdersService {



    UserPreferences userPreferences;
    String TAG = "RESPONSE";
    String message;
    RecyclerView recycler;
    ArrayList<ModelMyOrder> modelPreArrayList;
    AdapterMyOrder adapterMyOrder;

    public void pre_orders(Activity activity) {



        userPreferences = new UserPreferences(activity);






        final KProgressHUD progressDialog;// Validation
        progressDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("جاري جلب البيانات السابقة")
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
                                try {
                                    Log.e("response",response.get("data").toString());
                                    JSONArray jsonArray = response.getJSONArray("data");

                                    Log.e("response",jsonArray.toString());
                                    recycler = activity.findViewById(R.id.recycler);

                                    modelPreArrayList = new ArrayList<>();
                                    for (int i = 0; i < jsonArray.length(); i++) {
                                        JSONObject data = jsonArray.getJSONObject(i);
                                        ModelMyOrder modelMyOrder = new ModelMyOrder();
                                        JSONArray medcine = data.getJSONArray("medicine");

                                        for (int j = 0 ; j<medcine.length();j++){

                                            JSONObject dataMed = medcine.getJSONObject(j);
                                            modelMyOrder.setTradeName(dataMed.getString("tradeName"));
                                            modelMyOrder.setPublicName(dataMed.getString("publicName"));
                                            modelMyOrder.setPrice(dataMed.getString("price"));
                                            modelMyOrder.setStatus(data.getString("statu"));


                                        }


                                      //  modelMyOrder.setId(data.getString("id"));
//                                        modelMyOrder.setPharmacyID(data.getString("pharmacyID"));
//                                        modelMyOrder.setMedicineID(data.getString("medicineID"));


//                                        modelMyOrder.setPharmacyName(data.getString("name"));
//                                        modelMyOrder.setDescription(data.getString("description"));
//                                        modelMyOrder.setCompnayName(data.getString("compnayName"));
//                                        modelMyOrder.setPhoneNumber(data.getString("phoneNumber"));
//                                        modelMyOrder.setLocation(data.getString("location"));
//                                        modelMyOrder.setLng(data.getString("lng"));
//                                        modelMyOrder.setLat(data.getString("lat"));
//                                        modelMyOrder.setState(data.getString("state"));


                                        modelPreArrayList.add(modelMyOrder);
                                    }
                                    adapterMyOrder = new AdapterMyOrder(activity,modelPreArrayList);
                                    recycler.setAdapter(adapterMyOrder);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }


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
                } else if (error instanceof ServerError) {
                    message = "الخادم غير موجود";
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
