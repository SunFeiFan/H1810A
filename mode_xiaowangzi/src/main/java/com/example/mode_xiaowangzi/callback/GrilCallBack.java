package com.example.mode_xiaowangzi.callback;

import com.example.mode_xiaowangzi.bean.GrilBean;

import java.util.List;

public interface GrilCallBack {

    void getGrilCallBackYes(List<GrilBean.ResultsBean> resultsBeans);
    void getGrilCallBackNo(String string);

}
