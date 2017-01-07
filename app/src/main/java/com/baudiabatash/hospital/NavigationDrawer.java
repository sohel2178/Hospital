package com.baudiabatash.hospital;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.baudiabatash.hospital.Navigation.AddDoctorFragment;
import com.baudiabatash.hospital.Navigation.AddPatientFragment;
import com.baudiabatash.hospital.Navigation.DoctorListFragment;
import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;



/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationDrawer extends Fragment implements View.OnClickListener {


    public static final String PREF_NAME ="mypref";
    public static final String KEY_USER_LEARNED_DRAWERR="user_learned_drawer";

    private ActionBarDrawerToggle mDrawerToggle;
    private DrawerLayout mDrawerLayout;

    private boolean mUserLearnedDrawer;
    private boolean mFromSavedInstanceState;

    private View containerView;

    private LinearLayout rlAddPatient,rlAddDoctor,rlAllDoctors;




    public NavigationDrawer() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



        Iconify
                .with(new FontAwesomeModule());



        mUserLearnedDrawer = Boolean.valueOf(readFromPreferences(getActivity(),KEY_USER_LEARNED_DRAWERR,"false"));

        // if saveInstanceState is not null its coming back from rotation
        if(savedInstanceState!=null){
            mFromSavedInstanceState=true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view= inflater.inflate(R.layout.fragment_navigation_drawer, container, false);
        //Initialize View

        initView(view);

        return view;
    }

    private void initView(View view) {
        rlAddPatient = (LinearLayout) view.findViewById(R.id.add_patient);
        rlAddDoctor = (LinearLayout) view.findViewById(R.id.add_doctor);
        rlAllDoctors = (LinearLayout) view.findViewById(R.id.doctor_list);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        rlAddPatient.setOnClickListener(this);
        rlAddDoctor.setOnClickListener(this);
        rlAllDoctors.setOnClickListener(this);


    }

    public void setUp(int fragmentId, DrawerLayout layout, final Toolbar toolbar) {

        containerView = getActivity().findViewById(fragmentId);

        mDrawerLayout = layout;
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(),mDrawerLayout,toolbar,R.string.drawer_open,R.string.drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

                //if user gonna not seen the drawer before thats mean the drawer is open for the first time

                if(!mUserLearnedDrawer){
                    mUserLearnedDrawer=true;
                    // save it in sharedpreferences
                    saveToPreferences(getActivity(),KEY_USER_LEARNED_DRAWERR,mUserLearnedDrawer+"");

                    getActivity().invalidateOptionsMenu();
                }

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //Log.d("Sohel","Offset "+slideOffset);
                /*if(slideOffset<0.4){
                    toolbar.setAlpha(1-slideOffset);
                }*/
            }
        };

        if(!mUserLearnedDrawer && !mFromSavedInstanceState){
            mDrawerLayout.openDrawer(containerView);
        }

        mDrawerLayout.addDrawerListener(mDrawerToggle);

        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mDrawerToggle.syncState();
            }
        });
    }

    public static void saveToPreferences(Context context, String key, String prefValue){
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key,prefValue);
        editor.apply();
    }

    public static String readFromPreferences(Context context, String key, String defaultValue){
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);

        return pref.getString(key,defaultValue);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.add_patient:
                mDrawerLayout.closeDrawer(GravityCompat.START);
                getFragmentManager().beginTransaction().replace(R.id.main_container,new AddPatientFragment())
                        .setCustomAnimations(R.anim.enter_from_top,R.anim.exit_to_bottom).commit();
                break;


            case R.id.add_doctor:
                mDrawerLayout.closeDrawer(GravityCompat.START);

                getFragmentManager().beginTransaction().replace(R.id.main_container,new AddDoctorFragment())
                        .setCustomAnimations(R.anim.enter_from_top,R.anim.exit_to_bottom).commit();
                break;

            case R.id.doctor_list:
                mDrawerLayout.closeDrawer(GravityCompat.START);

                getFragmentManager().beginTransaction().replace(R.id.main_container,new DoctorListFragment())
                        .setCustomAnimations(R.anim.enter_from_top,R.anim.exit_to_bottom).commit();
                break;
        }



    }



}
