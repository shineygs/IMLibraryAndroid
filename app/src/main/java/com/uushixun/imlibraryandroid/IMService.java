package com.uushixun.imlibraryandroid;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import com.uushixun.client.IMClient;
import com.uushixun.client.listener.ChatServiceListener;
import com.uushixun.client.mode.SocketConfig;

public class IMService extends Service {

    private IMClient client;

    @Override
    public void onCreate() {
        connect();
    }

    private void connect(){
        SocketConfig config = new SocketConfig();
        config.host = "10.10.80.94";
        config.port = 9090;
        client = IMClient.getInstance(config, new ChatServiceListener() {

            @Override
            public void onServiceStatusConnectChanged(int statusCode) {		//连接状态监听
                Intent intent=new Intent("com.nuanxinli.BROADCAST.onServiceStatusConnectChanged");
                intent.putExtra("StatusCode", statusCode);
                sendBroadcast(intent);
            }

            @Override
            public void onMessageResponse(String inPacket) {	//消息接收
                Intent intent=new Intent("com.nuanxinli.BROADCAST.onMessageResponse");
                intent.putExtra("inPacket", inPacket);
                sendBroadcast(intent);
            }
        });

        client.connect();//连接服务器
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
