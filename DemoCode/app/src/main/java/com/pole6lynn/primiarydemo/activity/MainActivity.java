package com.pole6lynn.primiarydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.pole6lynn.primiarydemo.BroadcastDemoActivity;
import com.pole6lynn.primiarydemo.R;

public class MainActivity extends BaseActivity implements View.OnClickListener {
    private static final String TAG = "Pole";
    public static final int UI_CHAT_REQUEST_CODE = 2;
    public static final int RESULT_OK = 0;

    private Button mListDemoBtn;
    private Button mRecyclerViewDemoBtn;
    private Button mUIChat;
    private Button mFragmentDemoBtn;
    private Button mBroadcastDemoBtn;
    private Button mDataStoreDemo;
    private Button mContentProviderDemoBtn;

    private Bundle mBundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mListDemoBtn = findViewById(R.id.listview_demo);
        mRecyclerViewDemoBtn = findViewById(R.id.recyclerview_demo);
        mUIChat = findViewById(R.id.ui_chat_demo);
        mFragmentDemoBtn = findViewById(R.id.fragment_demo);
        mBroadcastDemoBtn = findViewById(R.id.broadcast_demo);
        mDataStoreDemo = findViewById(R.id.data_store_demo);
        mContentProviderDemoBtn = findViewById(R.id.content_provider_demo);

        mListDemoBtn.setOnClickListener(this);
        mRecyclerViewDemoBtn.setOnClickListener(this);
        mUIChat.setOnClickListener(this);
        mFragmentDemoBtn.setOnClickListener(this);
        mBroadcastDemoBtn.setOnClickListener(this);
        mDataStoreDemo.setOnClickListener(this);
        mContentProviderDemoBtn.setOnClickListener(this);

        if (savedInstanceState != null) {
            mBundle = savedInstanceState;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.listview_demo:
                Intent intent = new Intent(MainActivity.this, ListViewDemoActivity.class);
                startActivity(intent);
                break;
            case R.id.recyclerview_demo:
                Intent intent1 = new Intent(MainActivity.this, RecyclerViewActivity.class);
                startActivity(intent1);
                break;
            case R.id.ui_chat_demo:
                Intent intent2 = new Intent(MainActivity.this, UIChatActivity.class);
                startActivityForResult(intent2, UI_CHAT_REQUEST_CODE);
            case R.id.fragment_demo:
                Intent intent3 = new Intent(MainActivity.this, FragmentDemoActivity.class);
                startActivity(intent3);
                break;
            case R.id.broadcast_demo:
                Intent intent4 = new Intent(MainActivity.this, BroadcastDemoActivity.class);
                startActivity(intent4);
                break;
            case R.id.data_store_demo:
                Intent intent5 = new Intent(MainActivity.this, DataStoreDemoActivity.class);
                startActivity(intent5);
                break;
            case R.id.content_provider_demo:
                Intent intent6 = new Intent(MainActivity.this, ContentProviderActivity.class);
                startActivity(intent6);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case UI_CHAT_REQUEST_CODE:
                if (resultCode == RESULT_OK) {
                    //Get the return data.
                    String returnData = data.getStringExtra("data_return");
                    Log.d(TAG, returnData);
                }
                break;
        }
    }
}
