package com.enigmaticdevs.newsheads.activity;

import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.enigmaticdevs.newsheads.R;
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder;
import com.yarolegovich.slidingrootnav.callback.DragListener;
import com.yarolegovich.slidingrootnav.callback.DragStateListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    List<String> menu;
    List<Integer> icons;
    RecyclerView recyclerView;
    NavItemAdapter itemAdapter;
    TextView instructionText;
    Button button;
    SharedPreferences sharedPreferences;
    SlidingRootNavBuilder slidingRootNavBuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sharedPreferences=this.getSharedPreferences("com.enigmaticdevs.newsheads", MODE_PRIVATE);
        if(sharedPreferences.getString("Theme","dark").equals("dark")) {
            setTheme(R.style.AppTheme);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR);
            getWindow().setStatusBarColor(Color.parseColor("#000000"));
        }else{
            setTheme(R.style.LightTheme);
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.parseColor("#ffffff"));
        }
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);
        createNotificationChannel();
        createAlarmManager();

        ViewPager viewPager = findViewById(R.id.viewPager);
        ViewPagerAdapter adapter= new ViewPagerAdapter(getSupportFragmentManager());

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            public void onPageScrollStateChanged(int state) {    }
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            public void onPageSelected(int position) {
                // Check if this is the page you want.
                sharedPreferences.edit().putString("stopInstruction","yes").apply();
            }
        });

        // Add fragments here
        Toolbar toolbar = findViewById(R.id.toolbar);
       slidingRootNavBuilder= new SlidingRootNavBuilder(this);
                slidingRootNavBuilder.withToolbarMenuToggle(toolbar);
                slidingRootNavBuilder.withMenuLayout(R.layout.menu_left_drawer);
                //.withMenuLayout(R.layout.menu_left_drawer)
                slidingRootNavBuilder.withToolbarMenuToggle(toolbar);
        slidingRootNavBuilder.addDragStateListener(new DragStateListener() {
            @Override
            public void onDragStart() {

            }

            @Override
            public void onDragEnd(boolean isMenuOpened) {
                //sharedPreferences.edit().putString("stopInstruction","yes").apply();
            }
        });
                slidingRootNavBuilder.inject();
                TextView country = findViewById(R.id.countryy);
                country.setText(sharedPreferences.getString("country", "India"));


        //toolbar.setNavigationIcon(R.drawable.menu_icon);
        //setOverflowButtonColor(toolbar, Color.parseColor("#ffffff"));
        initializeRecycler();
        adapter.AddFragment(new FragmentHeading(),"Creator");
        adapter.AddFragment(new FragmentDescription(),"Saved");
        viewPager.setAdapter(adapter);


    }
    private void initializeRecycler() {
        additems();
        recyclerView = findViewById(R.id.recyclerView);
        itemAdapter = new NavItemAdapter(menu,icons,this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(itemAdapter);
    }
    private void additems() {
        menu = new ArrayList<>();
        icons = new ArrayList<>();
        menu.add("Categories");
        menu.add("Country");
        menu.add("Switch Theme");
        menu.add("Rate App");
        menu.add("Share");
        // menu.add("Remove Ads");
      //  menu.add("Logout");
        icons.add(R.drawable.ic_categories_white);
        icons.add(R.drawable.ic_globe);
        icons.add(R.drawable.light_theme);
        icons.add(R.drawable.ic_star_white);
        icons.add(R.drawable.ic_share_white);
        // icons.add(R.drawable.share);
        //   icons.add(R.drawable.remove_ads);
        //icons.add(R.drawable.logout);
    }

    public void createAlarmManager() {
        Intent intent = new Intent(this, NotificationReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 100, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
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
        alarmManager.setRepeating(AlarmManager.RTC, calendar.getTimeInMillis()+90*60*1000, 120*60*1000, pendingIntent);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "News Heads";
            String description = "News Heads";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("NotificationChannelId", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }



}

