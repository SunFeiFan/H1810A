package com.example.mode_xiaowangzi.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mode_xiaowangzi.R;
import com.example.mode_xiaowangzi.adapter.VpAdapter;
import com.example.mode_xiaowangzi.fragment.GrilFragment;
import com.example.mode_xiaowangzi.fragment.HomeFragment;
import com.example.mode_xiaowangzi.fragment.LoveFragment;
import com.example.mode_xiaowangzi.fragment.UpDataFragment;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private TextView mTv;
    private Toolbar mToolbar;
    private ViewPager mVp;
    private TabLayout mTab;
    private ArrayList<Fragment> mFragments;
    private VpAdapter mVpAdapter;
    private NavigationView mNv;
    private DrawerLayout mDl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mTv = (TextView) findViewById(R.id.tv);
        mTv.setText("首页");
        mNv = (NavigationView) findViewById(R.id.nv);
        mDl = (DrawerLayout) findViewById(R.id.dl);
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("");
        setSupportActionBar(mToolbar);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDl, mToolbar, R.string.app_name, R.string.app_name);
        toggle.syncState();
        mNv.setItemIconTintList(null);
        mNv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                startActivity(new Intent(MainActivity.this,Main3Activity.class));
                return true;
            }
        });
       /* View viewById = mNv.findViewById(R.id.wode);
        viewById.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/
       /* View inflate = LayoutInflater.from(this).inflate(R.menu.menu, null);
        View mWode = inflate.findViewById(R.id.wode);
        mWode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });*/
        mDl.addDrawerListener(toggle);
        mVp = (ViewPager) findViewById(R.id.vp);
        mTab = (TabLayout) findViewById(R.id.tab);

        mFragments = new ArrayList<>();
        mFragments.add(new HomeFragment());
//        mFragments.add(new GrilFragment());
//        mFragments.add(new UpDataFragment());
        mFragments.add(new LoveFragment());

        mVpAdapter = new VpAdapter(getSupportFragmentManager(), mFragments);
        mVp.setAdapter(mVpAdapter);
        mTab.setupWithViewPager(mVp);

        mTab.getTabAt(0).setIcon(R.drawable.selete).setText("首页");
//        mTab.getTabAt(1).setIcon(R.drawable.selete).setText("福利");
//        mTab.getTabAt(2).setIcon(R.drawable.selete).setText("上传");
        mTab.getTabAt(1).setIcon(R.drawable.selete).setText("收藏");

        mTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mTv.setText(tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 1, 1, "跳转到2");
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                startActivity(new Intent(MainActivity.this, Main2Activity.class));
                break;
        }
        return super.onOptionsItemSelected(item);
    }

}
