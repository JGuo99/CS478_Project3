package com.example.a2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

public class ReceiverBroadcast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(context, "What is up - message from A2 App", Toast.LENGTH_SHORT).show();
        int urlIndicator = intent.getIntExtra("URL", 0);
        dynamicToast(urlIndicator, context);
    }

    private void dynamicToast(int url, Context context) {
        switch (url) {
            case 0:
                Toast.makeText(context, "To Hawaii - message from A2", Toast.LENGTH_SHORT).show();
                break;
            case 1:
                Toast.makeText(context, "To Bahamas - message from A2", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(context, "To Bora-Bora - message from A2", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(context, "To Florida- message from A2", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(context, "What is up - message from A2 App", Toast.LENGTH_SHORT).show();
                break;
        }
    }
}