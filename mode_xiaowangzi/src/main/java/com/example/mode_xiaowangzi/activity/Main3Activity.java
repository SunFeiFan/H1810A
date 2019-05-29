package com.example.mode_xiaowangzi.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.mode_xiaowangzi.R;

public class Main3Activity extends AppCompatActivity {

    private TextView mTv;
    private Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        initView();
    }

    private void initView() {
        mTv = (TextView) findViewById(R.id.tv);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mTv.setText("我的");
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
    }
}
