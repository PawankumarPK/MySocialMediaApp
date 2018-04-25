package com.example.hp.photobloggerpart2.Profile;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hp.photobloggerpart2.R;
import com.example.hp.photobloggerpart2.Utils.FirebaseMethods;
import com.example.hp.photobloggerpart2.Utils.UniversalImageLoader;
import com.example.hp.photobloggerpart2.models.User;
import com.example.hp.photobloggerpart2.models.UserAccountSettings;
import com.example.hp.photobloggerpart2.models.UserSettings;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfileFragment extends Fragment {

    private static final String TAG = "EditProfileFragment";


    //firebase
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference myRef;
    private FirebaseMethods mFirebaseMethods;
    private String userID;

    //EditProfile Fragment widgets
    private EditText mDisplayName, mUsername, mWebsite, mDescription, mEmail, mPhoneNumber;
    private TextView mChangeProfilePhoto;
    private CircleImageView mProfilePhoto;

    //vars
    private UserSettings mUserSettings;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editprofile,container,false);
        mProfilePhoto = (CircleImageView) view.findViewById(R.id.profile_photo);
        mDisplayName = (EditText) view.findViewById(R.id.display_name);
        mUsername = (EditText) view.findViewById(R.id.username);
        mWebsite = (EditText) view.findViewById(R.id.website);
        mDescription = (EditText) view.findViewById(R.id.description);
        mEmail = (EditText) view.findViewById(R.id.email);
        mPhoneNumber = (EditText) view.findViewById(R.id.phoneNumber);
        mChangeProfilePhoto = (TextView) view.findViewById(R.id.changeProfilePhoto);
        mFirebaseMethods = new FirebaseMethods(getActivity());

       // initImageLoader();
      //  setProfileImage();
        setupFirebaseAuth();



        ImageView backArrow = (ImageView)view.findViewById(R.id.profile_photo);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: navigating back to ProfileActivity");
                getActivity().finish();


            }
       });

        ImageView checkmark = (ImageView)view.findViewById(R.id.saveChanges);
        checkmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: attempting to save changes. ");
                saveProfileSettings();
            }
        });

        return view;
    }


    private void saveProfileSettings(){
        final String displayName = mDisplayName.getText().toString();
        final String username = mUsername.getText().toString();
        final String website = mWebsite.getText().toString();
        final String discription = mDescription.getText().toString();
        final String email = mEmail.getText().toString();
        final long phoneNumber = Long.parseLong(mPhoneNumber.getText().toString());

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                //cance1: the user did not changed their username
                if (!mUserSettings.getUser().getUsername().equals(username))
                {

                    checkIfUsernameExists(username);

                }
                //case2: the user chaneged their username therefore we need to check their uniqeness
                else {

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void checkIfUsernameExists(final String username) {
        Log.d(TAG,"checkIfUsernameExits: Checking if "+ username+ "already exits. ");

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
        Query query = reference
                .child(getString(R.string.dbname_users))
                .orderByChild(getString(R.string.field_username))
                .equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if (!dataSnapshot.exists()){
                    //add user name

                    mFirebaseMethods.updateUsername(username);
                    Toast.makeText(getActivity(), "Saved Username. ", Toast.LENGTH_SHORT).show();

                }
                for (DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    if (singleSnapshot.exists()){
                        Log.d(TAG,"checkIfUsernameExits: FOUND A MATCH: "+ singleSnapshot.getValue(User.class).getUsername());
                        Toast.makeText(getActivity(), "That username is already exists. ", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

  /*  private void initImageLoader(){
        UniversalImageLoader universalImageLoader = new UniversalImageLoader(getActivity());
        ImageLoader.getInstance().init(universalImageLoader.getConfig());
    }*/

/*
       private void setProfileImage() {
        Log.d(TAG,"setProfileImage: setting profile image.");
        String imgURL = "www.google.co.in/search?q=android&rlz=1C1NDCM_enIN790IN790&source=lnms&tbm=isch&sa=X&ved=0ahUKEwjPzJ38ksLaAhXLLY8KHVVQAAMQ_AUICygC&biw=1366&bih=662#imgrc=m4ntQXPIF1QTVM:";
        UniversalImageLoader.setImage(imgURL, mProfilePhoto,null,"https://");
    }*/


    private void setProfileWidgets(UserSettings userSettings) {
      //  Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.toString());
       // Log.d(TAG, "setProfileWidgets: setting widgets with data retrieving from firebase database: " + userSettings.getSettings().getUsername());


        User user = userSettings.getUser();
        UserAccountSettings settings = userSettings.getSettings();

        UniversalImageLoader.setImage(settings.getProfile_photo(), mProfilePhoto, null, "");

//        Glide.with(getActivity())
//                .load(settings.getProfile_photo())
//                .into(mProfilePhoto);

        mDisplayName.setText(settings.getDisplay_name());
        mUsername.setText(settings.getUsername());
        mWebsite.setText(settings.getWebsite());
        mDescription.setText(settings.getDescription());
        mEmail.setText(userSettings.getUser().getEmail());
        mPhoneNumber.setText(String.valueOf(userSettings.getUser().getPhone_number()));
      //  mProgressBar.setVisibility(View.GONE);
    }


          /*
    ------------------------Firebase----------------------------
     */

    //setup firebase auth objects

    private void setupFirebaseAuth() {

        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();
        mFirebaseDatabase = FirebaseDatabase.getInstance();
        myRef = mFirebaseDatabase.getReference();
        userID = mAuth.getCurrentUser().getUid();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
                // ...
            }
        };

        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                // retrive user information from database
                 setProfileWidgets(mFirebaseMethods.getUserSettings(dataSnapshot));

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
