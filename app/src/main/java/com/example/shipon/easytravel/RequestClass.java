package com.example.shipon.easytravel;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import static com.example.shipon.easytravel.Accepted.Namee;
import static com.example.shipon.easytravel.LogInActivity.uid;
import static com.example.shipon.easytravel.MyAccount.Adapter;
import static com.example.shipon.easytravel.MyAccount.Adapter2;
import static com.example.shipon.easytravel.MyAccount.rp;
import static com.example.shipon.easytravel.MyAccount.user;
import static com.example.shipon.easytravel.MyAccount.userEmail;
import static com.example.shipon.easytravel.MyAccount.userNam;

/**
 * Created by Shipon on 12/10/2017.
 */

public class RequestClass extends ArrayAdapter<String> {

    ArrayList<String> Name;
    Context mContext;
    private ViewHold mViewHold;
    public RequestClass(@NonNull Context context, ArrayList<String> name) {
        super(context, R.layout.view_request);
        this.Name=name;

        this.mContext=context;
    }
    @Override
    public int getCount() {
        return Name.size();
    }
    @NonNull
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        mViewHold=new ViewHold();

        if (convertView == null) {

            LayoutInflater mInflater = (LayoutInflater) mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = mInflater.inflate(R.layout.view_request, parent, false);

            mViewHold.textViewName = (TextView) convertView.findViewById(R.id.lname);


                final Button b=convertView.findViewById(R.id.lAccept);
            final Button bb=convertView.findViewById(R.id.lIgnore);
             final View ConvertView = convertView;
             bb.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View view) {
                     try {
                         rp.setVisibility(View.VISIBLE);

                         DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Request/" + uid+"/"+ user.get(position));
                         ref2.getRef().removeValue();
                         Toast.makeText(getContext(),"Ignored Successful",Toast.LENGTH_SHORT).show();
                         Name.remove(position);
                         //Toast.makeText(getContext(), "OK "+Name[position], Toast.LENGTH_SHORT).show();
                         rp.setVisibility(View.GONE);
                         Adapter.notifyDataSetChanged();
                         Adapter2.notifyDataSetChanged();
                     }
                     catch (Exception E){

                     }

                 }
             });

            b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            rp.setVisibility(View.VISIBLE);
                            DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Accepted/" + uid+"/"+ user.get(position));
                            ref.child("Name").setValue(userNam.get(position).toString());
                            ref.child("Email").setValue(userEmail.get(position).toString());
                            DatabaseReference ref2 = FirebaseDatabase.getInstance().getReference("Request/" + uid+"/"+ user.get(position));
                            ref2.getRef().removeValue();
                            Namee.add(Name.get(position));
                           Name.remove(position);
                            Toast.makeText(getContext(),"Accepted Successful",Toast.LENGTH_SHORT).show();
                            //Toast.makeText(getContext(), "OK "+Name[position], Toast.LENGTH_SHORT).show();
                            rp.setVisibility(View.GONE);
                            Adapter.notifyDataSetChanged();
                            Adapter2.notifyDataSetChanged();
                        }
                        catch (Exception E){

                        }


                    }

                });

            convertView.setTag(mViewHold);

        } else {
            mViewHold = (RequestClass.ViewHold)convertView.getTag();

        }


        mViewHold.textViewName.setText(Name.get(position));



        return convertView;

    }




    static  class ViewHold{

        TextView textViewName;
        TextView proo;


    }

}
