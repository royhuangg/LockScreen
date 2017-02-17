package com.roy.lockscreen.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.roy.lockscreen.R;
import com.roy.lockscreen.activity.LockScreenActivity;

/**
 * Created by Roy on 2017/2/16.
 */

public class widgetProvider extends AppWidgetProvider {

    @Override
    public void onReceive(Context context, Intent intent) {
        super.onReceive(context, intent);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        Intent intent = new Intent(context, LockScreenActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, intent, 0);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_lock);
        remoteViews.setOnClickPendingIntent(R.id.image_icon, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
