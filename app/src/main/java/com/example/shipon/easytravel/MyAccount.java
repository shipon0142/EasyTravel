package com.example.shipon.easytravel;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.zip.Inflater;

import static com.example.shipon.easytravel.Events.in;
import static com.example.shipon.easytravel.Events.startDate;
import static com.example.shipon.easytravel.Events.userId;
import static com.example.shipon.easytravel.LogInActivity.uid;

/**
 * Created by Shipon on 11/27/2017.
 */

public class MyAccount extends Fragment{
    private ListView request = null, accept = null;
    private TextView r;
    public static ArrayList<String> user = new ArrayList<String>();
    public static ArrayList<String> userNam = new ArrayList<String>();
    public static ArrayList<String> userEmail = new ArrayList<String>();
    public static ArrayList<String> user1 = new ArrayList<String>();
    public static ArrayList<String> userNam1 = new ArrayList<String>();
    public static ArrayList<String> userEmail1 = new ArrayList<String>();
    private long days;
    private View view;
    private TextView textEvent_Name,textEvent_SDate,textRDate;
    private Button delete;
    private String EventName,day;
    private String StartDate;
    private String Year;
    private ImageView refresh;
    public static ProgressBar rp;
    public static RequestClass Adapter;
    public static Accepted Adapter2;
    private int mm,yy,dd;
    private TextView a,b;
    private  String[] m={"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

    //public static String[] user = new String[10];
    //public static String[] userNam = new String[10];
    //public static String[] userEmail = new String[10];
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        getActivity().setTitle("My Event");
        itemClick();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
try {


    view = inflater.inflate(R.layout.my_account, container, false);
    delete=view.findViewById(R.id.idDelete);
    refresh=view.findViewById(R.id.rf);
    a=view.findViewById(R.id.JoinText);
    b=view.findViewById(R.id.AcceptText);
    rp=view.findViewById(R.id.Aid);
    rp.setVisibility(View.GONE);
    textRDate = view.findViewById(R.id.rDate);
    r = view.findViewById(R.id.sdate);
    textEvent_Name = view.findViewById(R.id.sEventName);
    textEvent_SDate = view.findViewById(R.id.sdate);
    request = view.findViewById(R.id.idRequestView);
    accept = view.findViewById(R.id.idAcceptView);
    Adapter = new RequestClass(getActivity(), userNam);
    request.setAdapter(Adapter);
    Adapter2 = new Accepted(getActivity(), userNam1);
    accept.setAdapter(Adapter2);



    Adapter.notifyDataSetChanged();
    Adapter2.notifyDataSetChanged();
    delete.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();

            alertDialog.setMessage("Are you sure?");
            alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "Yes",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {

                    DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Events/" + uid);

                    ref2.getRef().removeValue();
                    DatabaseReference reff2 = FirebaseDatabase.getInstance().getReference("Accepted/" + uid);
                    reff2.getRef().removeValue();
                    DatabaseReference refff2 = FirebaseDatabase.getInstance().getReference("Request/" + uid);
                    refff2.getRef().removeValue();
                    Toast.makeText(getContext(),"Delete successful",Toast.LENGTH_SHORT).show();

                }
            });
            alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "No",new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {



                    // alertDialog.show();


                }
            });
            alertDialog.show();

        }
    });

   refresh.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            rp.setVisibility(View.VISIBLE);
            refresh.setVisibility(view.GONE);
            FragmentTransaction ft = getFragmentManager().beginTransaction();
            ft.detach(MyAccount.this).attach(MyAccount.this).commit();
            refresh.setVisibility(View.VISIBLE);
            rp.setVisibility(view.GONE);
        }
    });


}
catch (Exception e){
    Toast.makeText(getActivity(),"No event",Toast.LENGTH_SHORT ).show();

}
        return view;
    }

    public void itemClick() {
        try {


            final Firebase reff = new Firebase("https://easytravel142.firebaseio.com/Request/" + uid);
            reff.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int j = 0;
                    user.clear();
                    userNam.clear();
                    userEmail.clear();
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        user.add(dsp.getKey());
                        userNam.add((String) dsp.child("Name").getValue());
                        userEmail.add((String) dsp.child("Email").getValue());
                        j++;
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });
            final Firebase reff1 = new Firebase("https://easytravel142.firebaseio.com/Accepted/" + uid);
            reff1.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int j = 0;
                    user1.clear();
                    userNam1.clear();
                    userEmail1.clear();
                    for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                        user1.add(dsp.getKey());
                        userNam1.add((String) dsp.child("Name").getValue());
                        userEmail1.add((String) dsp.child("Email").getValue());
                        j++;
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                }
            });


            //Toast.makeText(getContext(),userNam[0]+" "+userNam[1],Toast.LENGTH_SHORT).show();
            final Firebase reffF = new Firebase("https://easytravel142.firebaseio.com/Events/" + uid);
            reffF.addListenerForSingleValueEvent(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int j = 0;

                        EventName = ((String) dataSnapshot.child("EventName").getValue());
                        StartDate = ((String) dataSnapshot.child("StartDate").getValue());
                        Year = ((String) dataSnapshot.child("Year").getValue());
                        try {


                            String substr = StartDate.substring(0, 3);

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
                            int subst = Integer.parseInt(StartDate.substring(4));
                            dd = subst;
                            yy = Integer.parseInt(Year);
                            //   Toast.makeText(getContext(),mm+" "+dd+" "+yy,Toast.LENGTH_SHORT).show();
                            Calendar thatDay = Calendar.getInstance();
                            thatDay.set(Calendar.DAY_OF_MONTH, dd);
                            thatDay.set(Calendar.MONTH, mm); // 0-11 so 1 less
                            thatDay.set(Calendar.YEAR, yy);

                            Calendar today = Calendar.getInstance();
                            long diff = thatDay.getTimeInMillis() - today.getTimeInMillis();
                            days = diff / (24 * 60 * 60 * 1000);
                            day = days + " days remaining";
                            textEvent_Name.setText(EventName);
                            textEvent_SDate.setText(StartDate);
                            textRDate.setText(day);

                        }
                        catch (Exception E) {
                            textEvent_Name.setText("No event found");
                            delete.setVisibility(View.GONE);
                            a.setText("");
                            b.setText("");
                            textEvent_SDate.setText("");
                            textRDate.setText("");
                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Toast.makeText(getActivity(),"No event",Toast.LENGTH_SHORT ).show();
                }
            });
        }
        catch (Exception e){
        }

    }
}