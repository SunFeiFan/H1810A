package com.example.mode_xiaowangzi.dbutils;

import com.example.mode_xiaowangzi.bean.LoveBean;
import com.example.mode_xiaowangzi.dao.DaoMaster;
import com.example.mode_xiaowangzi.dao.DaoSession;
import com.example.mode_xiaowangzi.dao.LoveBeanDao;
import com.example.mode_xiaowangzi.myapp.MyApp;

import java.util.List;

public class DbUtils {

    private static DbUtils dbUtils;
    private final LoveBeanDao mLoveBeanDao;

    public DbUtils(){
        DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(MyApp.getMyApp(),"zuoye"+".db");
        DaoMaster daoMaster = new DaoMaster(devOpenHelper.getWritableDatabase());
        DaoSession daoSession = daoMaster.newSession();
        mLoveBeanDao = daoSession.getLoveBeanDao();
    }

    private static DbUtils getDbUtils() {
        if (dbUtils == null) {
            synchronized (DbUtils.class) {
                if (dbUtils == null) {
                    dbUtils = new DbUtils();
                }
            }
        }
        return dbUtils;
    }

    public void insert(LoveBean loveBean){
        if (has(loveBean)){
            return;
        }
        mLoveBeanDao.insert(loveBean);
    }

    public void delete(LoveBean loveBean){
        mLoveBeanDao.delete(loveBean);
    }

    public List<LoveBean> query(){
        return mLoveBeanDao.queryBuilder().list();
    }

    private boolean has(LoveBean loveBean) {
        List<LoveBean> list = mLoveBeanDao.queryBuilder().where(LoveBeanDao.Properties.Url.eq(loveBean.getUrl())).list();
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

}
