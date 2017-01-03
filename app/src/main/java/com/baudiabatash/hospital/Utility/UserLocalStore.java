package com.baudiabatash.hospital.Utility;

import android.content.Context;
import android.content.SharedPreferences;




/**
 * Created by Sohel on 4/12/2016.
 */
public class UserLocalStore {

    private static final String SP_NAME ="userDetails";

    SharedPreferences userLocalDatabase;

    public UserLocalStore(Context context){
        userLocalDatabase = context.getSharedPreferences(SP_NAME,0);
    }


    // StoreUserData Method







    public  void setUserLoggedIn(boolean loggedin){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putBoolean("loggedIn",loggedin);
        spEditor.commit();

    }

    public boolean getUserLoggedIn(){
        return userLocalDatabase.getBoolean("loggedIn",false);
    }

    public void clearUserData(){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.clear();
        spEditor.apply();
    }

    public void setUserName(String userName){
        SharedPreferences.Editor spEditor = userLocalDatabase.edit();
        spEditor.putString("username",userName);
        spEditor.apply();
    }

    public String getUserName(){
        return userLocalDatabase.getString("username","");
    }




}
