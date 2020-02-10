package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Service;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    Button buttonStart;
    Button buttonStop;
    Button buttonDb;

    Uri authority = Uri.parse("content://com.example.project2/location_data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final ContentResolver contentResolver = this.getContentResolver();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        buttonStart = findViewById(R.id.startService);
        buttonStop = findViewById(R.id.stopService);
        buttonDb = findViewById(R.id.dbButton);

        buttonStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startService(new Intent(getBaseContext(), Services.class));
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                stopService(new Intent(getBaseContext(), Services.class));
            }
        });

        buttonDb.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Cursor c = contentResolver.query(authority, null, null, null, null);
                Log.e("Results", "Query " + c.toString());
            }
        });
    }
}
