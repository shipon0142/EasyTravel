package com.example.shipon.easytravel;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import static android.widget.Toast.LENGTH_SHORT;
import static com.example.shipon.easytravel.LogInActivity.email;
import static com.example.shipon.easytravel.LogInActivity.uEmail;
import static com.example.shipon.easytravel.LogInActivity.password;
import static com.example.shipon.easytravel.LogInActivity.saveLogin;
import static com.example.shipon.easytravel.LogInActivity.saveLoginCheckBox;
import static com.example.shipon.easytravel.LogInActivity.uid;

public class Splash extends Activity {
private ImageView IV;
private TextView tv;
    boolean connected;
    Thread timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        IV=(ImageView)findViewById(R.id.imageLogo);
        // tv=(TextView) findViewById(R.id.textView);
        Animation animation= AnimationUtils.loadAnimation(this,R.anim.myanim);
        IV.startAnimation(animation);
        //tv.startAnimation(animation);
        timer=new Thread() {
            public void run() {
                try {
                    sleep(5000);
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {

                    SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                    //  SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();
                    saveLogin = loginPreferences.getBoolean("saveLogin", false);
                    if (saveLogin == true) {



                        String e=loginPreferences.getString("username", "");
                        String p=(loginPreferences.getString("password", ""));
                        String u=(loginPreferences.getString("userid", ""));


                        uEmail=e.toString();
                        uid=u.toString();

                        Intent iuu;
                        iuu = new Intent(Splash.this,MainActivity.class);
                        //iuu.putExtra("loggedInUserId",uid);
                        startActivity(iuu);
                    }

                    else {

                        Intent i = new Intent(Splash.this, FirstActivity.class);
                        startActivity(i);
                        finish();
                    }
                }


            }
        };
        connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
            timer.start();
        }
        else
            connected = false;


        if(connected==false) {
            final AlertDialog alertDialog = new AlertDialog.Builder(Splash.this).create();
           alertDialog.setTitle("WiFi");
           alertDialog.setMessage("Check your internet connection");
           alertDialog.setIcon(R.drawable.ic_signal_wifi_off_black_24dp);
           alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Try again", new DialogInterface.OnClickListener() {
               public void onClick(DialogInterface dialog, int which) {

                   finish();
                   startActivity(getIntent());

                  // alertDialog.show();


               }
           });
            alertDialog.show();
       }














    }
}
