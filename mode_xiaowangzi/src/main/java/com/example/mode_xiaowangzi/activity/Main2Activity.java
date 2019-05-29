package com.example.mode_xiaowangzi.activity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.example.mode_xiaowangzi.R;
import com.example.mode_xiaowangzi.fragment.CallFragment;
import com.example.mode_xiaowangzi.fragment.HomeFragment;
import com.example.mode_xiaowangzi.fragment.LoveFragment;
import com.example.mode_xiaowangzi.fragment.WebFragment;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class Main2Activity extends AppCompatActivity implements View.OnClickListener {

    /**
     * title
     */
    private TextView mTbTitle;
    private Toolbar mToolbar;
    private LinearLayout mLinear;
    /**
     * 列表
     */
    private Button mBtn1;
    /**
     * 收藏
     */
    private Button mBtn2;
    private FragmentManager mManager;
    private ArrayList<Fragment> mFragments;
    private ProgressBar mProgress;
    private int num = 0;
    /**
     * 跳转到2
     */
    private Button mBtn3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        getFragments();
        initView();
    }

    private void getFragments() {
        mFragments = new ArrayList<>();
        mFragments.add(new CallFragment());
        mFragments.add(new WebFragment());
    }

    private void initView() {
        mTbTitle = (TextView) findViewById(R.id.tb_title);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mLinear = (LinearLayout) findViewById(R.id.linear);
        mBtn1 = (Button) findViewById(R.id.btn1);
        mBtn1.setOnClickListener(this);
        mBtn2 = (Button) findViewById(R.id.btn2);
        mBtn2.setOnClickListener(this);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);



        mManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = mManager.beginTransaction();
        fragmentTransaction.replace(R.id.linear, mFragments.get(0));
        fragmentTransaction.commit();

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.btn1:
                mTbTitle.setText("电话");
                FragmentTransaction ft1 = mManager.beginTransaction();
                ft1.replace(R.id.linear, mFragments.get(0));
                ft1.commit();
                break;
            case R.id.btn2:
                mTbTitle.setText("展示");
                FragmentTransaction ft2 = mManager.beginTransaction();
                ft2.replace(R.id.linear, mFragments.get(1));
                ft2.commit();
                break;

        }
    }



}