package com.enigmaticdevs.newsheads.activity;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;


public class ActionReceiver extends BroadcastReceiver {

    String word="";
    Context mContext;
    @Override
    public void onReceive(Context context, Intent intent) {
//        Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show();
        int notificationId=101;
        word=intent.getStringExtra("word");
        mContext=context;
        //saveToDatabase();

        NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        manager.cancel(notificationId);
        Uri webpage = Uri.parse(word);
        Intent i = new Intent(Intent.ACTION_VIEW, webpage);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
        //This is used to close the notification tray
        Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
        context.sendBroadcast(it);
    }

    /*public void saveToDatabase(){
        Log.i("Action","1");

        Cursor c=sqLiteDatabase.rawQuery("SELECT * FROM savedwords WHERE name='"+word+"'", null);
        if(c.moveToFirst())
        {
            Log.i("Error", "Record exist");
        }
        else
        {
            String sql="INSERT INTO savedwords(name) VALUES(?)";
            SQLiteStatement statement=sqLiteDatabase.compileStatement(sql);
            statement.bindString(1,word);
            statement.execute();
            Log.i("DataSaved","Done");
            Toast.makeText(mContext, "Saved", Toast.LENGTH_SHORT).show();
        }

    }*/


}