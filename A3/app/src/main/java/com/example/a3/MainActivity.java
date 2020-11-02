package com.example.a3;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.app.ActionBar;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements FirstFragment.ListSelectionListener {    //FragmentActivity is a subclass of Activity
    private static final String MY_PERMISSION = "edu.uic.cs478.f20.kaboom";
    private static final String TO_A1_INTENT = "com.example.vacation.toast";
    private Integer url = -1;

    public static String[] vacatSpotArray;
    public static ArrayList<Integer> mQuoteArray = new ArrayList<>(
            Arrays.asList(R.drawable.hawaii_maui_s, R.drawable.bahamas_s,
                    R.drawable.borabora_s, R.drawable.florida_miami_s));   //List of vacation images

    private final SecondFragment mSecondFragment = new SecondFragment();

    FragmentManager mFragmentManager;

    private FrameLayout mTitleFrameLayout, mQuotesFrameLayout;
    private static final int MATCH_PARENT = LinearLayout.LayoutParams.MATCH_PARENT;
    private static final String TAG = "MainActivity";	// For logging

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.i(TAG, getClass().getSimpleName() + ": entered onCreate()");

        super.onCreate(savedInstanceState);

        // Get the string arrays with the location names
        vacatSpotArray = getResources().getStringArray(R.array.Spots);

        setContentView(R.layout.activity_main);

        // Get references to the FirstFragment and to the SecondFragment
        mTitleFrameLayout = (FrameLayout) findViewById(R.id.first_fragment_spot);
        mQuotesFrameLayout = (FrameLayout) findViewById(R.id.second_fragment_image);


        // Get reference to the SupportFragmentManager instead of original FragmentManager
        mFragmentManager = getSupportFragmentManager();

        // Start a new FragmentTransaction with backward compatibility
        FragmentTransaction fragmentTransaction = mFragmentManager
                .beginTransaction();

        // Add the FirstFragment to the layout
        fragmentTransaction.replace(
                R.id.first_fragment_spot,
                new FirstFragment(), "ListFrag");

        // Commit the FragmentTransaction
        fragmentTransaction.commit();

        mFragmentManager.addOnBackStackChangedListener(
                new FragmentManager.OnBackStackChangedListener() {
                    public void onBackStackChanged() {
                        setLayout();
                    }
                });

//        MUST be place at the bottom of onCreate
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(tb);
    }

    //This should populate the items in the Action Bar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_option, menu);
//        MenuInflater inflaterM = getMenuInflater();
//        inflaterM.inflate(R.menu.menu_option, menu);
        return true;
    }

    //Action performed when item selected
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toA1A2:
                toSendBroadcast();
                return true;
            case R.id.exit:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle saveState) {
        super.onSaveInstanceState(saveState);
        saveState.putInt("SelectedItem", url);
    }

    @Override
    public void onRestoreInstanceState(@Nullable Bundle savedState) {
        super.onRestoreInstanceState(savedState);
        url = savedState.getInt("SelectedItem");
        onListSelection(url);
    }

    private void setLayout() {
        if(!mSecondFragment.isAdded()) { //When second fragment is not called
            activateSingleLayout();
        } else { //When second fragment is called
            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                activatePortrait();
            } else {
                activateLandscape();
            }
        }
    }

    private void activatePortrait() {
        mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
        mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
    }

    private void activateLandscape() {
        mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 1f));
        mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT, 2f));
    }

    private void activateSingleLayout() {
        mTitleFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT));
        mQuotesFrameLayout.setLayoutParams(new LinearLayout.LayoutParams(0, MATCH_PARENT));
    }

    // Implement Java interface ListSelectionListener defined in FirstFragment
    // Called by FirstFragment when the user selects an item in the FirstFragment
    @Override
    public void onListSelection(int index) {

        // If the MainActivity has not been added, add it now
        if (!mSecondFragment.isAdded()) {

            // Start a new FragmentTransaction
            FragmentTransaction fragmentTransaction = mFragmentManager
                    .beginTransaction();

            // Add the SecondFragment to the layout
            fragmentTransaction.add(R.id.second_fragment_image, mSecondFragment);

            // Add this FragmentTransaction to the backstack
            fragmentTransaction.addToBackStack(null);

            // Commit the FragmentTransaction
            fragmentTransaction.commit();

            // Force Android to execute the committed FragmentTransaction
            mFragmentManager.executePendingTransactions();
        }

        if (mSecondFragment.getShownIndex() != index) {
            // Tell the SecondFragment to show the image at position index
            mSecondFragment.showImageAtIndex(index);
            url = index;
        }
    }

    private void toSendBroadcast() {
        //  Debugging
//        String urlIndicator = Integer.toString(url);
//        Log.i(TAG, getClass().getSimpleName() + urlIndicator);
//        Toast.makeText(this, urlIndicator, Toast.LENGTH_SHORT).show();

        if (url == -1) {
            Toast.makeText(this, "Please Select a Place!", Toast.LENGTH_SHORT).show();
        } else {
//            Toast.makeText(this, "Permission is Granted!", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(TO_A1_INTENT);
            i.putExtra("URL", url);
            sendOrderedBroadcast(i, MY_PERMISSION);
        }
    }

//    Not Needed For A3
    private void checkForPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, MY_PERMISSION);
        if (PackageManager.PERMISSION_GRANTED == permissionCheck) {
            toSendBroadcast();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{MY_PERMISSION}, 0) ;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                toSendBroadcast();
            } else {
                Toast.makeText(this, "Permission was NOT granted!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}