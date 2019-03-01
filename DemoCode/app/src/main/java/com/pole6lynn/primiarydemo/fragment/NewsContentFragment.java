package com.pole6lynn.primiarydemo.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.pole6lynn.primiarydemo.R;

/*
* For double page mode.
* */
public class NewsContentFragment extends Fragment {
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.news_contnet_frag, container, false);
        return mView;
    }

    public void refresh(String newsTitle, String newsContent) {
        View view = mView.findViewById(R.id.visibility_layout);
        view.setVisibility(View.VISIBLE);
        TextView newsTitleText = mView.findViewById(R.id.news_title);
        TextView newsContentText = mView.findViewById(R.id.news_content);
        newsTitleText.setText(newsTitle);
        newsContentText.setText(newsContent);
    }
}
