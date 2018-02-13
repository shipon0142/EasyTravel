package com.example.shipon.easytravel;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class FirstActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_first);
    }

    public void create(View view) {
        Intent i=new Intent(FirstActivity.this,SignUpActivity.class);
        startActivity(i);

    }

    public void goSignIn(View view) {
        Intent ii=new Intent(FirstActivity.this,LogInActivity.class);
        startActivity(ii);

    }
}
