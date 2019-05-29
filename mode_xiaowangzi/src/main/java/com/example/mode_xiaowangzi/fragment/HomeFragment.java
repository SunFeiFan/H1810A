package com.example.mode_xiaowangzi.fragment;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.mode_xiaowangzi.R;
import com.example.mode_xiaowangzi.adapter.HomeAdapter;
import com.example.mode_xiaowangzi.api.ApiServer;
import com.example.mode_xiaowangzi.bean.GrilBean;
import com.example.mode_xiaowangzi.bean.LoveBean;
import com.example.mode_xiaowangzi.dbutils.DbUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.youth.banner.Banner;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment {

    private View view;
    private XRecyclerView mXrl;
    private ArrayList<GrilBean.ResultsBean> mList;
    private HomeAdapter mHomeAdapter;
    private List<GrilBean.ResultsBean> mResults;
    private View mBanner1;
    private int positions;
    private DbUtils mDbUtils;
    private Banner ban;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_home, container, false);
        mDbUtils = new DbUtils();
        initView(inflate);
        initData();
        registerForContextMenu(mXrl);
        return inflate;
    }

    private static final String TAG = "HomeFragment";

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServer.getGrilUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofit.create(ApiServer.class)
                .getGrilData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<GrilBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(GrilBean grilBean) {
                        mResults = grilBean.getResults();
                        mList.addAll(mResults);
                        mHomeAdapter.setList(mList);
                        //设置轮播图
                        ban.setImages(mList).setImageLoader(new ImageLoader() {
                            @Override
                            public void displayImage(Context context, Object path, ImageView imageView) {
                                GrilBean.ResultsBean grilBean = (GrilBean.ResultsBean) path;
                                String url = grilBean.getUrl();
                                Glide.with(context).load(url).into(imageView);
                            }
                        }).start();

                        mHomeAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: ");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    private void initView(View inflate) {
        mXrl = (XRecyclerView) inflate.findViewById(R.id.xrl);
        mList = new ArrayList<>();
        mHomeAdapter = new HomeAdapter(getActivity(), mList);
        //获取Banner的xml
        mBanner1 = LayoutInflater.from(getActivity()).inflate(R.layout.home_item, null);
        //绑定Banner
        mXrl.addHeaderView(mBanner1);
        //找到Banner的id
        ban = mBanner1.findViewById(R.id.banner_item);
        mXrl.setAdapter(mHomeAdapter);
        mXrl.setLayoutManager(new LinearLayoutManager(getActivity()));
        mXrl.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        //下拉刷新，上拉加载
        mXrl.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                initData();
                mXrl.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mXrl.loadMoreComplete();
            }
        });
        //点击和长按事件
        mHomeAdapter.setOnClickListener(new HomeAdapter.CallBack() {
            @Override
            public void OnClick(GrilBean.ResultsBean resultsBean, int position) {
                LoveBean loveBean = new LoveBean();
                loveBean.setLongid(null);
                loveBean.setPublishedAt(resultsBean.getPublishedAt());
                loveBean.setUrl(resultsBean.getUrl());
                mDbUtils.insert(loveBean);
                mHomeAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "收藏成功~~~", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void OnLongClick(GrilBean.ResultsBean resultsBean, int position) {
                positions = position;

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.add(1, 1, 1, "删除");
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                mList.remove(positions);
                mHomeAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "删除成功~~~", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    //上下文菜单
//    registerForContextMenu(mXrl);   在 onCreateView  中注册
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 1, "删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    //删除子条目并刷新
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                mList.remove(positions);
                mHomeAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "删除成功~~~", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
