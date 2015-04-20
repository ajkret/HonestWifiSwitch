package com.dersommer.honestwifiswitch;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.widget.RemoteViews;


/**
 * Implementation of App Widget functionality.
 */
public class WifiSwitch extends AppWidgetProvider {

    BroadcastReceiver receiver;

    @Override
    public void onUpdate(Context context, AppWidgetManager manager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
//        final int N = appWidgetIds.length;
//        for (int i = 0; i < N; i++) {
//            updateAppWidget(context, manager, appWidgetIds[i]);
//        }
    }


    @Override
    public void onEnabled(Context context) {
        IntentFilter filter = new IntentFilter();
        //filter.addAction("android.net.wifi.STATE_CHANGE");
        filter.addAction("android.net.wifi.WIFI_STATE_CHANGED"); // http://java-knowhow.blogspot.com.br/2011/11/android-broadcast-receiver-on-wifi.html
        // Enter relevant functionality for when the first widget is created
        final ComponentName thisWidget = new ComponentName(context, this.getClass());

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                AppWidgetManager manager = AppWidgetManager.getInstance(context);
                int[] appWidgetIds = manager.getAppWidgetIds(thisWidget);
                final int N = appWidgetIds.length;
                for (int i = 0; i < N; i++) {
                    updateAppWidget(context, manager, appWidgetIds[i]);
                }
            }
        };
        context.getApplicationContext().registerReceiver(receiver, filter);
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
        super.onDisabled(context);
        if(receiver!=null) {
            context.getApplicationContext().unregisterReceiver(receiver);
            receiver=null;
        }
    }

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {

        WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);

        // Construct the RemoteViews object
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.wifi_switch);
        if(wifiManager.isWifiEnabled())
            views.setImageViewResource(R.id.imageButton,R.drawable.wifi_on);
        else
            views.setImageViewResource(R.id.imageButton,R.drawable.wifi_off);
        //views.setTextViewText(R.id.appwidget_text, widgetText);

        // Instruct the widget manager to update the widget
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }
}


