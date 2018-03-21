package com.example.thiag.alarmclock;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;


import java.time.Clock;
import java.util.Calendar;

import static java.util.Calendar.AM;
import static java.util.Calendar.HOUR_OF_DAY;

public class MainActivity extends AppCompatActivity {

     TimePicker time_Alarm;
     Button btn_set, btn_dismiss, btn_repeat, btn_snooze;
     String format = " ";
     Calendar calendar;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Notifications.createChannel(this);


        time_Alarm = findViewById(R.id.time_Alarm);
        btn_set = findViewById(R.id.btn_set);
        btn_dismiss.findViewById(R.id.btn_dismiss);
        btn_repeat.findViewById(R.id.btn_repeat);
        btn_snooze = findViewById(R.id.btn_snooze);
        calendar = Calendar.getInstance();

        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        showTime(hour, minute);


        btn_set.setOnClickListener(new View.OnClickListener() {
            Boolean check = false;

            @Override
            public void onClick(View view) {
                if (time_Alarm.equals("")) {
                    check = false;
                    Toast.makeText(getApplicationContext(), " What time do you want to wake up? ", Toast.LENGTH_LONG);

                }
            }
        });

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), " Alarm dismissed. ", Toast.LENGTH_SHORT);
            }
        });

        btn_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), " Alarm set to repeat every day. ", Toast.LENGTH_SHORT);
            }
        });

        btn_snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), " Alarm snoozed for 10 minutes. ", Toast.LENGTH_SHORT);
            }
        });

    }


    public void setTime(View view) {
        int hour = time_Alarm.getCurrentHour();
        int min = time_Alarm.getCurrentMinute();
        showTime(hour, min);
    }

    public void showTime(int hour, int min) {
        if (hour == 0) {
            hour += 12;
            format = "AM";
        } else if (hour == 12) {
            format = "PM";
        } else if (hour > 12) {
            hour -= 12;
            format = "PM";
        } else {
            format = "AM";
        }


    }

    //Date/time to set an alarm
    public long getTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, 9);
        calendar.set(Calendar.MINUTE, 00);
        long time = calendar.getTimeInMillis();
        return time;
    }

    public void onClickSet(View view) {
        Intent intent = new Intent(WakeupThiagoReceiver.ACTION);
        //Set an alarm
        AlarmUtils.schedule(this, intent, getTime());
        //sendBroadcastIntent
        Toast.makeText(this, " Alarm set! ", Toast.LENGTH_SHORT).show();

    }

    public void onClickRepeat(View view) {
        Intent intent = new Intent(WakeupThiagoReceiver.ACTION);
        //Repeat an alarm everyday.
        AlarmUtils.scheduleRepeat(this, intent, getTime(), AlarmManager.INTERVAL_DAY);
        //sendBroadcastIntent
        Toast.makeText(getApplicationContext(), " Alarm will repeat everyday. ", Toast.LENGTH_SHORT).show();
    }

    public void onClickDismiss(View view) {
        Intent intent = new Intent(WakeupThiagoReceiver.ACTION);
        //Dismiss an alarm
        AlarmUtils.dismiss(this, intent);
        Toast.makeText(getApplicationContext(), " Alarm dismissed. ", Toast.LENGTH_SHORT).show();


    }


}

















