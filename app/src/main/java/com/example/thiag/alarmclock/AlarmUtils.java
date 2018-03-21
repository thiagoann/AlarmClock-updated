package com.example.thiag.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;


/**
 * Created by thiag on 21/03/2018.
 */

public class AlarmUtils {

    private static final String TAG = "MyAlarmClock";

    //Set an alarm for the day/time chosen by the user
    public static void schedule(Context context, Intent intent, long triggerAtMillis) {
        PendingIntent p = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmclock = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmclock.set(AlarmManager.RTC_WAKEUP, triggerAtMillis,p);
        Log.d("MyAlarmClock", " Alarm set! ");

    }


    //Set an alarm with the repeat option
    public  static void scheduleRepeat(Context context, Intent intent, long triggerAtMillis, long intervalMillis){
    PendingIntent p = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmclock = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        alarmclock.setInexactRepeating(AlarmManager.RTC_WAKEUP, triggerAtMillis, intervalMillis, p);
        Log.d("MyAlarmClock", " Alarm is going to repeat everyday. ");

    }

    //Dismiss an alarm
    public static void dismiss(Context context, Intent intent){
        AlarmManager alarmclock = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        PendingIntent p = PendingIntent.getBroadcast(context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmclock.cancel(p);
        Log.d("MyAlarmClock", " Alarm dismissed. ");

    }
}
