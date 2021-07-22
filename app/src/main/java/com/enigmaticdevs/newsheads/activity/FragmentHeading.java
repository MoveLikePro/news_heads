package com.enigmaticdevs.newsheads.activity;


import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;

import com.enigmaticdevs.newsheads.model.Article;
import com.enigmaticdevs.newsheads.model.ResponseModel;
import com.enigmaticdevs.newsheads.rests.ApiClient;
import com.enigmaticdevs.newsheads.rests.ApiInterface;


import androidx.constraintlayout.widget.ConstraintLayout;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.enigmaticdevs.newsheads.R;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentHeading extends Fragment{
    private static final String API_KEY = "Enter newsapi.org API key here";

    TextView heading, sourceText, dateText, instructionTextView;
    List<Article> articleList;
    ArrayList<String> titles, description, sources, dates, urlToImage, content, url;
    ArrayList<String> categories;
    Sprite doubleBounce;
    ProgressBar progressBar;
    FloatingActionButton shareNews;
    ImageView upArrow;
    ConstraintLayout headingLayout;

   // private InterstitialAd mInterstitialAd;

    SharedPreferences sharedPreferences;
    int index;
    int i=0;
    int adCount=0;
    public FragmentHeading(){ }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        sharedPreferences=getContext().getSharedPreferences("com.enigmaticdevs.newsheads", Context.MODE_PRIVATE);
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
        View v=inflater.inflate(R.layout.heading_fragment,container,false);
        headingLayout=v.findViewById(R.id.headingLayout);
        shareNews=v.findViewById(R.id.shareNews);
        heading=v.findViewById(R.id.heading);
        sourceText=v.findViewById(R.id.source);
        dateText=v.findViewById(R.id.date);
        //fingerprint=v.findViewById(R.id.fingerprint);
        instructionTextView=v.findViewById(R.id.instructionTextView);
        //instructionTextView.setText("Swipe up and down for next and previous News. Swipe left for News description");
        upArrow=v.findViewById(R.id.upArrow);
        titles=new ArrayList<>();
        description=new ArrayList<>();
        sources=new ArrayList<>();
        dates=new ArrayList<>();
        urlToImage=new ArrayList<>();
        content=new ArrayList<>();
        url=new ArrayList<>();
        articleList=new ArrayList<>();
        categories=new ArrayList<>();
        progressBar = (ProgressBar)v.findViewById(R.id.spin_kit);
        doubleBounce = new CubeGrid();
        doubleBounce.start();
        progressBar.setIndeterminateDrawable(doubleBounce);
        try {
            categories=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("categories",ObjectSerializer.serialize(new ArrayList<String>())));

        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.i("Categories", String.valueOf(categories.size()));

       /* for(i=0; i<categories.size();i++){
            Log.i("I", String.valueOf(i));
            callApi("in", categories.get(i));
        }*/
       callApi(sharedPreferences.getString("countryShort","in"),categories.get(i));


        v.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            public void onSwipeTop() {
                //Toast.makeText(getContext(), "top", Toast.LENGTH_SHORT).show();
                Log.i("Size", String.valueOf(titles.size()));
                if(index>=titles.size()-1){
                    heading.animate().alpha(0f);
                    heading.setText("No More Heads :(");
                    sourceText.setText("");
                    dateText.setText("");
                    heading.animate().alpha(1f).setDuration(500);
                    shareNews.setVisibility(View.GONE);
                    sharedPreferences.edit().putString("description", "Nothing here").apply();
                    sharedPreferences.edit().putString("urlToImage","noImage").apply();
                    sharedPreferences.edit().putString("content","").apply();
                    sharedPreferences.edit().putString("url", "").apply();
                }
                else {
                    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                    alphaAnimation.setDuration(250);
                    alphaAnimation.setRepeatCount(1);
                    alphaAnimation.setRepeatMode(Animation.REVERSE);

                    alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                          /*  adCount++;
                            if(adCount>7) {
                                if (mInterstitialAd.isLoaded()) {
                                    mInterstitialAd.show();
                                    adCount=0;
                                } else {
                                    Log.d("TAG", "The interstitial wasn't loaded yet.");
                                    mInterstitialAd.loadAd(new AdRequest.Builder().build());
                                }
                            }*/
                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            instructionTextView.setText("Swipe Down for previous News");
                            upArrow.animate().rotation(90).setDuration(500);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            Log.i("Index", String.valueOf(index));
                            heading.setText(titles.get(index));
                            sourceText.setText(sources.get(index));
                            dateText.setText(dates.get(index));
                            sharedPreferences.edit().putString("description", description.get(index)).apply();
                            sharedPreferences.edit().putString("urlToImage", urlToImage.get(index)).apply();
                            sharedPreferences.edit().putString("content",content.get(index)).apply();
                            sharedPreferences.edit().putString("url", url.get(index)).apply();
                        }
                    });
                    index++;
                    heading.startAnimation(alphaAnimation);
                    sourceText.startAnimation(alphaAnimation);
                    dateText.startAnimation(alphaAnimation);
                }

            }
            public void onSwipeRight() {
               // Toast.makeText(getContext(), "right", Toast.LENGTH_SHORT).show();
                Log.i("Swipe","Right");
            }
            public void onSwipeLeft() {
                //Toast.makeText(getContext(), "left", Toast.LENGTH_SHORT).show();
                Log.i("Swipe","Left");
            }
            public void onSwipeBottom() {
                // Toast.makeText(getContext(), "bottom", Toast.LENGTH_SHORT).show();
                if (index == 0) {

                } else {
                    AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f, 0.0f);
                    alphaAnimation.setDuration(500);
                    alphaAnimation.setRepeatCount(1);
                    alphaAnimation.setRepeatMode(Animation.REVERSE);

                    alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            upArrow.animate().rotation(180).setDuration(500);
                            instructionTextView.setText("Swipe Left for Description");
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            Log.i("Index", String.valueOf(index));
                            heading.setText(titles.get(index));
                            sourceText.setText(sources.get(index));
                            dateText.setText(dates.get(index));
                            sharedPreferences.edit().putString("description", description.get(index)).apply();
                            sharedPreferences.edit().putString("urlToImage", urlToImage.get(index)).apply();
                            sharedPreferences.edit().putString("content",content.get(index)).apply();
                            sharedPreferences.edit().putString("url", url.get(index)).apply();
                        }
                    });
                    index--;
                    heading.startAnimation(alphaAnimation);
                    sourceText.startAnimation(alphaAnimation);
                    dateText.startAnimation(alphaAnimation);
                }
            }

        });

        shareNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newsTitle=titles.get(index);
                String newsUrl=url.get(index);
                Intent sharingIntent = new Intent(Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = newsTitle+". "+newsUrl+"\n\nTo read more interesting news like this download News Heads. https://play.google.com/store/apps/details?id=com.enigmaticdevs.newsheads";
                String shareSub = "News Heads";
                sharingIntent.putExtra(Intent.EXTRA_SUBJECT, shareSub);
                sharingIntent.putExtra(Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share using"));
            }
        });

     /*   mInterstitialAd = new InterstitialAd(getContext());
        mInterstitialAd.setAdUnitId("ca-app-pub-7745746346619481/5221247022");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                // Load the next interstitial.
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }

        });*/

        return v;
    }


    public void callApi(String country, String category){
        final ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<ResponseModel> call = apiService.getLatestNews(country,category,API_KEY);
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
                toBeTrimmed=articleList.get(i).getPublishedAt();
                toBeStored=toBeTrimmed.split("T");
                dates.add(toBeStored[0]);
                description.add(articleList.get(i).getDescription());
                urlToImage.add(articleList.get(i).getUrlToImage());
                content.add(articleList.get(i).getContent());
                url.add(articleList.get(i).getUrl());
            }

         progressBar.setVisibility(View.GONE);
        doubleBounce.stop();
        if(sharedPreferences.getString("showInstructions","yes").equals("yes")){
            sharedPreferences.edit().putString("showInstructions","no").apply();
            instructionTextView.setVisibility(View.VISIBLE);
            //fingerprint.setVisibility(View.VISIBLE);
            upArrow.setVisibility(View.VISIBLE);
            instructionTextView.setText("Swipe Up for next News");
        }
        else{
            instructionTextView.setVisibility(View.GONE);
            // fingerprint.setVisibility(View.GONE);
            upArrow.setVisibility(View.GONE);
        }
         shareNews.setVisibility(View.VISIBLE);
        heading.setText(titles.get(0));
        sourceText.setText(sources.get(0));
        dateText.setText(dates.get(0));
        sharedPreferences.edit().putString("description", description.get(0)).apply();
        sharedPreferences.edit().putString("urlToImage", urlToImage.get(0)).apply();
        sharedPreferences.edit().putString("content",content.get(0)).apply();
        sharedPreferences.edit().putString("url", url.get(0)).apply();

    }

    SharedPreferences.OnSharedPreferenceChangeListener listener=(sharedPreferences1, key) -> {

        /*if(sharedPreferences1.getString("updateInstruction","").equals("yes")){
            *//*instructionTextView.setText("Swipe Right from Left Edge for Menu");
            //upArrow.animate().translationX(-500).setDuration(500);
            upArrow.setRotation(0);

            ConstraintLayout.LayoutParams params = (ConstraintLayout.LayoutParams) upArrow.getLayoutParams();
            params.horizontalBias = 0f; // here is one modification for example. modify anything else you want :)
            upArrow.setLayoutParams(params);
*//*
        }*/
        if(sharedPreferences1.getString("stopInstruction","").equals("yes")){
            instructionTextView.animate().alpha(0).setDuration(500);
            upArrow.animate().alpha(0).setDuration(500);

        }

    };


}
