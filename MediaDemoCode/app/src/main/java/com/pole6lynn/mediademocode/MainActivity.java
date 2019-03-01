package com.pole6lynn.mediademocode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mNotificationDemoBtn;
    private Button mMediaDemoBtn;
    private Button mMusicDemoBtn;
    private Button mVideoDemoBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotificationDemoBtn = findViewById(R.id.notification_demo);
        mMediaDemoBtn = findViewById(R.id.media_demo);
        mMusicDemoBtn = findViewById(R.id.music_demo);
        mVideoDemoBtn = findViewById(R.id.video_demo);
        mNotificationDemoBtn.setOnClickListener(this);
        mMediaDemoBtn.setOnClickListener(this);
        mMusicDemoBtn.setOnClickListener(this);
        mVideoDemoBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.notification_demo:
                Intent intent = new Intent(this, NotificationDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.media_demo:
                Intent intent1 = new Intent(this, MediaActivity.class);
                startActivity(intent1);
                break;
            case R.id.music_demo:
                Intent intent2 = new Intent(this, MusicDemoActivity.class);
                startActivity(intent2);
                break;
            case R.id.video_demo:
                Intent intent3 = new Intent(this, VideoDemoActivity.class);
                startActivity(intent3);
                break;
            default:
                break;

        }
    }
}
