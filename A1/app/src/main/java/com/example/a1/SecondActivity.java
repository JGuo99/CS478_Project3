package com.example.a1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        Log.i("A1_sec", "Second Activity in action.");
        Intent intent = getIntent();
        int urlIndicator = intent.getIntExtra("dataFromRB", 0);
        toWebsite(urlIndicator);
    }
    private void toWebsite(int url) {
        Uri webpage = Uri.parse("https://www.android.com"); //Only for initializing
        switch (url) {
            case 0:
                webpage = Uri.parse("https://www.gohawaii.com/");
                break;
            case 1:
                webpage = Uri.parse("https://www.bahamas.com/");
                break;
            case 2:
                webpage = Uri.parse("https://www.marriott.com/hotels/travel/bobxr-the-st-regis-bora-bora-resort/");
                break;
            case 3:
                webpage = Uri.parse("https://www.visitflorida.com/en-us/cities/miami.html");
                break;
            default:
                webpage = Uri.parse("https://www.android.com");
                break;
        }
        Intent webIntent = new Intent(Intent.ACTION_VIEW, webpage);
        startActivity(webIntent);
        finish();

    }
}