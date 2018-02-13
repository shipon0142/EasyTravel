package com.example.shipon.easytravel;

import android.app.ActionBar;
import android.app.Activity;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;

import static com.example.shipon.easytravel.Events.Cost;
import static com.example.shipon.easytravel.Events.Details;
import static com.example.shipon.easytravel.Events.Location;
import static com.example.shipon.easytravel.Events.eventName;
import static com.example.shipon.easytravel.Events.eventYear;
import static com.example.shipon.easytravel.Events.in;
import static com.example.shipon.easytravel.Events.startDate;
import static com.example.shipon.easytravel.Events.userId;
import static com.example.shipon.easytravel.LogInActivity.uEmail;
import static com.example.shipon.easytravel.LogInActivity.uName;
import static com.example.shipon.easytravel.LogInActivity.uid;

public class EventDetails extends Activity {
private Button BU;
private ImageView coverPhoto;
private String U;
private boolean k=true;

private TextView event_name,start_date,remaining_date,location,manager,details,cost;
private ProgressBar pp;
    String name;
    int mm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_event_details);
          BU=findViewById(R.id.ssJoinButton);
         //in = Integer.parseInt(getIntent().getExtras().getString("indexNo"));
        coverPhoto=findViewById(R.id.ssphoto);
        event_name=findViewById(R.id.sEventName);
        start_date=findViewById(R.id.sdate);
        remaining_date=findViewById(R.id.rDate);
        location=findViewById(R.id.slName);
        manager=findViewById(R.id.mName);
        details=findViewById(R.id.idetails);
        pp=findViewById(R.id.p);
        pp.setVisibility(View.VISIBLE);
        cost=findViewById(R.id.mNam);


        k=false;
        final Firebase reff1 = new Firebase("https://easytravel142.firebaseio.com/Accepted/" + userId.get(in));
        reff1.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                k=false;
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    U=(dsp.getKey());
                    if(uid.equals(U)){
                        k=true;
                        BU.setText("You are going this event");
                    }

                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });
        if(k==false) {
            final Firebase reff = new Firebase("https://easytravel142.firebaseio.com/Request/" + userId.get(in));
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    k = false;
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        U = (dsp.getKey());
                        if (uid.equals(U)) {
                            k = true;
                            BU.setText("Request sent");
                        }

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
        }
        if(k==false)BU.setText("Sent Join Request");
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//pp.setVisibility(View.VISIBLE);

     showw();






    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public void showw(){

        StorageReference load = FirebaseStorage.getInstance().getReference().child("Photos/"+userId.get(in));

        load.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Got the download URL for 'users/me/profile.png'
                // Pass it to Picasso to download, show in ImageView and caching
                Picasso.with(EventDetails.this).load(uri.toString()).into(coverPhoto);
                coverPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
                pp.setVisibility(View.GONE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                // Handle any errors
            }
        });

        final Firebase ref = new Firebase("https://easytravel142.firebaseio.com/UserInfo/"+userId.get(in));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // String name=dataSnapshot.getValue(String.class);
                //   Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
                // collectuserId((Map<String,Object>) dataSnapshot.getValue());




                    name = (String) dataSnapshot.child("Name").getValue();
                      manager.setText(name);
                   // Toast.makeText(getApplicationContext(),name,Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        String substr = startDate.get(in).toString().substring(0, 3);
        if (substr.equals("Jan")) mm = 0;
        if (substr.equals("Feb")) mm = 1;
        if (substr.equals("Mar")) mm = 2;
        if (substr.equals("Apr")) mm = 3;
        if (substr.equals("May")) mm = 4;
        if (substr.equals("Jun")) mm = 5;
        if (substr.equals("Jul")) mm = 6;
        if (substr.equals("Aug")) mm = 7;
        if (substr.equals("Sep")) mm = 8;
        if (substr.equals("Oct")) mm = 9;
        if (substr.equals("Nov")) mm = 10;
        if (substr.equals("Dec")) mm = 11;
        int subst = Integer.parseInt(startDate.get(in).substring(4));
       int dd = subst;
      int  yy = Integer.parseInt(eventYear.get(in));
        //   Toast.makeText(getContext(),mm+" "+dd+" "+yy,Toast.LENGTH_SHORT).show();
        Calendar thatDay = Calendar.getInstance();
        thatDay.set(Calendar.DAY_OF_MONTH, dd);
        thatDay.set(Calendar.MONTH, mm); // 0-11 so 1 less
        thatDay.set(Calendar.YEAR, yy);

        Calendar today = Calendar.getInstance();
        long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
      long  days = diff / (24 * 60 * 60 * 1000);
      String  day = days + " days remaining";
      if(days<=0){
          day="Happening now";
      }
remaining_date.setText(day);

        // Toast.makeText(getApplicationContext(),"Name: "+userName[0],Toast.LENGTH_SHORT).show();
      //  Toast.makeText(getApplicationContext(),user[0]+" "+user[1],Toast.LENGTH_SHORT).show();
       // RequestClass Adapter = new RequestClass(this, userName);
      //  request.setAdapter(Adapter);

        event_name.setText(eventName.get(in));
        start_date.setText(startDate.get(in));
        location.setText(Location.get(in));
        details.setText(Details.get(in));
        cost.setText(Cost.get(in));



    }
 public void another(){
     //RequestClass Adapter = new RequestClass(this, userName);
   //  request.setAdapter(Adapter);
 }

    public void JoinRequest(View view) {
     if(userId.get(in).equals(uid)){
         Toast.makeText(getApplicationContext(), "You Are the Event creator",Toast.LENGTH_SHORT).show();
         return;
     }
      else  if(BU.getText().toString().equals("Request sent")){
            Toast.makeText(getApplicationContext(), "You already sent request",Toast.LENGTH_SHORT).show();
            return;
        }
       else if(BU.getText().toString().equals("You are going this event")){
            Toast.makeText(getApplicationContext(), "You are Going",Toast.LENGTH_SHORT).show();
            return;
        }
     else {

         try {
             DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Request/" + userId.get(in) + "/" + uid);
             ref.child("Name").setValue(uName.toString());
             ref.child("Email").setValue(uEmail.toString());
             Button mButton = (Button) findViewById(R.id.ssJoinButton);
             mButton.setText("Request sent");
             Toast.makeText(getApplicationContext(), "Request sent succesfully",Toast.LENGTH_SHORT).show();
         } catch (Exception E) {

         }
     }




    }




}
