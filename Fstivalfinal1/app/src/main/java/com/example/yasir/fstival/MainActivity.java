package com.example.yasir.fstival;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.auth.api.Auth;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<Feedback>>{
    public final String url ="https://fstival17.000webhostapp.com/getinputnew.php";
    public static final String ANONYMOUS="anonymous";
    public static final int RC_SIGN_IN =1;
    private  FeedbackAdapter feedbackAdapter;
    private  ProgressBar progressBar;
    private  TextView emptystate;
    private ConnectivityManager connMgr;
    private NetworkInfo networkInfo;
    private String mUsername;
    private String email ="";


    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mMessagesDatabaseReference;
    private ChildEventListener mChildEventListener;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;


    //Mywork

    private DrawerLayout drawer;
    private ActionBarDrawerToggle actionBarDrawerToggle;
    private Toolbar tool_bar;
    String []drawerelements;



    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.about:
                startActivity(new Intent(MainActivity.this,About.class));
        }
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Mywork starts
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        drawer = (DrawerLayout)findViewById(R.id.drawer);
        drawerelements = getResources().getStringArray(R.array.drawerelements);

        actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawer,R.string.opendrawer,R.string.closedrawer){
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();
            }
        };

        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();


        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                switch (id){
                    case R.id.feeds:
                        startActivity(new Intent(MainActivity.this,MainActivity.class));
                        break;
                    case R.id.graph:
                        startActivity(new Intent(MainActivity.this,piechart.class));
                        break;
                    case R.id.feedbackform:
                        Intent i = new Intent(MainActivity.this,form.class);
                        i.putExtra("email",email);
                        startActivity(i);
                        break;
                    case R.id.signout:
                        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                        firebaseAuth.signOut();
                        break;

                }

                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }
        });


        //////MY work ends


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mFirebaseAuth = FirebaseAuth.getInstance();

        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    FirebaseUser f = mFirebaseAuth.getCurrentUser();
                    email = f.getEmail();
                    onSignedInInitialize(user.getDisplayName());
                } else {
                    // User is signed out
                    //onSignedOutCleanup();
                    startActivityForResult(
                            AuthUI.getInstance()
                                    .createSignInIntentBuilder()
                                    .setIsSmartLockEnabled(false)
                                    .setProviders(
                                            AuthUI.EMAIL_PROVIDER,
                                            AuthUI.GOOGLE_PROVIDER)
                                    .build(),
                            RC_SIGN_IN);
                }
            }
        };
        progressBar = (ProgressBar)findViewById(R.id.loading_indicator);
        progressBar.setVisibility(View.GONE);
        ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
        FeedbackAdapter feedbackAdapter = new FeedbackAdapter(this,feedbacks);
        ListView listView = (ListView)findViewById(R.id.mainlistview);
        listView.setAdapter(feedbackAdapter);
//        FeedbackAsyncTask feedbackAsyncTask = new FeedbackAsyncTask();
//        feedbackAsyncTask.execute(url);
         emptystate = (TextView)findViewById(R.id.empty_view);
        listView.setEmptyView(emptystate);

        connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connMgr.getActiveNetworkInfo();

        // Test if the user has a network connection, otherwise give the user an error message
        // informing them.
        if (networkInfo != null && networkInfo.isConnected()) {

            getLoaderManager().initLoader(0, null, MainActivity.this);
        }
        else{
            emptystate.setText("No Network Connection");

        }

    };




    public void runAdapter(Context context,ArrayList<Feedback> feedbacks){


        //ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
         feedbackAdapter = new FeedbackAdapter(this,feedbacks);
        ListView listView = (ListView)findViewById(R.id.mainlistview);

        listView.setAdapter(feedbackAdapter);
    }

    @Override
    public Loader<ArrayList<Feedback>> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        FeedbackLoader feedbackLoader = new FeedbackLoader(MainActivity.this,url);
        return feedbackLoader;
    }

    @Override
    public void onLoadFinished(Loader<ArrayList<Feedback>> loader,ArrayList<Feedback> feedbacks ) {
        progressBar.setVisibility(View.GONE);
        runAdapter(MainActivity.this,feedbacks);
    }

    @Override
    public void onLoaderReset(Loader<ArrayList<Feedback>> loader) {
        feedbackAdapter.clear();
    }


//    private class FeedbackAsyncTask extends AsyncTask<String,Void,ArrayList<Feedback>>{
//
//        ArrayList<Feedback> feedbacks1 = new ArrayList<Feedback>();
//        @Override
//        protected ArrayList<Feedback> doInBackground(String... strings) {
//            feedbacks1 = Utils.fetchFeedbackData(strings[0]);
//            return feedbacks1;
//        }
//
//        @Override
//        protected void onPostExecute(ArrayList<Feedback> feedbacks) {
//            runAdapter(MainActivity.this,feedbacks);
//        }
//    }


    private void onSignedInInitialize(String username) {
        mUsername = username;
       // attachDatabaseReadListener();
    }

    private void onSignedOutCleanup() {
        mUsername = ANONYMOUS;
        feedbackAdapter.clear();
        //detachDatabaseReadListener();
    }



    private void detachDatabaseReadListener() {
        if (mChildEventListener != null) {
            mMessagesDatabaseReference.removeEventListener(mChildEventListener);
            mChildEventListener = null;
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
//        FirebaseUser f = mFirebaseAuth.getCurrentUser();
//        email = f.getEmail();

    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mAuthStateListener != null) {
            mFirebaseAuth.removeAuthStateListener(mAuthStateListener);
        }

        //detachDatabaseReadListener();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_file, menu);
        return true;
    }

}


