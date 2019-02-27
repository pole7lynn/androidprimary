package com.pole6lynn.servicedemocode;

import android.Manifest;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mStartDownLoadBtn;
    private Button mPauseDownLoadBtn;
    private Button mCancelDownLaodBtn;

    private DownLoadService.DownLoadBinder mDownLoadBinder;

    private ServiceConnection mConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mDownLoadBinder = (DownLoadService.DownLoadBinder) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStartDownLoadBtn = findViewById(R.id.start_download);
        mPauseDownLoadBtn = findViewById(R.id.pause_download);
        mCancelDownLaodBtn = findViewById(R.id.cancel_download);
        mStartDownLoadBtn.setOnClickListener(this);
        mPauseDownLoadBtn.setOnClickListener(this);
        mCancelDownLaodBtn.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        }

        Intent intent = new Intent(this, DownLoadService.class);
        startService(intent);
        bindService(intent, mConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        if (mDownLoadBinder == null) {
            return;
        }
        switch (v.getId()) {
            case R.id.start_download:
                String uri = "https://vd3.bdstatic.com/mda-jbsme9e45r1uv6ew/mda-jbsme9e45r1uv6ew.mp4";
                mDownLoadBinder.startDownLoad(uri);
                break;
            case R.id.pause_download:
                Log.e("Pole","Pause");
                mDownLoadBinder.pauseDownLoad();
                break;
            case R.id.cancel_download:
                Log.e("Pole","Cancel");
                mDownLoadBinder.cancelDownLoad();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] !=
                        PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You denied the permission.",
                            Toast.LENGTH_SHORT).show();
                    finish();
                }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(mConnection);
    }
}
