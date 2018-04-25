package com.example.hp.photobloggerpart2.Profile;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;


import com.example.hp.photobloggerpart2.R;
import com.example.hp.photobloggerpart2.Utils.BottomNavigationViewHelper;
import com.example.hp.photobloggerpart2.Utils.GridImageAdapter;
import com.example.hp.photobloggerpart2.Utils.UniversalImageLoader;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 4;
    private static final int NUM_GRID_COLUMNS = 3;

    private Context mContext = ProfileActivity.this;
    private ProgressBar mProgressBar;
    private ImageView profilePhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Log.d(TAG, "onCreate: started.");

        mProgressBar = (ProgressBar)findViewById(R.id.profileProgressBar);
//        mProgressBar.setVisibility(View.GONE);

        init();
/*
        setupBottomNavigationView();
        setupToolbar();
        setupActivityWidget();
        setProfileImage();
        tempGridSetup();*/
    }

    private void init() {
        Log.d(TAG, "init: inflating Profile");
        ProfileFragment fragment = new ProfileFragment();
        FragmentTransaction transaction = ProfileActivity.this.getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.container, fragment);
        transaction.addToBackStack(getString(R.string.profile_fragment));
        transaction.commit();
    }
  /*  private void tempGridSetup(){
        ArrayList<String> imgURLs = new ArrayList<>();
        imgURLs.add("https://www.google.co.in/search?q=scenery+images&rlz=1C1NDCM_enIN790IN790&tbs=isz:i&tbm=isch&source=lnt&sa=X&ved=0ahUKEwjKv-q1n8PaAhXKr48KHWuvAcUQpwUIHg&biw=1366&bih=662&dpr=1#imgrc=7wUFanYEmeYEjM:");
        imgURLs.add("https://www.google.co.in/search?q=scenery+images&rlz=1C1NDCM_enIN790IN790&tbs=isz:m,sur:fc&tbm=isch&source=lnt&sa=X&ved=0ahUKEwjKv-q1n8PaAhXKr48KHWuvAcUQpwUIHg&biw=1366&bih=662&dpr=1#imgrc=qRJJzQDWx6MmqM:");
        imgURLs.add("https://www.google.co.in/search?q=scenery+images&rlz=1C1NDCM_enIN790IN790&tbm=isch&source=lnt&tbs=isz:m&sa=X&ved=0ahUKEwi1gYqwn8PaAhXEv48KHefSBugQpwUIHw&biw=1366&bih=662&dpr=1#imgrc=xtiODpZYDC3ToM:");
        imgURLs.add("https://www.google.co.in/search?q=scenery+images&rlz=1C1NDCM_enIN790IN790&tbm=isch&source=lnt&tbs=isz:m&sa=X&ved=0ahUKEwi1gYqwn8PaAhXEv48KHefSBugQpwUIHw&biw=1366&bih=662&dpr=1#imgrc=ZuuhyOVT3O__nM:");
        imgURLs.add("https://www.google.co.in/search?q=scenery+images&rlz=1C1NDCM_enIN790IN790&tbm=isch&source=lnt&tbs=isz:m&sa=X&ved=0ahUKEwi1gYqwn8PaAhXEv48KHefSBugQpwUIHw&biw=1366&bih=662&dpr=1#imgrc=rEMEkxc1NOqKDM:");
        imgURLs.add("https://www.google.co.in/search?q=scenery+images&rlz=1C1NDCM_enIN790IN790&tbm=isch&source=lnt&tbs=isz:m&sa=X&ved=0ahUKEwi1gYqwn8PaAhXEv48KHefSBugQpwUIHw&biw=1366&bih=662&dpr=1#imgrc=k0h_XV4cf2jMYM:");
        imgURLs.add("https://www.google.co.in/search?q=scenery+images&rlz=1C1NDCM_enIN790IN790&tbm=isch&source=lnt&tbs=isz:m&sa=X&ved=0ahUKEwi1gYqwn8PaAhXEv48KHefSBugQpwUIHw&biw=1366&bih=662&dpr=1#imgrc=jxl_IZHd8qiSTM:");
        imgURLs.add("https://www.google.co.in/search?q=scenery+images&rlz=1C1NDCM_enIN790IN790&tbm=isch&source=lnt&tbs=isz:m&sa=X&ved=0ahUKEwi1gYqwn8PaAhXEv48KHefSBugQpwUIHw&biw=1366&bih=662&dpr=1#imgrc=hNfD90efmEyZ8M:  ");
        imgURLs.add("https://www.google.co.in/search?q=scenery+images&rlz=1C1NDCM_enIN790IN790&tbm=isch&source=lnt&tbs=isz:m&sa=X&ved=0ahUKEwi1gYqwn8PaAhXEv48KHefSBugQpwUIHw&biw=1366&bih=662&dpr=1#imgrc=61isFrfyFsHnfM:");
        imgURLs.add("https://www.google.co.in/search?q=scenery+images&rlz=1C1NDCM_enIN790IN790&tbm=isch&source=lnt&tbs=isz:m&sa=X&ved=0ahUKEwi1gYqwn8PaAhXEv48KHefSBugQpwUIHw&biw=1366&bih=662&dpr=1#imgrc=0LDgHe3kWVyFFM:");
        imgURLs.add("https://www.google.co.in/search?q=scenery+images&rlz=1C1NDCM_enIN790IN790&tbm=isch&source=lnt&tbs=isz:m&sa=X&ved=0ahUKEwi1gYqwn8PaAhXEv48KHefSBugQpwUIHw&biw=1366&bih=662&dpr=1#imgrc=oyTtz2wVECwz0M:");
        imgURLs.add("https://www.google.co.in/search?q=scenery+images&rlz=1C1NDCM_enIN790IN790&tbm=isch&source=lnt&tbs=isz:m&sa=X&ved=0ahUKEwi1gYqwn8PaAhXEv48KHefSBugQpwUIHw&biw=1366&bih=662&dpr=1#imgrc=TufArx0OMx6hWM:");

        setupImageGrid(imgURLs);
    }
    private void setupImageGrid(ArrayList<String>imgURLs){
        GridView gridView = (GridView)findViewById(R.id.gridView);

        int gridWidth = getResources().getDisplayMetrics().widthPixels;
        int imageWidth = gridWidth/NUM_GRID_COLUMNS;
        gridView.setColumnWidth(imageWidth);

        GridImageAdapter adapter = new GridImageAdapter(mContext, R.layout.layout_grid_imageview,"",imgURLs);
        gridView.setAdapter(adapter);
    }

    private void setProfileImage(){
        Log.d(TAG,"setProfileImage: setting profile photo");
        String imgURL = "www.google.co.in/search?q=android&rlz=1C1NDCM_enIN790IN790&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjPzJ38ksLaAhXLLY8KHVVQAAMQ_AUICygC&biw=1366&bih=662#imgrc=m4ntQXPIF1QTVM:";
        UniversalImageLoader.setImage(imgURL,profilePhoto,mProgressBar,"https://");

    }
    private void setupActivityWidget(){
        mProgressBar = (ProgressBar)findViewById(R.id.profileProgressBar);
        mProgressBar.setVisibility(View.GONE);
        profilePhoto = (ImageView)findViewById(R.id.profile_photo);
    }
    private void setupToolbar(){
        Toolbar toolbar = (Toolbar)findViewById(R.id.profileToolBar);
        setSupportActionBar(toolbar);

    ImageView profileMenu = (ImageView)findViewById(R.id.profileMenu);
    profileMenu.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Log.d(TAG, "onClick: navigating to account settings. ");
            Intent intent = new Intent(mContext, AccountSettingActivity.class);
            startActivity(intent);
        }
    });
    }

    //Bottom navigation setup

    private void setupBottomNavigationView(){
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigation");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx)findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);
*/
    }




