package com.uushixun.imlibraryandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        IntentFilter intentStatus = new IntentFilter("com.nuanxinli.BROADCAST.onServiceStatusConnectChanged");
        registerReceiver(statusbr,intentStatus);

        IntentFilter intentMsg = new IntentFilter("com.nuanxinli.BROADCAST.onMessageResponse");
        registerReceiver(msgbr,intentMsg);

        Intent intent = new Intent(this,IMService.class);
        startService(intent);
    }

    private BroadcastReceiver statusbr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive: " + intent.getIntExtra("StatusCode",0));
        }
    };

    private BroadcastReceiver msgbr = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.i(TAG, "onReceive: " + intent.getStringExtra("inPacket"));
        }
    };

    @Override
    protected void onDestroy() {
        unregisterReceiver(statusbr);
        unregisterReceiver(msgbr);
        super.onDestroy();
    }
}
