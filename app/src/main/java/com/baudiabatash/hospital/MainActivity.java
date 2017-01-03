package com.baudiabatash.hospital;

import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;

import com.baudiabatash.hospital.Utility.Constant;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private DrawerLayout mDrawerLayout;
    private FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        Iconify
                .with(new FontAwesomeModule());

        setContentView(R.layout.activity_main);

        manager = getSupportFragmentManager();
        //Toolbar Code
        toolbar = (Toolbar) findViewById(R.id.app_bar);
        setSupportActionBar(toolbar);




        //Drawer Layout Code
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);



        NavigationDrawer drawerFragment =
                (NavigationDrawer) manager.findFragmentById(R.id.fragment_navigation_drawer);


        drawerFragment.setUp(R.id.fragment_navigation_drawer, mDrawerLayout, toolbar);
        getSupportActionBar().setTitle(Constant.HOME);

        mDrawerLayout.closeDrawer(Gravity.LEFT);
    }
}
