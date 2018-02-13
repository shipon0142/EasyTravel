package com.example.shipon.easytravel;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Connection;
import java.util.Map;

import static android.widget.Toast.LENGTH_SHORT;

public class SignUpActivity extends Activity {
   private EditText name, email, contact, username, password,cPassword;
private FirebaseAuth mAuth;
private FirebaseUser user;
private ProgressBar pp;
 private String userId;
    //connection con= new connection();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        name = (EditText) findViewById(R.id.editName);
        email = (EditText) findViewById(R.id.editEmail);
        contact = (EditText) findViewById(R.id.editContact);
        username = (EditText) findViewById(R.id.editUsername);
        password = (EditText) findViewById(R.id.editPassword);
        cPassword = (EditText) findViewById(R.id.editCPassword);
        pp=findViewById(R.id.pbb);
        pp.setVisibility(View.GONE);
      //  ref.addValueEventListener(new ValueEventListener() {
         //   @Override
           // public void onDataChange(DataSnapshot dataSnapshot) {
              //  children= (int) dataSnapshot.getChildrenCount();
           // }

          //  @Override
          //  public void onCancelled(FirebaseError firebaseError) {

           // }
     //   });


    }

    public void logInNow(View view) {
        Intent in = new Intent(this, LogInActivity.class);
        startActivity(in);
    }

    public void signup(View view) {
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



        if(name.getText().toString().isEmpty()){
            name.setError("This field cannot be empty");
            return;
        }
        if(email.getText().toString().isEmpty()){
            email.setError("This field cannot be empty");
            return;
        }
        if(email.getText().toString().contains("@gmail.com") || email.getText().toString().contains("@yahoo.com") || email.getText().toString().contains("@diu.edu.bd")){

        }
        else{
            email.setError("Email Not valid");
        }
        if(contact.getText().toString().isEmpty()){
            contact.setError("This field cannot be empty");
            return;
        }
        if(contact.getText().toString().length()!=11 ){
            contact.setError("Contact not valid");
            return;
        }
        if(!contact.getText().toString().substring(0,2).equals("01")){
            contact.setError("Contact not valid");
            return;
        }
        if(username.getText().toString().isEmpty()){
            username.setError("This field cannot be empty");
            return;
        }
        if(password.getText().toString().isEmpty()){
            password.setError("This field cannot be empty");
            return;
        }
        if(password.getText().toString().length()<6){
            password.setError("Password Minimum 6 charecter");
            return;
        }
        if(cPassword.getText().toString().isEmpty()){
            cPassword.setError("This field cannot be empty");
            return;
        }
        if(!password.getText().toString().equals(cPassword.getText().toString())){
            cPassword.setError("Password Not Match");
            return;
        }

      callSignUp(email.getText().toString().trim(),password.getText().toString().trim());
        // try {


          //    Firebase parent = ref.child(Integer.toString(children));
            //  parent.child("Name").setValue(name.getText().toString());
            //  parent.child("Email").setValue(email.getText().toString());
             // parent.child("Contact").setValue(contact.getText().toString());
             // parent.child("Username").setValue(username.getText().toString());
             // parent.child("Password").setValue(password.getText().toString());
            //  name.setText("");
            //  email.setText("");
             // contact.setText("");
             // username.setText("");
            //  password.setText("");
            //  cPassword.setText("");


            //  Toast toast = Toast.makeText(getApplicationContext(), "Registration Succesfull", Toast.LENGTH_SHORT);
            //  toast.show();
            //  Intent i=new Intent(this,LogInActivity.class);
            //  startActivity(i);
           //   return;
        //  }
        //  catch (Exception E){
         //     Toast toast = Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT);
         //     toast.show();
         //     return;
        //  }

    }
 private void callSignUp(String email, String password){
        pp.setVisibility(View.VISIBLE);

     mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
         @Override
         public void onComplete(@NonNull Task<AuthResult> task) {
             if (task.isSuccessful()) {
                 // Sign in success, update UI with the signed-in user's information
                 Log.d("Testing", "createUserWithEmail:success");
                user = mAuth.getCurrentUser();
                // Toast.makeText(SignUpActivity.this, "Authentication success", Toast.LENGTH_SHORT).show();
                 userId=user.getUid();
                 userProfile(user);



             } else {
                 // If sign in fails, display a message to the user.
                 Log.w("Test", "createUserWithEmail:failure", task.getException());
                 //Toast.makeText(SignUpActivity.this, "Authentication failed.",
                   //      Toast.LENGTH_SHORT).show();

             }


         }
     });

 }
 private void userProfile(FirebaseUser us){


     UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder().build();

     us.updateProfile(profileUpdates)
             .addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     if (task.isSuccessful()) {
                      try {


                          DatabaseReference ref = FirebaseDatabase.getInstance().getReference("UserInfo/"+userId);
                          ref.child("Name").setValue(name.getText().toString());
                          ref.child("Email").setValue(email.getText().toString());
                          ref.child("Contact").setValue(contact.getText().toString());
                          ref.child("Username").setValue(username.getText().toString());


                          Toast mess = Toast.makeText(getApplicationContext(), "Sign Up succesful", Toast.LENGTH_SHORT);
                          pp.setVisibility(View.GONE);
                          mess.show();
                          Intent i = new Intent(SignUpActivity.this, LogInActivity.class);
                          startActivity(i);
                      }
                      catch (Exception e){

                      }
                     }
                 }
             });
 }

}
