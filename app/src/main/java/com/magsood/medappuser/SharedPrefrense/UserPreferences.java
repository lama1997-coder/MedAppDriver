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

    public String  getlong(){
        return sharedpreferences.getString("long","");


    }

    public String  getlat(){
        return sharedpreferences.getString("lat","");


    }


    public void setlat(String lat){


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("lat",lat);
        editor.apply();
    }

    public void setlon(String longt){


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("long",longt);
        editor.apply();
    }

    public void setToken(String token){


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("token",token);
        editor.apply();
    }

    public void setPhoneNumber(String token){


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("phoneNumber",token);
        editor.apply();
    }

    public String  getPhoneNumber(){
        return sharedpreferences.getString("phoneNumber","");


    }

    public void setUserId(String id){


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("userId",id);
        editor.apply();
    }
    public void setUserName(String id){


        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString("fullName",id);
        editor.apply();
    }
    public String  getUserName(){
        return sharedpreferences.getString("fullName","");


    }
    public String  getToken(){
        return sharedpreferences.getString("token","");


    }
    public String getUserId(){


       return sharedpreferences.getString("userId","");
    }
    public  void removeSharedPrefrenceData(){
        try {

            context.deleteDatabase("easylife.db");

            SharedPreferences.Editor prefEditor = sharedpreferences.edit();
            prefEditor.remove("data");
            prefEditor.apply();
            prefEditor.clear();
            prefEditor.apply();
        } catch (Exception e) {
            Log.i("", "Exception : " + e.toString());
        }
    }


}
