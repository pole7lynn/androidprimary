package com.pole6lynn.mediademocode;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import android.widget.VideoView;

import java.io.File;

public class VideoDemoActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mPlayBtn;
    private Button mPauseBtn;
    private Button mReplayBtn;
    private VideoView mVideoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_activity);
        mPlayBtn = findViewById(R.id.play_video);
        mPauseBtn = findViewById(R.id.pause_video);
        mReplayBtn = findViewById(R.id.replay_video);
        mPlayBtn.setOnClickListener(this);
        mPauseBtn.setOnClickListener(this);
        mReplayBtn.setOnClickListener(this);
        mVideoView = findViewById(R.id.video_view);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            initVideo();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.play_video:
                if (!mVideoView.isPlaying()) {
                    mVideoView.start();
                }
                break;
            case R.id.pause_video:
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                }
                break;
            case R.id.replay_video:
                if (mVideoView.isPlaying()) {
                    mVideoView.resume();
                }
                break;
            default:
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initVideo();
                } else {
                    Toast.makeText(this, "You denied the permission.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    private void initVideo() {
        File file = new File(Environment.getExternalStorageDirectory(), "VID20181115165444.mp4");
        mVideoView.setVideoPath(file.getPath());

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mVideoView != null) {
            mVideoView.suspend();
        }
    }
}
