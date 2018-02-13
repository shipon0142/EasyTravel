package com.example.shipon.easytravel;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by Shipon on 11/25/2017.
 */

public class EasyTravel extends Application {
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }


}
