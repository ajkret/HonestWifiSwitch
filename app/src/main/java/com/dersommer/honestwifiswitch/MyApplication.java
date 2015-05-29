package com.dersommer.honestwifiswitch;

import android.app.Application;
import android.content.Context;

/**
 * Created by a.kretschmer on 29/05/2015.
 */
public class MyApplication extends Application {
    private static Context context;

    public void onCreate(){
        super.onCreate();
        MyApplication.context = getApplicationContext();
    }

    public static Context getAppContext() {
        return MyApplication.context;
    }
}
