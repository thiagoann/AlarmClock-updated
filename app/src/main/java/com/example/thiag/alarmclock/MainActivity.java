package com.example.thiag.alarmclock;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

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

//        Notifications.createChannel(this);


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
//            Boolean check = false;

            @Override
            public void onClick(View view) {
                Toast myToast;
                myToast = Toast.makeText(getApplicationContext(), " What time do you want to wake up? ", Toast.LENGTH_LONG);
//                if (time_Alarm.equals("")) {
//                    check = false;
//                    Toast.makeText(getApplicationContext(), " What time do you want to wake up? ", Toast.LENGTH_LONG);
//
//                }
            myToast.show();
                onClickSet();
            }
        });

        btn_dismiss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), " Alarm dismissed. ", Toast.LENGTH_SHORT).show();
                onClickDismiss();
            };
        });

        btn_repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), " Alarm set to repeat every day. ", Toast.LENGTH_SHORT).show();
                onClickRepeat();
            }
        });

        btn_snooze.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), " Alarm snoozed for 10 minutes. ", Toast.LENGTH_SHORT).show();
                onClickSnooze();
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
        calendar.set(Calendar.HOUR, 9);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);

        long time = calendar.getTimeInMillis();
        return time;
    }

    public void onClickSet() {
        Intent intent = new Intent(WakeupThiagoReceiver.ACTION);
        //Set an alarm
        AlarmUtils.schedule(this, intent, getTime());
        //sendBroadcastIntent
        Toast.makeText(this, " Alarm set! ", Toast.LENGTH_SHORT).show();

    }

    public void onClickRepeat() {
        Intent intent = new Intent(WakeupThiagoReceiver.ACTION);
        //Repeat an alarm everyday.
        AlarmUtils.scheduleRepeat(this, intent, getTime(), AlarmManager.INTERVAL_DAY);
        //sendBroadcastIntent
        Toast.makeText(getApplicationContext(), " Alarm will repeat everyday. ", Toast.LENGTH_SHORT).show();
    }

    public void onClickDismiss() {
        Intent intent = new Intent(WakeupThiagoReceiver.ACTION);
        //Dismiss an alarm
        AlarmUtils.dismiss(this, intent);
        Toast.makeText(getApplicationContext(), " Alarm dismissed. ", Toast.LENGTH_SHORT).show();




    }

    public void onClickSnooze(){

        int snoozeCounter = 0;
        snoozeCounter++;


        Calendar calendar = Calendar.getInstance();


        long currentTimeMillis = System.currentTimeMillis(); // declarou o tempo atual como long e pegou do sistema

        long nextTimeMillis = (currentTimeMillis + 10 * DateUtils.MINUTE_IN_MILLIS); // declarou o tempo de soneca como proximo alarme ou next time +5 min

        calendar.setTimeInMillis(nextTimeMillis);// Chamou a classe do calendario e setou o tempo do proximo alarme;

        long nextSnoozeTime = calendar.getTimeInMillis(); // declra o tempo de soneca e pega da classe calendario o tempo

        Intent intent = new Intent(getApplicationContext(),WakeupThiagoReceiver.class); //  Chama a tela do despertador

        intent.putExtra("SNOOZE_COUNTER", snoozeCounter);// NÃO ESTA CLARO O CONTEXTO
        PendingIntent pendingIntent = PendingIntent.getBroadcast// NÃO ESTA CLARO O CONTEXO DA CLASSE
                (getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);// Atualiza alguma coisa ...!!

        AlarmManager alarmManager = (AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        Log.d("snooze",  " Snooze set to 10 minutes " + calendar.getTime().toString());// registro de log no sistema

        alarmManager.set(AlarmManager.RTC_WAKEUP, nextSnoozeTime, pendingIntent);// Setando o proximo alarme.

        finish();
    }
}


















