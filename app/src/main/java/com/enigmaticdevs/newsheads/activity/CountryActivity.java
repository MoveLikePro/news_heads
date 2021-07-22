package com.enigmaticdevs.newsheads.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.enigmaticdevs.newsheads.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class CountryActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    List<String> titles;
    SharedPreferences sharedPreferences;
    String theme="dark";
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sharedPreferences=this.getSharedPreferences("com.enigmaticdevs.newsheads",MODE_PRIVATE);
        Intent i=new Intent(this, MainActivity.class);
        if(sharedPreferences.getString("Theme","dark").equals("dark")) {
            setTheme(R.style.AppTheme);
            theme="dark";
        }else{
            setTheme(R.style.LightTheme);
            theme="light";
        }
        setContentView(R.layout.activity_country);
        fab=findViewById(R.id.fabCountry);
        if(!theme.equals("dark"))
            fab.setImageResource(R.drawable.arrow_white);
        recyclerView=findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        titles=new ArrayList<>();
        titles.add("Argentina");
        titles.add("Australia");
        titles.add("Austria");
        titles.add("Belgium");
        titles.add("Brazil");
        titles.add("Bulgaria");
        titles.add("Canada");
        titles.add("China");
        titles.add("Colombia");
        titles.add("Cuba");
        titles.add("Czech Republic");
        titles.add("Egypt");
        titles.add("France");
        titles.add("Germany");
        titles.add("Greece");
        titles.add("Hong Kong");
        titles.add("Hungary");
        titles.add("India");
        titles.add("Indonesia");
        titles.add("Ireland");
        titles.add("Israel");
        titles.add("Italy");
        titles.add("Japan");
        titles.add("Latvia");
        titles.add("Lithuania");
        titles.add("Malaysia");
        titles.add("Mexico");
        titles.add("Morocco");
        titles.add("Netherlands");
        titles.add("New Zealand");
        titles.add("Nigeria");
        titles.add("Norway");
        titles.add("Philippines");
        titles.add("Poland");
        titles.add("Portugal");
        titles.add("Romania");
        titles.add("Russia");
        titles.add("Saudi Arabia");
        titles.add("Serbia");
        titles.add("Singapore");
        titles.add("Slovakia");
        titles.add("Slovenia");
        titles.add("South Africa");
        titles.add("South Korea");
        titles.add("Sweden");
        titles.add("Switzerland");
        titles.add("Taiwan");
        titles.add("Thailand");
        titles.add("Turkey");
        titles.add("UAE");
        titles.add("Ukraine");
        titles.add("United Kingdom");
        titles.add("United States");
        titles.add("Venuzuela");
        ItemAdapter itemAdapter=new ItemAdapter(titles,this);
        recyclerView.setAdapter(itemAdapter);

        fab.setOnClickListener(v -> {
            if(sharedPreferences.getString("firstTime","yes").equals("yes")){
                Intent in=new Intent(this,CategoryActivity.class);
                startActivity(in);
            }
            else {
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
                finish();
            }

        });
    }


}