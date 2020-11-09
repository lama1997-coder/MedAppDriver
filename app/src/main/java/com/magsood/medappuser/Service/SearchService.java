package com.magsood.medappuser.Service;

import android.app.Activity;
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
import com.magsood.medappuser.Adapter.AdapterSearchResult;
import com.magsood.medappuser.Constants;
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

public class SearchService {
    UserPreferences userPreferences;
    RecyclerView recyclerView;
    ArrayList<ModelSearch> modelSearchArrayList;
    AdapterSearchResult adapterSearchResult;
    String message;



    String TAG = "RESPONSE";

    public void search(Activity activity, String searchString) {



        userPreferences = new UserPreferences(activity );

        recyclerView = activity.findViewById(R.id.recycler);




        Map<String, String> params = new HashMap<>();
        params.put("searchString", searchString);

        final KProgressHUD progressDialog;// Validation
        progressDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("الرجاء الانتظار")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.POST,
                Constants.SEARCH_URL+"?token="+userPreferences.getToken(), new JSONObject(params),
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();


                        try {
                            Log.e("response",response.get("data").toString());
                            JSONArray jsonArray = response.getJSONArray("data");

                            Log.e("response",jsonArray.toString());

                        modelSearchArrayList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject data = jsonArray.getJSONObject(i);
                            ModelSearch modelSearch = new ModelSearch();
                            modelSearch.setId(data.getString("id"));
                            modelSearch.setPharmacyID(data.getString("pharmacyID"));
                            modelSearch.setMedicineID(data.getString("medicineID"));
                            modelSearch.setTradeName(data.getString("tradeName"));
                            modelSearch.setPublicName(data.getString("publicName"));
                            modelSearch.setPrice(data.getString("price"));
                            modelSearch.setPharmacyName(data.getString("name"));
                            modelSearch.setDescription(data.getString("description"));
                            modelSearch.setCompnayName(data.getString("compnayName"));
                            modelSearch.setPhoneNumber(data.getString("phoneNumber"));
                            modelSearch.setLocation(data.getString("location"));
                            modelSearch.setLng(data.getString("lng"));
                            modelSearch.setLat(data.getString("lat"));
                            modelSearch.setState(data.getString("state"));


                            modelSearchArrayList.add(modelSearch);
                        }
                        adapterSearchResult = new AdapterSearchResult(activity,modelSearchArrayList);
                        recyclerView.setAdapter(adapterSearchResult);
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
                    message = "غير موجود";
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

    public ArrayList<String> getMedicine(Activity activity) {

        userPreferences = new UserPreferences(activity );

        recyclerView = activity.findViewById(R.id.recycler);


        ArrayList<String> medicine = new ArrayList<>();


        final KProgressHUD progressDialog;// Validation
        progressDialog = KProgressHUD.create(activity)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("الرجاء الانتظار")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();
        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                Constants.GET_MEDICINE+"?token="+userPreferences.getToken(), null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        progressDialog.dismiss();
                        Log.e("response",response.toString());

                        try {
                            JSONArray data = response.getJSONArray("data");

                            for (int i = 0 ;i<data.length();i++){
                                JSONObject jsonObject = data.optJSONObject(i);
                                medicine.add(jsonObject.getString("tradeName"));
                                medicine.add(jsonObject.getString("publicName"));



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
                    message = "الرجاء تسجيل الدخول";
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

        return medicine;
    }

}
