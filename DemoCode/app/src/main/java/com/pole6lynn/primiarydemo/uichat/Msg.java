package com.pole6lynn.primiarydemo.uichat;

import android.os.Parcel;

public class Msg {
    public static final int TYPE_RECEIVED = 0;
    public static final int TYPE_SEND = 1;

    private String mMsgContent;
    private int mType;

    public Msg(String content, int type) {
        mMsgContent = content;
        mType = type;
    }

    public String getContent() {
        return mMsgContent;
    }

    public int getType() {
        return mType;
    }

}
