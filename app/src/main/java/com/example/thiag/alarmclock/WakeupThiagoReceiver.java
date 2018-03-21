package com.example.thiag.alarmclock;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Date;

/**
 * Created by thiag on 21/03/2018.
 */

public class WakeupThiagoReceiver extends BroadcastReceiver {


    private  static final String TAG = "MyAlarmClock";
    public static final String ACTION = "com.example.thiag.WAKEUP_THIAGO_RECEIVER";

    @Override
    public void onReceive(Context context, Intent intent){
        Log.d(TAG, " Wake up, Thiago!!! " + new Date());
        Intent notifIntent = new Intent(context,MainActivity.class);
        Notifications.create(context, notifIntent, " Time to wake up! ", 1);

    }


}


