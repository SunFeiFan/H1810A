package com.example.mode_xiaowangzi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mode_xiaowangzi.R;

import java.util.ArrayList;

public class ViewPagerActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * Button
     */
    private Button mBt;
    private ImageView mImage;
    /**
     * TextView
     */
    private TextView mTv;
    private long mMillisUntilFinished1;
    private ViewPager mVp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager);
        initView();
//        initView();
//        Count();
    }

   /* private void initView() {
        mVp = (ViewPager) findViewById(R.id.vp);
        mBt = (Button) findViewById(R.id.bt);
        mBt.setOnClickListener(this);
    }*/

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.bt:
                startActivity(new Intent(ViewPagerActivity.this,MainActivity.class));
                break;
        }
    }

    private void initView() {

        final ArrayList<View> views = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            View inflate = LayoutInflater.from(this).inflate(R.layout.item, null, false);
            ImageView image = inflate.findViewById(R.id.image);
            if (i == 0) {
                image.setImageResource(R.drawable.aaa);
            } else if (i == 1) {
                image.setImageResource(R.drawable.aaa);
            } else if (i == 2) {
                image.setImageResource(R.drawable.aaa);
            }
            views.add(inflate);
        }

        mVp = (ViewPager) findViewById(R.id.vp);
        ViewPageAdapter viewPageAdapter = new ViewPageAdapter(views);
        mVp.setAdapter(viewPageAdapter);
        mBt = (Button) findViewById(R.id.bt);
        mBt.setOnClickListener(this);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == views.size() - 1) {
                    mBt.setVisibility(View.VISIBLE);
                } else {
                    mBt.setVisibility(View.GONE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    public class ViewPageAdapter extends PagerAdapter {
        private ArrayList<View> mViews;

        public ViewPageAdapter(ArrayList<View> views) {

            mViews = views;
        }

        @Override
        public int getCount() {
            return mViews.size();
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {

            container.removeView(mViews.get(position));
    //        super.destroyItem(container, position, object);
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            View view = mViews.get(position);
            container.addView(view);
            return view;
        }
    }
}
