package com.example.mode_xiaowangzi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mode_xiaowangzi.R;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private long mMillisUntilFinished1;
    /**
     * Button
     */
    private Button mBt;
    private ImageView mImage;
    /**
     * TextView
     */
    private TextView mTv;
    private CountDownTimer mCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        Count();
    }

    private void initView() {
        mBt = (Button) findViewById(R.id.bt);
        mBt.setOnClickListener(this);
        mImage = (ImageView) findViewById(R.id.image);
        mTv = (TextView) findViewById(R.id.tv);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.animation);
        mImage.setAnimation(animation);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt:
                mCountDownTimer.cancel();
                startActivity(new Intent(LoginActivity.this, ViewPagerActivity.class));
                finish();
                break;
        }
    }

    public void Count() {
        mCountDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                mMillisUntilFinished1 = millisUntilFinished;
                mTv.setText(mMillisUntilFinished1 / 1000 + "s");
            }

            @Override
            public void onFinish() {
                startActivity(new Intent(LoginActivity.this, ViewPagerActivity.class));
                finish();
            }
        };
        mCountDownTimer.start();
    }
}
