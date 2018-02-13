package com.example.shipon.easytravel;

import com.firebase.client.Firebase;

/**
 * Created by Shipon on 11/25/2017.
 */

public class connection {
    Firebase getReference(){
        Firebase rf= new Firebase("https://easytravel142.firebaseio.com/UserInfo/3XZz7DIZ3dNmRya1izgjcCHa1l12/Name");
        return rf;
    }
}
