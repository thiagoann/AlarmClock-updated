package com.example.thiag.alarmclock;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;

/**
 * Created by thiag on 21/03/2018.
 */

class Notifications {


    public final String ACTION_VIEW = "com.example.thiag.alarmclock";

    static final String CHANNEL_ID = "1";

    @RequiresApi(api = Build.VERSION_CODES.O)

    public static void createChannel(Context context){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O);

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationChannel c = new NotificationChannel(CHANNEL_ID,
                context.getString(R.string.app_name), NotificationManager.IMPORTANCE_DEFAULT);

        c.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
//        c.enableVibration(false);
//        c.enableLights(false);
//        c.setSound();
        manager.createNotificationChannel(c);
    }


        public static void create(Context context, Intent intent, Intent contentTitle, int contentText, int id){

            PendingIntent p = getPendingIntent(context, intent, id);

            //Creates the notification on your phone
            NotificationCompat.Builder b = new NotificationCompat.Builder(context,CHANNEL_ID);
            b.setDefaults(Notification.DEFAULT_ALL); //Pops up the notification
            b.setColor(Color.GREEN);
            b.setAutoCancel(true); //Auto cancels the notification when you click on it
            b.setSmallIcon(R.mipmap.ic_launcher);//Icon
            b.setContentIntent(p);//Intent that will be notified when tapping the notification
            b.setContentTitle((CharSequence) contentTitle);//Notification

            NotificationManagerCompat nm = NotificationManagerCompat.from(context);

            nm.notify(id, b.build());
        }

    private static PendingIntent getPendingIntent(Context context, Intent intent, int id) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addNextIntent(intent);
        stackBuilder.addParentStack(intent.getComponent());
        PendingIntent p = stackBuilder.getPendingIntent(id, PendingIntent.FLAG_UPDATE_CURRENT);
        return p;


    }



    public static void cancel(Context context, int id){
            NotificationManagerCompat nm = NotificationManagerCompat.from(context);

            nm.cancel(id);

    }

    public static void cancelAll(Context context, int id){
        NotificationManagerCompat nm = NotificationManagerCompat.from(context);

        nm.cancelAll();
    }


    public static void create(Context context, Intent notifIntent, String s, int i){

    }
}

