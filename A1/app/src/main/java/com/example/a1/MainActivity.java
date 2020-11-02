package com.example.a1;

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
    private BroadcastReceiver a1Receiver;
    private IntentFilter a1Filter;
    private static final String My_PERMISSION = "edu.uic.cs478.f20.kaboom";
    private static final String A1_RECEIVER = "com.example.vacation.toast";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        a1Filter = new IntentFilter(A1_RECEIVER);
        a1Filter.setPriority(10);
        a1Receiver = new ReceiverBroadcast();
        registerReceiver(a1Receiver, a1Filter);

        cButton = (Button) findViewById(R.id.A1Button);
        cButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkForPermission();
            }
        });
    }

    private void toA2() {
        Intent i = getPackageManager().getLaunchIntentForPackage("com.example.a2");
        startActivity(i);
    }

    private void checkForPermission() {
        int permissionCheck = ContextCompat.checkSelfPermission(this, My_PERMISSION);
        if (PackageManager.PERMISSION_GRANTED == permissionCheck) {
            Toast.makeText(this, "Permission is Granted!", Toast.LENGTH_SHORT).show();
            toA2();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{My_PERMISSION}, 0) ;
        }
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission is Granted!", Toast.LENGTH_SHORT).show();
                toA2();
            } else {
                Toast.makeText(this, "Permission was NOT granted!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(a1Receiver);
    }
}