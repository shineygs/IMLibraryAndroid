package com.uushixun.imlibraryandroid;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.uushixun.client.IMClient;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "MainActivity";

    private TextView textView;

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

        textView = (TextView) findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IMClient.getInstance().sendMsgToServer("clent msg");
            }
        });
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
