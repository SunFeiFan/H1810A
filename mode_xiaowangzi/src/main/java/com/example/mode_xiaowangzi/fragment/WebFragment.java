package com.example.mode_xiaowangzi.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mode_xiaowangzi.R;
import com.example.mode_xiaowangzi.activity.WebActivity;
import com.example.mode_xiaowangzi.adapter.WebAdapter;
import com.example.mode_xiaowangzi.api.ApiServer;
import com.example.mode_xiaowangzi.bean.WebBean;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
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
public class WebFragment extends Fragment {

    private View view;
    private XRecyclerView mXrl;
    private ArrayList<WebBean.DataBean.DatasBean> mList;
    private WebAdapter mWebAdapter;
    private int mPosition;
    private WebBean.DataBean.DatasBean mDatasBean;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_web, container, false);
        initView(inflate);
        registerForContextMenu(mXrl);
        initData();
        return inflate;
    }

    private static final String TAG = "WebFragment";

    private void initData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiServer.Urli)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiServer myApi = retrofit.create(ApiServer.class);
        Observable<WebBean> data = myApi.getData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<WebBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(WebBean webBean) {
                        List<WebBean.DataBean.DatasBean> datas = webBean.getData().getDatas();
                        mList.addAll(datas);
                        mWebAdapter.setList(mList);
                        mWebAdapter.notifyDataSetChanged();
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
        mWebAdapter = new WebAdapter(getActivity(), mList);
        mXrl.setAdapter(mWebAdapter);
        mXrl.setLayoutManager(new LinearLayoutManager(getActivity()));
        mXrl.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
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
        mWebAdapter.setOnClickListener(new WebAdapter.CallBack() {
            @Override
            public void OnClick(WebBean.DataBean.DatasBean datasBean, int position) {

            }

            @Override
            public void OnLongClick(WebBean.DataBean.DatasBean datasBean,int position) {
                mPosition = position;
                mDatasBean = datasBean;
            }
        });
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 1, "WebView");
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                Intent intent = new Intent(getActivity(),WebActivity.class);
                intent.putExtra("url",mDatasBean.getLink());
                startActivity(intent);
                break;
        }
        return super.onContextItemSelected(item);
    }
}
