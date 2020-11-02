package com.example.a2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.content.BroadcastReceiver;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Button cButton;
    private BroadcastReceiver a2Receiver;
    private IntentFilter a2Filter;
    private static final String My_PERMISSION = "edu.uic.cs478.f20.kaboom";
    private static final String INTENT_RECEIVER = "com.example.vacation.toast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cButton = (Button) findViewById(R.id.A2Button);
        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForPermission();
            }
        });
    }

    private void toRegister() {
        a2Filter = new IntentFilter(INTENT_RECEIVER);
        a2Filter.setPriority(20);
        a2Receiver = new ReceiverBroadcast();
        registerReceiver(a2Receiver, a2Filter);

        Intent i = getPackageManager().getLaunchIntentForPackage("com.example.a3");
        startActivity(i);
    }

    private void checkForPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, My_PERMISSION);
        if (PackageManager.PERMISSION_GRANTED == permissionCheck) {
            Toast.makeText(this, "Permission is Granted!", Toast.LENGTH_SHORT).show();
            toRegister();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{My_PERMISSION}, 0) ;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission is Granted!", Toast.LENGTH_SHORT).show();
                toRegister();
            } else {
                Toast.makeText(this, "Permission was NOT granted!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}