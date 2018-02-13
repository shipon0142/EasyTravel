package com.example.shipon.easytravel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import static android.widget.Toast.LENGTH_SHORT;

public class LogInActivity extends Activity {
   public static EditText email,password;
   private String user,pass;
   private FirebaseAuth mAuth;
 public static String uid=null,uEmail=null,uName=null;
    public static CheckBox saveLoginCheckBox;
    public static SharedPreferences loginPreferences;
    private SharedPreferences.Editor loginPrefsEditor;
    private Button BBBBB;
    public static Boolean saveLogin;

    ProgressBar pb;
 private String name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_log_in);
        mAuth=FirebaseAuth.getInstance();
        BBBBB=(Button)findViewById(R.id.logIn);

        email=(EditText)findViewById(R.id.editLEmail);
        saveLoginCheckBox=findViewById(R.id.rCheckBox);
        password=(EditText)findViewById(R.id.editLPassword);
        pb=findViewById(R.id.pb);
        pb.setVisibility(View.GONE);
        loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
        loginPrefsEditor = loginPreferences.edit();

        saveLogin = loginPreferences.getBoolean("saveLogin", false);
        if (saveLogin == true) {
            email.setText(loginPreferences.getString("username", ""));
            password.setText(loginPreferences.getString("password", ""));
            saveLoginCheckBox.setChecked(true);
            Intent inn=new Intent(LogInActivity.this,Splash.class);
            startActivity(inn);
            finish();

        }
     //   uid="mXTtHZzWevVJZsNdhLRt3RHrIj62";
      //  uEmail="rubel@gmail.com";
       // uName="N";

    }
    public  void go(String uid){

        Intent iuu;
        iuu = new Intent(LogInActivity.this,MainActivity.class);
        //iuu.putExtra("loggedInUserId",uid);
        startActivity(iuu);

    }
    public void signUpNow(View view) {
        Intent in = new Intent(LogInActivity.this,SignUpActivity.class);
        startActivity(in);

    }
    public void callLogin(final String email, String password) {

        pb.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Toast mess = Toast.makeText(getApplicationContext(), "login successful", Toast.LENGTH_SHORT);
                            mess.show();
                            FirebaseUser user=mAuth.getCurrentUser();
                           uid=user.getUid();
                            uEmail=email;
                            if (saveLoginCheckBox.isChecked()) {
                                loginPrefsEditor.putString("userid", uid);
                                loginPrefsEditor.commit();
                            }
                            go(uid);


                        } else {
                            loginPrefsEditor.putBoolean("saveLogin", false);

                            Toast mess = Toast.makeText(getApplicationContext(), "Wrong Username or Password", Toast.LENGTH_SHORT);
                            mess.show();
                        }
                        pb.setVisibility(View.GONE);
                    }
                });
    }
    public void LogInn(View view) {
        boolean connected = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;
        if(connected==false){
            Toast.makeText(getApplicationContext(),"Please check your Internet Connection",LENGTH_SHORT).show();
            return;
        }




        String Lemail= email.getText().toString();
        String Lpassword= password.getText().toString();
        if(Lemail.length()==0){
            email.setError("This field should not be blank");
            return;
        }
        if(Lpassword.length()==0){
            password.setError("This field should not be blank");
            return;
        }
        if(Lemail.contains("@gmail.com") || Lemail.contains("@yahoo.com") || Lemail.contains("@diu.edu.bd")){


        }
        else{
            email.setError("Email Not Valid");
            return;
        }
        if(Lpassword.length()<6){
            password.setError("Password length minimum 6 charecter");
            return;
        }
        if (view == BBBBB) {
            InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(email.getWindowToken(), 0);

            user = email.getText().toString();
            pass = password.getText().toString();

            if (saveLoginCheckBox.isChecked()) {
                loginPrefsEditor.putBoolean("saveLogin", true);
                loginPrefsEditor.putString("username", user);
                loginPrefsEditor.putString("password", pass);

                loginPrefsEditor.commit();
            } else {
                loginPrefsEditor.clear();
                loginPrefsEditor.commit();
            }
            //callLogin(Lemail,Lpassword);
            //doSomethingElse();
        }


        callLogin(Lemail,Lpassword);
       // Toast mess = Toast.makeText(getApplicationContext(), email.getText().toString(), Toast.LENGTH_SHORT);
     //   mess.show();
    }
}
