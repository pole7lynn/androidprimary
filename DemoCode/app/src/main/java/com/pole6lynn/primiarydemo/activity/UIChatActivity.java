package com.pole6lynn.primiarydemo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.pole6lynn.primiarydemo.MsgAdapter;
import com.pole6lynn.primiarydemo.R;
import com.pole6lynn.primiarydemo.uichat.Msg;

import java.util.ArrayList;

public class UIChatActivity extends BaseActivity {
    private static final String TAG = "Pole";

    private ArrayList<Msg> mMsgLists = new ArrayList<>();

    private EditText mEditText;
    private Button mSendBtn;

    private RecyclerView mRecyclerView;
    private MsgAdapter mMsgAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ui_chat);
        if (savedInstanceState != null) {

        }
        initMsg();
        mEditText = findViewById(R.id.input_text);
        mSendBtn = findViewById(R.id.send);

        mRecyclerView = findViewById(R.id.msg_recycler_view);
        mMsgAdapter = new MsgAdapter(mMsgLists);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(manager);
        mRecyclerView.setAdapter(mMsgAdapter);

        mSendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = mEditText.getText().toString();
                if (!"".equals(content)) {
                    Msg msg = new Msg(content, Msg.TYPE_SEND);
                    mMsgLists.add(msg);
                    mMsgAdapter.notifyItemInserted(mMsgLists.size() -1);
                    mRecyclerView.scrollToPosition(mMsgLists.size() -1);
                    mEditText.setText("");
                }
            }
        });
    }

    private void initMsg() {
        Msg msg = new Msg("Begin to chat.", Msg.TYPE_RECEIVED);
        mMsgLists.add(msg);
        Msg msg1 = new Msg("Hello guy.", Msg.TYPE_RECEIVED);
        mMsgLists.add(msg1);
        Msg msg2 = new Msg("Hello. Who is that?", Msg.TYPE_SEND);
        mMsgLists.add(msg2);
        Msg msg3 = new Msg("This is Tom. Nice talking to you. ", Msg.TYPE_RECEIVED);
        mMsgLists.add(msg3);
        Log.i("Pole","size = " + mMsgLists.size());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //outState.putParcelableArrayList("", mMsgLists);
    }

    @Override
    public void onBackPressed() {
        Log.d(TAG, "Back.");
        Intent intent = new Intent();
        intent.putExtra("data_return", "Hello MainActivity.Back from UIChat.");
        setResult(0, intent);
        finish();
    }
}
