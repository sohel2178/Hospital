package com.baudiabatash.hospital;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Gravity;

import com.baudiabatash.hospital.Model.Doctor;
import com.baudiabatash.hospital.Navigation.HomeFragment;
import com.baudiabatash.hospital.Utility.Constant;
import com.baudiabatash.hospital.Utility.UserLocalStore;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainActivity extends AppCompatActivity {

    public static final int REQUEST_READ_PHONE_STATE=12;

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private FragmentManager manager;

    private UserLocalStore userLocalStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Iconify
                .with(new FontAwesomeModule());

        setContentView(R.layout.activity_main);

       /* TelephonyManager tm = (TelephonyManager) getSystemService(TELEPHONY_SERVICE);
// get IMEI

//get The Phone Number
        //String phone = tm.getLine1Number();
        String bal = tm.getDeviceId();
*/
        manager = getSupportFragmentManager();
        //Toolbar Code
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);




        //Drawer Layout Code
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        userLocalStore = new UserLocalStore(this);

       






        NavigationDrawer drawerFragment =
                (NavigationDrawer) manager.findFragmentById(R.id.fragment_navigation_drawer);


        drawerFragment.setUp(R.id.fragment_navigation_drawer, mDrawerLayout, toolbar);
        getSupportActionBar().setTitle(Constant.HOME);

        mDrawerLayout.closeDrawer(Gravity.LEFT);

        getSupportFragmentManager().beginTransaction().replace(R.id.main_container,new HomeFragment())
                .commit();


        /*int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);

        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
        } else {
            //TODO
        }*/
    }
}
