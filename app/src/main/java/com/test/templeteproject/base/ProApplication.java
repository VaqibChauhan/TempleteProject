package com.test.templeteproject.base;

import android.app.Application;
import android.content.Context;

public class ProApplication extends Application {
    private static ProApplication instance;

    public static Context getContext() {
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance = this;


    }

    public static ProApplication getInstance() {
        return instance;
    }
}
