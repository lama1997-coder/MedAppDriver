package com.magsood.medappuser.SharedPrefrense;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

public class UserPreferences {




    public static final String MyPREFERENCES = "data" ;
    SharedPreferences sharedpreferences;
    Context context;


    public UserPreferences(Context context) {
        this.context =context;
        sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
    }

    public void setToken(String token){


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("token",token);
        editor.apply();
    }

    public void setUserId(String id){


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("userId",id);
        editor.apply();
    }


    public String  getToken(){
        return sharedpreferences.getString("token","");


    }
    public String getUserId(){


       return sharedpreferences.getString("userId","");
    }
    public  void removeSharedPrefrenceData(){
        try {

            SharedPreferences.Editor prefEditor = sharedpreferences.edit();
            prefEditor.remove("data");
            prefEditor.apply();
            prefEditor.clear();
        } catch (Exception e) {
            Log.i("", "Exception : " + e.toString());
        }
    }


}
