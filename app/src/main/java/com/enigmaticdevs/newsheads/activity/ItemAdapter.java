package com.enigmaticdevs.newsheads.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.enigmaticdevs.newsheads.R;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ViewHolder> {

    private List<String> titles;
    private List<String> countryShort;
    ViewHolder myHolder;
    SharedPreferences sharedPreferences;
    String theme="dark";
    int row_index=-1;

    public ItemAdapter(List<String> data, Context context){
         sharedPreferences=context.getSharedPreferences("com.enigmaticdevs.newsheads", Context.MODE_PRIVATE);
        if(sharedPreferences.getString("Theme","dark").equals("dark")) {
            theme="dark";
        }else{
            theme="light";
        }
         this.titles=data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater=LayoutInflater.from(parent.getContext());
        View v=inflater.inflate(R.layout.list_layout, parent,false);
        countryShort=new ArrayList<>();
        countryShort.add("ar");
        countryShort.add("au");
        countryShort.add("at");
        countryShort.add("be");
        countryShort.add("br");
        countryShort.add("bg");
        countryShort.add("ca");
        countryShort.add("cn");
        countryShort.add("co");
        countryShort.add("cu");
        countryShort.add("cz");
        countryShort.add("eg");
        countryShort.add("fr");
        countryShort.add("de");
        countryShort.add("gr");
        countryShort.add("hk");
        countryShort.add("hu");
        countryShort.add("in");
        countryShort.add("id");
        countryShort.add("ie");
        countryShort.add("il");
        countryShort.add("it");
        countryShort.add("jp");
        countryShort.add("lv");
        countryShort.add("lt");
        countryShort.add("my");
        countryShort.add("mx");
        countryShort.add("ma");
        countryShort.add("nl");
        countryShort.add("nz");
        countryShort.add("ng");
        countryShort.add("no");
        countryShort.add("ph");
        countryShort.add("pl");
        countryShort.add("pt");
        countryShort.add("ro");
        countryShort.add("ru");
        countryShort.add("sa");
        countryShort.add("rs");
        countryShort.add("sg");
        countryShort.add("sk");
        countryShort.add("si");
        countryShort.add("za");
        countryShort.add("kr");
        countryShort.add("ch");
        countryShort.add("se");
        countryShort.add("tw");
        countryShort.add("th");
        countryShort.add("tr");
        countryShort.add("ae");
        countryShort.add("ua");
        countryShort.add("gb");
        countryShort.add("us");
        countryShort.add("ve");
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        myHolder=holder;
        String title=titles.get(position);
        holder.title.setText(title);
        //int countryPosition=sharedPreferences.getInt("countryPosition",1);
        //String countrySelected=titles.get(countryPosition);

       /* if(countryPosition==position){
            if(theme.equals("dark"))
                holder.title.setTextColor(Color.parseColor("#ffffff"));
            else
                holder.title.setTextColor(Color.parseColor("#000000"));
        }*/


        holder.title.setOnClickListener(view -> {
            holder.title.setTextColor(Color.parseColor("#ffffff"));
            Log.i("Country",titles.get(position));
            Log.i("Country",countryShort.get(position));
            sharedPreferences.edit().putString("country",titles.get(position)).apply();
            sharedPreferences.edit().putString("countryShort",countryShort.get(position)).apply();
            //sharedPreferences.edit().putInt("countryPosition",position).apply();
            row_index=position;
            notifyDataSetChanged();
        });
        if(row_index==position){
           // holder.row_linearlayout.setBackgroundColor(Color.parseColor("#567845"));
            if(theme.equals("dark"))
              holder.title.setTextColor(Color.parseColor("#ffffff"));
            else
                holder.title.setTextColor(Color.parseColor("#000000"));

        }
        else
        {
            //holder.row_linearlayout.setBackgroundColor(Color.parseColor("#ffffff"));
            holder.title.setTextColor(Color.parseColor("#838383"));
        }
    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textView);
        }
    }
}
