package com.enigmaticdevs.newsheads.activity;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import com.enigmaticdevs.newsheads.R;
import com.enigmaticdevs.newsheads.model.Article;
import com.enigmaticdevs.newsheads.model.ResponseModel;
import com.enigmaticdevs.newsheads.rests.ApiClient;
import com.enigmaticdevs.newsheads.rests.ApiInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationReceiver extends BroadcastReceiver {

    List<Article> articleList;
    ArrayList<String> categories, titles, url, sources;
    SharedPreferences sharedPreferences;
    String notificationTitle, notificationUrl, notificationSource;
    int index=0;
    int i=0;
    Context myContext;
    Intent myIntent;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.i("Ran","RAN");
        myContext=context;
        myIntent=intent;
        sharedPreferences=context.getSharedPreferences("com.enigmaticdevs.newsheads",Context.MODE_PRIVATE);
        Log.i("DOY", String.valueOf(sharedPreferences.getInt("dayOfYear",0)));
        processData();
        Intent intentAction = new Intent(context,ActionReceiver.class);

        intentAction.putExtra("word", notificationUrl);
        PendingIntent pIntentlogin = PendingIntent.getBroadcast(context,1,intentAction,PendingIntent.FLAG_UPDATE_CURRENT);

        intent=new Intent(context,SplashActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(context,0,intent,PendingIntent.FLAG_ONE_SHOT);

        if(!titles.isEmpty()) {
            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "NotificationChannelId")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(notificationSource)
                    //.setContentText(notificationTitle)
                    .setSmallIcon(R.drawable.app_icon)
                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                    .setContentIntent(pendingIntent)
                    .setAutoCancel(true)
                    .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(notificationTitle))
                    .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
                    .addAction(R.drawable.app_icon, "Read more", pIntentlogin);
            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
            // notificationId is a unique int for each notification that you must define
            notificationManager.notify(101, builder.build());
        }

    }

    public void callApi(String country, String category){
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel> call = apiService.getLatestNews(country,category,"2a75f3dbcae446c4868c3e50e889dab7");
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel>call, Response<ResponseModel> response) {
                if(response.body().getStatus().equals("ok")) {
                    //articleList = response.body().getArticles();y
                    articleList.addAll(response.body().getArticles());
                    i++;
                    if(i<categories.size()){
                        callApi("in", categories.get(i));
                    }
                    else{
                        Log.i("Yes",articleList.toString());
                        Collections.shuffle(articleList);
                        getDataFromArticles();
                    }

                   /* if(i>=categories.size()){
                        Log.i("IinApi", String.valueOf(i));



                    }*/
                    //Log.i("NewArticles", articleList.toString());
                }
            }
            @Override
            public void onFailure(Call<ResponseModel>call, Throwable t) {
                Log.e("out", t.toString());
            }
        });
    }

    public void getDataFromArticles(){
        for(int i=0; i<articleList.size();i++){
            // Log.i("Title",articleList.get(i).getTitle());
            String toBeTrimmed=articleList.get(i).getTitle();
            String[] toBeStored=toBeTrimmed.split(" - ");
            titles.add(toBeStored[0]);
            sources.add(articleList.get(i).getSource().getName());
            //toBeTrimmed=articleList.get(i).getPublishedAt();
            //toBeStored=toBeTrimmed.split("T");
            //dates.add(toBeStored[0]);
            //description.add(articleList.get(i).getDescription());
            //urlToImage.add(articleList.get(i).getUrlToImage());
            //content.add(articleList.get(i).getContent());
            url.add(articleList.get(i).getUrl());
        }
        Log.i("NotificationSize",String.valueOf(titles.size()));
        Log.i("NotificationTitles",titles.toString());
        try {
            sharedPreferences.edit().putString("notificationTitles",ObjectSerializer.serialize(titles)).apply();
            sharedPreferences.edit().putString("notificationUrl",ObjectSerializer.serialize(url)).apply();
            sharedPreferences.edit().putString("notificationSource",ObjectSerializer.serialize(sources)).apply();
        } catch (IOException e) {
            e.printStackTrace();
        }
        notificationTitle=titles.get(index);
        notificationUrl=url.get(index);
        notificationSource=sources.get(index);
    }

    public void processData(){
        Calendar calendar = Calendar.getInstance();
        categories=new ArrayList<>();
        titles=new ArrayList<>();
        url=new ArrayList<>();
        articleList=new ArrayList<>();
        sources=new ArrayList<>();
        if(calendar.get(Calendar.DAY_OF_YEAR) != sharedPreferences.getInt("dayOfYear", 0)){
            Log.i("Called","if");
            sharedPreferences.edit().putInt("dayOfYear",calendar.get(Calendar.DAY_OF_YEAR)).apply();
            index=0;
            sharedPreferences.edit().putInt("notificationIndex", index).apply();
            try {
                categories=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("categories",ObjectSerializer.serialize(new ArrayList<String>())));

            } catch (IOException e) {
                e.printStackTrace();
            }
            callApi(sharedPreferences.getString("countryShort","in"),categories.get(i));
        }else {
            Log.i("Called","else");
            index=sharedPreferences.getInt("notificationIndex",0);

            try {
                titles=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notificationTitles",ObjectSerializer.serialize(new ArrayList<String>())));
                url=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notificationUrl",ObjectSerializer.serialize(new ArrayList<String>())));
                sources=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("notificationSource",ObjectSerializer.serialize(new ArrayList<String>())));
            } catch (IOException e) {
                e.printStackTrace();
            }
            notificationTitle=titles.get(index);
            notificationUrl=url.get(index);
            notificationSource=sources.get(index);

            index++;
            sharedPreferences.edit().putInt("notificationIndex", index).apply();


        }
    }
}
