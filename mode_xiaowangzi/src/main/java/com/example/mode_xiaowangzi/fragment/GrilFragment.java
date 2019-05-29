package com.example.mode_xiaowangzi.fragment;


import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.example.mode_xiaowangzi.R;
import com.example.mode_xiaowangzi.adapter.GrilAdapter;
import com.example.mode_xiaowangzi.bean.GrilBean;
import com.example.mode_xiaowangzi.presenter.GrilPresenter;
import com.example.mode_xiaowangzi.view.GrilIView;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class GrilFragment extends Fragment implements GrilIView {


    private View view;
    private XRecyclerView mXrl;
    private GrilPresenter mGrilPresenter;
    private ArrayList<GrilBean.ResultsBean> mList;
    private GrilAdapter mGrilAdapter;
    private PopupWindow mPopupWindow;
    private int positions;

    public GrilFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_gril, container, false);
        initView(inflate);
        mGrilPresenter = new GrilPresenter(this);
        mGrilPresenter.getGrilIPresenter();
        return inflate;
    }

    private void initView(View inflate) {
        mXrl = (XRecyclerView) inflate.findViewById(R.id.xrl);
        mList = new ArrayList<>();
        mGrilAdapter = new GrilAdapter(getActivity(), mList);
        mXrl.setAdapter(mGrilAdapter);
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
        mGrilAdapter.setOnClickListener(new GrilAdapter.CallBack() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void OnClick(final int position) {

                View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.popupw_item, null);
                mPopupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                //设置背景
                mPopupWindow.setBackgroundDrawable(new ColorDrawable(R.color.c_220000000));
                // <color name="c_220000000">#22000000</color>
                //点击外部消失
                mPopupWindow.setOutsideTouchable(true);
                //水平方向
                mPopupWindow.showAtLocation(mXrl, Gravity.CENTER, 0, 0);
                //                popupWindow.showAsDropDown(inflate);
                inflate.findViewById(R.id.shanchu).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //关闭
                        mList.remove(position);
                        mGrilAdapter.notifyDataSetChanged();
                        Toast.makeText(getActivity(), "删除成功~~~", Toast.LENGTH_SHORT).show();

                        mPopupWindow.dismiss();
                    }
                });
            }

            @Override
            public void OnLongClick(int position) {

            }
        });
    }



    @Override
    public void getIViewDataYes(List<GrilBean.ResultsBean> resultsBeans) {
        mList.addAll(resultsBeans);
        mGrilAdapter.setList(mList);
        mGrilAdapter.notifyDataSetChanged();
    }

    @Override
    public void getIViewDataNo(String string) {

    }
}
