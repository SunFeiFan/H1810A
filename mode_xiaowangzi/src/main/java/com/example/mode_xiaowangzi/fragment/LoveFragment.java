package com.example.mode_xiaowangzi.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mode_xiaowangzi.R;
import com.example.mode_xiaowangzi.adapter.HomeAdapter;
import com.example.mode_xiaowangzi.adapter.LoveAdapter;
import com.example.mode_xiaowangzi.bean.LoveBean;
import com.example.mode_xiaowangzi.dbutils.DbUtils;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoveFragment extends Fragment {


    private View view;
    private XRecyclerView mXrl;
    private ArrayList<LoveBean> mList;
    private LoveAdapter mLoveAdapter;
    private DbUtils mDbUtils;
    private int positions;
    private LoveBean loveBeans;

    public LoveFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_love, container, false);
        mDbUtils = new DbUtils();
        initView(inflate);
        registerForContextMenu(mXrl);
        initChaxun();
        return inflate;
    }

    private void initChaxun() {
        List<LoveBean> query = mDbUtils.query();
        mList.addAll(query);
        mLoveAdapter.setList(mList);
        mLoveAdapter.notifyDataSetChanged();
    }

    private void initView(View inflate) {
        mXrl = (XRecyclerView) inflate.findViewById(R.id.xrl);
        mList = new ArrayList<>();
        mLoveAdapter = new LoveAdapter(getActivity(), mList);
        mXrl.setAdapter(mLoveAdapter);
        mXrl.setLayoutManager(new LinearLayoutManager(getActivity()));
        mXrl.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mXrl.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                mXrl.refreshComplete();
            }

            @Override
            public void onLoadMore() {
                mXrl.loadMoreComplete();
            }
        });
        mLoveAdapter.setOnClickListener(new LoveAdapter.CallBack() {
            @Override
            public void OnClick(LoveBean loveBean, int position) {

            }

            @Override
            public void OnLongClick(LoveBean loveBean, int position) {
                positions = position;
                loveBeans = loveBean;
            }
        });
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        menu.add(1, 1, 1, "删除");
        super.onCreateContextMenu(menu, v, menuInfo);
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 1:
                mDbUtils.delete(loveBeans);
                mList.remove(positions);
                mLoveAdapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "删除成功~~~", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onContextItemSelected(item);
    }
}
