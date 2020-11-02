package com.example.a1;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

public class ReceiverBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "Successfully Receive Data From Test1: ", Toast.LENGTH_SHORT).show();
        Log.i("A1_Receiver", "Data From A3" + intent);
        int urlIndicator = intent.getIntExtra("URL", 0);
        Log.i("From A3", String.format("Value of url is: %d", urlIndicator));

        Intent i = new Intent();
        i.putExtra("dataFromRB", urlIndicator);
        i.setClassName("com.example.a1", "com.example.a1.SecondActivity");
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);

    }
}


