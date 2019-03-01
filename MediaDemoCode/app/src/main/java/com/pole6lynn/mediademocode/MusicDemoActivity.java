package com.pole6lynn.mediademocode;

import android.Manifest;
import android.content.pm.PackageManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

public class MusicDemoActivity extends AppCompatActivity implements View.OnClickListener {
    private MediaPlayer mMediaPlayer;
    private Button mStartBtn;
    private Button mPasueBtn;
    private Button mStopBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_activity);
        mStartBtn = findViewById(R.id.start_music);
        mPasueBtn = findViewById(R.id.pause_music);
        mStopBtn = findViewById(R.id.stop_music);
        mStartBtn.setOnClickListener(this);
        mPasueBtn.setOnClickListener(this);
        mStopBtn.setOnClickListener(this);

        mMediaPlayer = new MediaPlayer();
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);
        } else {
            initMediaPlayer();
        }
    }

    private void initMediaPlayer() {
        try {
            File file = new File(Environment.getExternalStorageDirectory(), "music.mp3");
            mMediaPlayer.setDataSource(file.getPath());
            mMediaPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case 0:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    initMediaPlayer();
                } else {
                    Toast.makeText(this, "You denied the permission.", Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start_music:
                if (!mMediaPlayer.isPlaying()) {
                    mMediaPlayer.start();
                }
                break;
            case R.id.pause_music:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.pause();
                }
                break;
            case R.id.stop_music:
                if (mMediaPlayer.isPlaying()) {
                    mMediaPlayer.reset();
                    initMediaPlayer();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mMediaPlayer != null) {
            mMediaPlayer.stop();
            mMediaPlayer.release();
        }
    }
}
