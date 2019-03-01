package com.pole6lynn.primiarydemo;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.pole6lynn.primiarydemo.activity.BaseActivity;

public class BroadcastDemoActivity extends BaseActivity implements View.OnClickListener {
    //private IntentFilter mIntentFilter;
    //private NetWorkChangeReceiver mNetWorkChangeReceiver;

    //private LocalReceiver mLocalReceiver;
    //private LocalBroadcastManager mLocalBroadcastManager;

    private Button mForceOffLineBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.broadcast_demo);
        //mLocalBroadcastManager = LocalBroadcastManager.getInstance(this);

        //mIntentFilter = new IntentFilter();
        //mIntentFilter.addAction("com.pole6lynn.primiarydemo.Local_BROADCAST");
        //mNetWorkChangeReceiver = new NetWorkChangeReceiver();
        //mLocalReceiver = new LocalReceiver();
        //mLocalBroadcastManager.registerReceiver(mLocalReceiver, mIntentFilter);
        //registerReceiver(mNetWorkChangeReceiver, mIntentFilter);

        mForceOffLineBtn = findViewById(R.id.force_offline);
        mForceOffLineBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //mLocalBroadcastManager.unregisterReceiver(mLocalReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.force_offline:
                Intent intent = new Intent("com.pole6lynn.primiarydemo.FORCE_OFFLINE");
                sendBroadcast(intent);
                //sendOrderedBroadcast(intent, null);
                //mLocalBroadcastManager.sendBroadcast(intent);
                break;
        }

    }

    class NetWorkChangeReceiver extends BroadcastReceiver {

        public NetWorkChangeReceiver() {
            super();
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = manager.getActiveNetworkInfo();
            if (info != null && info.isAvailable()) {
                Toast.makeText(context, "network is Available", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "network is Unavailable", Toast.LENGTH_SHORT).show();
            }

        }
    }

    class LocalReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            Toast.makeText(context, "Receive local broadcast.", Toast.LENGTH_SHORT).show();
        }
    }
}
