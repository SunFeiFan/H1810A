package com.example.mode_xiaowangzi.presenter;

import com.example.mode_xiaowangzi.bean.GrilBean;
import com.example.mode_xiaowangzi.callback.GrilCallBack;
import com.example.mode_xiaowangzi.model.GrilIModel;
import com.example.mode_xiaowangzi.model.GrilModel;
import com.example.mode_xiaowangzi.view.GrilIView;

import java.util.List;

public class GrilPresenter implements GrilIPresenter {

    GrilIModel mGrilIModel;
    GrilIView mGrilIView;

    public GrilPresenter(  GrilIView grilIView) {
        mGrilIModel = new GrilModel();
        mGrilIView = grilIView;
    }

    @Override
    public void getGrilIPresenter() {
        mGrilIModel.getIModelData(new GrilCallBack() {
            @Override
            public void getGrilCallBackYes(List<GrilBean.ResultsBean> resultsBeans) {
                mGrilIView.getIViewDataYes(resultsBeans);
            }

            @Override
            public void getGrilCallBackNo(String string) {
                mGrilIView.getIViewDataNo(string);
            }
        });
    }
}
