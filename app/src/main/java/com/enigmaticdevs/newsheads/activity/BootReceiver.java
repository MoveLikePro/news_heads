package com.enigmaticdevs.newsheads.activity;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.util.Calendar;

import static android.content.Context.ALARM_SERVICE;

public class BootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals("android.intent.action.BOOT_COMPLETED")){
            setAlarm(context);
        }
    }

    private void setAlarm(Context context) {
        Intent intent = new Intent(context, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        Log.d("before", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        int curHr = calendar.get(Calendar.HOUR_OF_DAY);
        /*if(curHr>9)
           calendar.add(Calendar.HOUR,2);*/
        //calendar.set(Calendar.HOUR_OF_DAY, 22);
        //calendar.set(Calendar.MINUTE, 48);
        Log.d("after", String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)));
        assert alarmManager != null;
        alarmManager.cancel(pendingIntent);
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis()+60*60*1000, 180*60*1000, pendingIntent);
    }
}
