package com.enigmaticdevs.newsheads.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.enigmaticdevs.newsheads.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    TextView business, entertainment, health, science, sports, technology, skip;
    FloatingActionButton fab;
    ArrayList<String> categories;
    SharedPreferences sharedPreferences;
    String theme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences=this.getSharedPreferences("com.enigmaticdevs.newsheads", MODE_PRIVATE);
        if(sharedPreferences.getString("Theme","dark").equals("dark")) {
            setTheme(R.style.AppTheme);
            theme="dark";
        }else{
            setTheme(R.style.LightTheme);
            theme="light";
        }
        setContentView(R.layout.activity_category);
        business=findViewById(R.id.business);
        entertainment=findViewById(R.id.entertainment);
        health=findViewById(R.id.health);
       // politics=findViewById(R.id.politics);
        science=findViewById(R.id.science);
        sports=findViewById(R.id.sports);
        technology=findViewById(R.id.technology);
        skip=findViewById(R.id.skip);
        fab=findViewById(R.id.fab);
        categories=new ArrayList<>();
        if(!theme.equals("dark"))
           fab.setImageResource(R.drawable.arrow_white);
        fab.setScaleX(0f);
        fab.setScaleY(0f);
        skip.setScaleX(1f);
        skip.setScaleY(1f);
        highlightTextViews();
    }

    private void highlightTextViews() {
        try {
            categories=(ArrayList<String>) ObjectSerializer.deserialize(sharedPreferences.getString("categories",ObjectSerializer.serialize(new ArrayList<String>())));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(categories.contains("business")){
            if(theme.equals("dark"))
                business.setTextColor(getColor(R.color.colorPrimary));
            else
                business.setTextColor(getColor(R.color.colorPrimaryDark));
        }
        if(categories.contains("entertainment")){
            if(theme.equals("dark"))
                entertainment.setTextColor(getColor(R.color.colorPrimary));
            else
                entertainment.setTextColor(getColor(R.color.colorPrimaryDark));
        }
        if(categories.contains("health")){
            if(theme.equals("dark"))
                health.setTextColor(getColor(R.color.colorPrimary));
            else
                health.setTextColor(getColor(R.color.colorPrimaryDark));
        }
       /* if(categories.contains("politics")){
            if(theme.equals("dark"))
                politics.setTextColor(getColor(R.color.colorPrimary));
            else
                politics.setTextColor(getColor(R.color.colorPrimaryDark));
        }*/
        if(categories.contains("science")){
            if(theme.equals("dark"))
                science.setTextColor(getColor(R.color.colorPrimary));
            else
                science.setTextColor(getColor(R.color.colorPrimaryDark));
        }
        if(categories.contains("sports")){
            if(theme.equals("dark"))
                sports.setTextColor(getColor(R.color.colorPrimary));
            else
                sports.setTextColor(getColor(R.color.colorPrimaryDark));
        }
        if(categories.contains("technology")){
            if(theme.equals("dark"))
                technology.setTextColor(getColor(R.color.colorPrimary));
            else
                technology.setTextColor(getColor(R.color.colorPrimaryDark));
        }
    }

    public void businessSelected(View view) {
        if(!categories.contains("business")){
            categories.add("business");
            if(theme.equals("dark"))
               business.setTextColor(getColor(R.color.colorPrimary));
            else
                business.setTextColor(getColor(R.color.colorPrimaryDark));
        }
        else{
            categories.remove("business");
            business.setTextColor(getColor(R.color.colorGrey));
        }
        showOrHideFab();
    }

    public void entertainmentSelected(View view) {
        if(!categories.contains("entertainment")){
            categories.add("entertainment");
            if(theme.equals("dark"))
                entertainment.setTextColor(getColor(R.color.colorPrimary));
            else
                entertainment.setTextColor(getColor(R.color.colorPrimaryDark));
        }
        else{
            categories.remove("entertainment");
            entertainment.setTextColor(getColor(R.color.colorGrey));
        }
        showOrHideFab();
    }

    public void healthSelected(View view) {
        if(!categories.contains("health")){
            categories.add("health");
            if(theme.equals("dark"))
                health.setTextColor(getColor(R.color.colorPrimary));
            else
                health.setTextColor(getColor(R.color.colorPrimaryDark));
        }
        else{
            categories.remove("health");
            health.setTextColor(getColor(R.color.colorGrey));
        }
        showOrHideFab();
    }

   /* public void politicsSelected(View view) {
        if(!categories.contains("politics")){
            categories.add("politics");
            if(theme.equals("dark"))
                politics.setTextColor(getColor(R.color.colorPrimary));
            else
                politics.setTextColor(getColor(R.color.colorPrimaryDark));
        }
        else{
            categories.remove("politics");
            politics.setTextColor(getColor(R.color.colorGrey));
        }
        showOrHideFab();
    }*/

    public void scienceSelected(View view) {
        if(!categories.contains("science")){
            categories.add("science");
            if(theme.equals("dark"))
                science.setTextColor(getColor(R.color.colorPrimary));
            else
                science.setTextColor(getColor(R.color.colorPrimaryDark));
        }
        else{
            categories.remove("science");
            science.setTextColor(getColor(R.color.colorGrey));
        }
        showOrHideFab();
    }

    public void sportsSelected(View view) {
        if(!categories.contains("sports")){
            categories.add("sports");
            if(theme.equals("dark"))
                sports.setTextColor(getColor(R.color.colorPrimary));
            else
                sports.setTextColor(getColor(R.color.colorPrimaryDark));
        }
        else{
            categories.remove("sports");
            sports.setTextColor(getColor(R.color.colorGrey));
        }
        showOrHideFab();
    }

    public void technologySelected(View view) {
        if(!categories.contains("technology")){
            categories.add("technology");
            if(theme.equals("dark"))
                technology.setTextColor(getColor(R.color.colorPrimary));
            else
                technology.setTextColor(getColor(R.color.colorPrimaryDark));
        }
        else{
            categories.remove("technology");
            technology.setTextColor(getColor(R.color.colorGrey));
        }
        showOrHideFab();
    }

    public void go(View view) {
        //Toast.makeText(this, "Go", Toast.LENGTH_SHORT).show();
        if(!categories.isEmpty()){
            try {
                sharedPreferences.edit().putString("categories",ObjectSerializer.serialize(categories)).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            categories.add("business");
            categories.add("entertainment");
            categories.add("health");
           // categories.add("politics");
            categories.add("science");
            categories.add("sports");
            categories.add("technology");
            try {
                sharedPreferences.edit().putString("categories",ObjectSerializer.serialize(categories)).apply();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        sharedPreferences.edit().putString("firstTime","no").apply();
        Intent i=new Intent(this, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(i);
        finish();
    }

    public void showOrHideFab(){
        if(categories.isEmpty()){
            fab.animate().scaleX(0f).scaleY(0f).setDuration(500);
            skip.animate().scaleX(1f).scaleY(1f).setDuration(500);
        }
        else{
            fab.setVisibility(View.VISIBLE);
            fab.animate().scaleX(1f).scaleY(1f).setDuration(500);
            skip.animate().scaleX(0f).scaleY(0f).setDuration(500);
        }
    }


}