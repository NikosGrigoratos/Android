package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button buttonStart;
    private Button buttonStop;

    private WifiManager wifiManager;

    Uri authority = Uri.parse("content://com.example.project2/location_data");

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        final ContentResolver contentResolver = this.getContentResolver();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, PackageManager.PERMISSION_GRANTED);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, PackageManager.PERMISSION_GRANTED);

        wifiManager = (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);

        buttonStart = findViewById(R.id.startService);
        buttonStop = findViewById(R.id.stopService);

        buttonStart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(wifiManager.isWifiEnabled()){
                    startService(new Intent(getBaseContext(), Services.class));
                }else{
                    Toast.makeText(getApplicationContext(), "You need to enable your Wifi", Toast.LENGTH_LONG).show();
                    stopService(new Intent(getBaseContext(), Services.class));
                }
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                stopService(new Intent(getBaseContext(), Services.class));
            }
        });

    }

    protected void onStart(){
        super.onStart();
        IntentFilter intentFilter = new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(wifiStateReceiver, intentFilter);
    }

    protected void onStop(){
        super.onStop();
        unregisterReceiver(wifiStateReceiver);
    }

    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiStateExtra = intent.getIntExtra(wifiManager.EXTRA_WIFI_STATE, WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiStateExtra){
                case WifiManager.WIFI_STATE_ENABLED:
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    Toast.makeText(getApplicationContext(), "You need to enable your Wifi, locator stopped", Toast.LENGTH_LONG).show();
                    stopService(new Intent(getBaseContext(), Services.class));
                    break;

            }
        }
    };

}
