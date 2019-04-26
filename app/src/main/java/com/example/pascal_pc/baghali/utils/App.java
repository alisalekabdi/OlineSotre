package com.example.pascal_pc.baghali.utils;

import android.app.Application;


import com.example.pascal_pc.baghali.model.dataBaseModel.DaoMaster;
import com.example.pascal_pc.baghali.model.dataBaseModel.DaoSession;

import org.greenrobot.greendao.database.Database;

public class App extends Application {

    private static App app;
    private DaoSession daoSession;

    @Override
    public void onCreate() {
        super.onCreate();

        MyDevOpenHelper myDevOpenHelper = new MyDevOpenHelper(this,"Cart.db");
        Database db = myDevOpenHelper.getWritableDb();

        daoSession =new DaoMaster(db).newSession();

        app=this;
    }

    public static App getApp() {
        return app;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }
}
