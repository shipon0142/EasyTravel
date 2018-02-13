package com.example.shipon.easytravel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Process;
import android.support.design.widget.TabLayout;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import android.support.v4.app.FragmentPagerAdapter;
import android.webkit.WebView;
import android.support.v4.app.FragmentStatePagerAdapter;
        import android.support.v4.view.PagerAdapter;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.zip.Inflater;


import static android.widget.Toast.LENGTH_SHORT;
import static com.example.shipon.easytravel.LogInActivity.uEmail;
import static com.example.shipon.easytravel.LogInActivity.uName;
import static com.example.shipon.easytravel.LogInActivity.uid;
import static com.example.shipon.easytravel.R.layout.nav_header_main;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
  //  private SectionsPagerAdapter mSectionsPagerAdapter;

          private TextView showEmail,showName;
    private ViewPager mViewPager;
    private ImageView imageView;
    private DatabaseReference mDatabase;
    private String name;
    Bundle bundle = new Bundle();
    private Boolean exit = false;
private String text;
    private Inflater inflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        showEmail = header.findViewById(R.id.emailField);
        showName = header.findViewById(R.id.nameField);
        imageView=header.findViewById(R.id.imageVie);

       getname();
        showEmail.setText(uEmail.toString());
        navigationView.setNavigationItemSelectedListener(this);

        displayScene(R.id.idPlace);
    }





    @Override
    public void onBackPressed() {
        if (exit) {
            moveTaskToBack(true);
            android.os.Process.killProcess(android.os.Process.myPid());


           System.exit(1);

            finish();
            // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }
    public void getname(){
      //  Toast.makeText(getApplicationContext(),"okk",Toast.LENGTH_SHORT).show();
        Firebase ref = new Firebase("https://easytravel142.firebaseio.com/UserInfo/"+uid+"/Name");

        ref.addListenerForSingleValueEvent(new com.firebase.client.ValueEventListener() {
            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                String NAME=dataSnapshot.getValue(String.class);
                uName=NAME;
             //   Toast.makeText(getApplicationContext(),NAME,Toast.LENGTH_SHORT).show();
                showName.setText(uName.toString());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });


    }


       // @Override
  //  public void onBackPressed() {
   //     DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
      //  if (drawer.isDrawerOpen(GravityCompat.START)) {
      //      drawer.closeDrawer(GravityCompat.START);
       // } else {
        //    super.onBackPressed();
      //  }
  //  }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    private void displayScene(int id){
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

//showEmail.setText("dsaf");
       // Toast.makeText(getApplicationContext(),"gfhf",Toast.LENGTH_SHORT).show();
          Fragment fragment=null;
          switch (id){
              case R.id.idMyaccount:
                  fragment=new MyAccount();
                  break;
              case R.id.idPlace:
                      fragment=new DefaultPlaceDetails();
                      break;
              case R.id.idEvents:
                  fragment=new Events();
                  break;
              case R.id.idcreateEvent:
                  fragment=new CreateEvents();
                  break;
              case R.id.idSetting:
                  fragment=new Settings();
                  break;
              case R.id.idSignout:
                  SharedPreferences loginPreferences = getSharedPreferences("loginPrefs", MODE_PRIVATE);
                  SharedPreferences.Editor loginPrefsEditor = loginPreferences.edit();
                  loginPrefsEditor.clear();
                  loginPrefsEditor.commit();
                  Intent intent= new Intent(MainActivity.this,LogInActivity.class);
                  startActivity(intent);
                  finish();
                  break;
          }
          if(fragment!=null) {
              FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
              ft.replace(R.id.idMainNav, fragment);
              ft.commit();
          }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

         displayScene(id);


        return true;
    }


    public void placeClick(View view) {

         text= (String) ((Button) view).getText();
        Toast mess = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        mess.show();
        Fragment fragment = new ClickButtonAllPlaces();

        bundle.putString("placename", text);
        fragment.setArguments(bundle);


        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.idMainNav, fragment);
        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }


    public void clickHistory(View view) {


        Fragment fragment = new historyTab();

       fragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.idMainNav, fragment);
        ft.commit();
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

    }


}
