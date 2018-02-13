package com.example.shipon.easytravel;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Gallery;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.File;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Locale;

import static android.app.Activity.RESULT_OK;
import static com.example.shipon.easytravel.DatePickerFragment.formattedDate;
import static com.example.shipon.easytravel.Events.in;
import static com.example.shipon.easytravel.Events.userId;
import static com.example.shipon.easytravel.LogInActivity.uid;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;


/**
 * Created by Shipon on 11/27/2017.
 */

public class CreateEvents extends Fragment{
    private String yr;
    public static TextView pickStartDate,pickEndDate,pickStartTime,pickEndTime,pickLocation;
private int day,month,year,hour,minute;
private Button addPhoto;
private ImageView coverPhoto;
private RelativeLayout  layout;
private EditText details,cost,eventName;
private Button createEvent;
private Uri imageUri;
private FirebaseUser user;
private FirebaseAuth mAuth;
private ProgressBar mProcessbar;
    Calendar myCalendar = Calendar.getInstance();
private DatabaseReference ref;
// private int day,month,year;
private String []M={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
public static final int SELECTED_PICTURE=1,PLACE_PICKER_REQUEST=2;
    boolean k=false;
    boolean b=false;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Create Event");

    }
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_events, container, false);
        mProcessbar=view.findViewById(R.id.pbHeaderProgress);
        //requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        mProcessbar.setVisibility(view.GONE);

        eventName=view.findViewById(R.id.idEventName);
        pickStartDate = view.findViewById(R.id.idStartDate);
        pickEndDate = view.findViewById(R.id.idEndDate);
        pickStartTime = view.findViewById(R.id.idStartTime);
        pickEndTime = view.findViewById(R.id.idEndTime);
        addPhoto = view.findViewById(R.id.idAddPhoto);
        coverPhoto = view.findViewById(R.id.idImageView);
        pickLocation=view.findViewById(R.id.idPickLocation);
        details=view.findViewById(R.id.idDetails);
        cost=view.findViewById(R.id.idCost);
        createEvent=view.findViewById(R.id.idButtonCreateEvent);
        Calendar c = Calendar.getInstance();
        year = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day = c.get(Calendar.DAY_OF_MONTH);
        Calendar mcurrentTime = Calendar.getInstance();
        hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        minute = mcurrentTime.get(Calendar.MINUTE);
       // pickStartDate.setText(day + "-" + month + "-" + year);
        pickStartDate.setText(M[month]+" "+day);
        pickStartTime.setText(hour + ":" + minute);
        pickStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                           yr= String.valueOf(i);
                        pickStartDate.setText(M[i1] + " " + i2);
                        pickStartDate.setTextColor(Color.BLACK);
                    }
                }, year, month, day);
                datePickerDialog.show();


            }
        });
        pickEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {

                        pickEndDate.setText(M[i1] + " " + i2);
                        pickEndDate.setTextColor(Color.BLACK);
                    }
                }, year, month, day);
                datePickerDialog.show();


            }
        });
        pickStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        pickStartTime.setText(i + ":" + i1);
                        pickStartTime.setTextColor(Color.BLACK);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        pickEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TimePickerDialog timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        pickEndTime.setText(i + ":" + i1);
                        pickEndTime.setTextColor(Color.BLACK);
                    }
                }, hour, minute, false);
                timePickerDialog.show();
            }
        });
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i=new Intent(Intent.ACTION_PICK);
                File picture= Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
                String path=picture.getPath();
                Uri data=Uri.parse(path);
                i.setDataAndType(data,"image/*");
                startActivityForResult(i, SELECTED_PICTURE);
            }
        });
        pickLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              PlacePicker.IntentBuilder builder=new PlacePicker.IntentBuilder();
              Intent intent;
                try {
                    intent=builder.build((Activity) getContext());
                    startActivityForResult(intent,PLACE_PICKER_REQUEST);
                } catch (GooglePlayServicesRepairableException e) {
                    e.printStackTrace();
                } catch (GooglePlayServicesNotAvailableException e) {
                    e.printStackTrace();
                }
            }
        });
        createEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                k=false;
                b=false;
                mProcessbar.setVisibility(view.VISIBLE);
               Firebase reff1 = new Firebase("https://easytravel142.firebaseio.com/Events");
                reff1.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                           String U=(dsp.getKey());
                            if(uid.equals(U)){
                                k=true;
                                Toast.makeText(getContext(),"You already have an Event.",Toast.LENGTH_SHORT).show();
                                mProcessbar.setVisibility(view.GONE);
                                return;
                            }

                        }
                    }
                    @Override
                    public void onCancelled(FirebaseError firebaseError) {

                    }
                });

                if(addPhoto.getText().toString().equals("AddPhoto")){
                    mProcessbar.setVisibility(view.GONE);
                    k=true;
                    Toast.makeText(getContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(eventName.getText().toString().equals("")){
                    k=true;
                    mProcessbar.setVisibility(view.GONE);
                    Toast.makeText(getContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pickEndDate.getText().toString().equals("Pick Date")){
                    k=true;
                    mProcessbar.setVisibility(view.GONE);
                    Toast.makeText(getContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pickEndTime.getText().toString().equals("Pick Time")){
                    k=true;
                    mProcessbar.setVisibility(view.GONE);
                    Toast.makeText(getContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(pickLocation.getText().toString().equals("Location")){
                    k=true;
                    mProcessbar.setVisibility(view.GONE);
                    Toast.makeText(getContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(details.getText().toString().equals("")){
                    mProcessbar.setVisibility(view.GONE);
                    k=true;
                    Toast.makeText(getContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(cost.getText().toString().equals("")){
                    mProcessbar.setVisibility(view.GONE);
                    k=true;
                    Toast.makeText(getContext(),"Invalid Input",Toast.LENGTH_SHORT).show();
                    return;
                }
                ref = FirebaseDatabase.getInstance().getReference().child("Events").child(uid);
                StorageReference store=FirebaseStorage.getInstance().getReference();
              //  String uId=user.getUid();
           StorageReference filepath= store.child("Photos").child(uid);

           filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
               @Override
               public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    if(k==false) {
                        ref.child("EventName").setValue(eventName.getText().toString());
                        ref.child("StartDate").setValue(pickStartDate.getText().toString());
                        ref.child("StartTime").setValue(pickStartTime.getText().toString());
                        ref.child("EndDate").setValue(pickEndDate.getText().toString());
                        ref.child("EndTime").setValue(pickEndTime.getText().toString());
                        ref.child("Location").setValue(pickLocation.getText().toString());
                        ref.child("Details").setValue(details.getText().toString());
                        ref.child("Cost").setValue(cost.getText().toString());
                        ref.child("Year").setValue(yr.toString());
                        Toast.makeText(getContext(), "Event create succesful", Toast.LENGTH_SHORT).show();
                        Fragment fragment = null;
                        fragment = new Events();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace(R.id.idMainNav, fragment);
                        fragmentTransaction.commit();



                        mProcessbar.setVisibility(view.GONE);
                    }



               }
           });
            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==PLACE_PICKER_REQUEST){
            Place place=PlacePicker.getPlace(data, getContext());
            String name= (String) place.getName();
            String address= (String) place.getAddress();
            pickLocation.setText(name+"\n"+address);
             pickLocation.setTextColor(Color.BLACK);
        }

        if(requestCode==SELECTED_PICTURE){
            imageUri=data.getData();
            InputStream inputStream;
            try {


                inputStream = getActivity().getContentResolver().openInputStream(imageUri);
                Bitmap image=BitmapFactory.decodeStream(inputStream);
                coverPhoto.setImageBitmap(image);
               // Toast.makeText(getContext(),"YES",Toast.LENGTH_SHORT).show();
                addPhoto.setText("Change Photo");
                coverPhoto.setScaleType(ImageView.ScaleType.FIT_XY);
            }
            catch (Exception e){
                e.printStackTrace();
               // Toast.makeText(getContext(),"NO",Toast.LENGTH_SHORT).show();

            }
        }











                   //  String[] projection={MediaStore.Images.Media.DATA};
                   //  Uri uri=data.getData();
                  //   Cursor cursor= getContext().getContentResolver().query(uri,projection,null,null,null);
                  //   cursor.moveToFirst();
                   //  int columIndex=cursor.getColumnIndex(projection[0]);
                    // String filePath=cursor.getString(columIndex);
                     //cursor.close();
                   //  Bitmap B=BitmapFactory.decodeFile(filePath);
                    // coverPhoto.setImageBitmap(B);
               }






    }






