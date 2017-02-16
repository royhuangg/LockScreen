package com.roy.lockscreen;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

/**
 * Created by Roy on 2017/2/16.
 */

public class widgetProvider extends AppWidgetProvider {

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager,
                         int[] appWidgetIds) {

        Intent intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context,
                0, intent, 0);
        RemoteViews remoteViews = new RemoteViews(context.getPackageName(),
                R.layout.widget_lock);
        remoteViews.setOnClickPendingIntent(R.id.image_widget, pendingIntent);
        appWidgetManager.updateAppWidget(appWidgetIds, remoteViews);

        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }
}
