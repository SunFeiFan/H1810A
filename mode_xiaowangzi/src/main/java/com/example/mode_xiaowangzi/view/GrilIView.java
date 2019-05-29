package com.example.mode_xiaowangzi.view;

import com.example.mode_xiaowangzi.bean.GrilBean;

import java.util.List;

public interface GrilIView {

    void getIViewDataYes(List<GrilBean.ResultsBean> resultsBeans);
    void getIViewDataNo(String string);

}
