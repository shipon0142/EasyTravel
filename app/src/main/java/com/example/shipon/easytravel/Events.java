package com.example.shipon.easytravel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.okhttp.Call;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.shipon.easytravel.LogInActivity.uName;
import static com.example.shipon.easytravel.LogInActivity.uid;

/**
 * Created by Shipon on 11/27/2017.
 */

public class Events extends Fragment {
    private ListView LV;
    ImageView pb;
    RelativeLayout mainLayout;
    public static int in;

    public static ArrayList<String> userId = new ArrayList<String>();
    public static ArrayList<String> startDate = new ArrayList<String>();
    public static ArrayList<String> endDate = new ArrayList<String>();
    public static ArrayList<String> eventName = new ArrayList<String>();
    public static ArrayList<String> eventYear = new ArrayList<String>();
    public static ArrayList<String> Location = new ArrayList<String>();
    public static ArrayList<String> Details = new ArrayList<String>();
    public static ArrayList<String> Cost = new ArrayList<String>();

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("Events");
    }

    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.events, container, false);
        LV = view.findViewById(R.id.idListView);
        pb = view.findViewById(R.id.pbEvent);
        pb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.detach(Events.this).attach(Events.this).commit();
                //  Toast.makeText(getContext(), "ok", Toast.LENGTH_SHORT).show();

            }
        });
        mainLayout = (RelativeLayout) view.findViewById(R.id.idEventsLayout);
       // pb.setVisibility(View.GONE);

        final Firebase ref = new Firebase("https://easytravel142.firebaseio.com/Events");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // String name=dataSnapshot.getValue(String.class);
                //   Toast.makeText(getContext(),name,Toast.LENGTH_SHORT).show();
                // collectuserId((Map<String,Object>) dataSnapshot.getValue());
                userId.clear();
                startDate.clear();
                endDate.clear();
                eventName.clear();
                eventYear.clear();
                Location.clear();
                Details.clear();
                Cost.clear();
                int i = 0;
                pb.setVisibility(View.VISIBLE);
                for (DataSnapshot dsp : dataSnapshot.getChildren()) {
                    // String U=((String) dsp.getValue().toString());
                    //   Map<String,String> map=dsp.getValue(Map.class)
                    userId.add(dsp.getKey());
                    startDate.add((String) dsp.child("StartDate").getValue());
                    endDate.add((String) dsp.child("EndDate").getValue());
                    eventName.add((String) dsp.child("EventName").getValue());
                    eventYear.add((String) dsp.child("Year").getValue());
                    Location.add((String) dsp.child("Location").getValue());
                    Details.add((String) dsp.child("Details").getValue());
                    Cost.add((String) dsp.child("Cost").getValue());
                    //name[i]=dsp.getKey();
                    //  Toast.makeText(getContext(), s,Toast.LENGTH_SHORT).show();
                    i++;
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        myAdopter myAdapter = new myAdopter(getActivity(), startDate, endDate, eventName, eventYear);

        LV.setAdapter(myAdapter);

        pb.setVisibility(View.GONE);
        listViewClick();
        return view;

    }

    public void listViewClick() {
        LV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
              in=i;
                Intent ij=new Intent(getActivity(),EventDetails.class);
                startActivity(ij);

            }
        });
    }


}



