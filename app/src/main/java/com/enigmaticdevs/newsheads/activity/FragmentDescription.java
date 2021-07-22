package com.enigmaticdevs.newsheads.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.enigmaticdevs.newsheads.R;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.WanderingCubes;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class FragmentDescription extends Fragment {
    TextView description, content, readMore;
    ImageView imageView;
    ProgressBar progressBar;
    WanderingCubes wanderingCubes;
    SharedPreferences sharedPreferences;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.description_fragment,container,false);
        sharedPreferences=getContext().getSharedPreferences("com.enigmaticdevs.newsheads", Context.MODE_PRIVATE);
        progressBar = (ProgressBar)v.findViewById(R.id.spin_kit);
        wanderingCubes = new WanderingCubes();
        description=v.findViewById(R.id.description);
        imageView=v.findViewById(R.id.imageView);
        content=v.findViewById(R.id.content);
        readMore=v.findViewById(R.id.readMore);
        sharedPreferences.registerOnSharedPreferenceChangeListener(listener);
        readMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri webpage = Uri.parse(sharedPreferences.getString("url", ""));
                Intent i = new Intent(Intent.ACTION_VIEW, webpage);
                    startActivity(i);
            }
        });
        return v;
    }

    SharedPreferences.OnSharedPreferenceChangeListener listener=(sharedPreferences1, key) -> {

        wanderingCubes.start();
        progressBar.setVisibility(View.VISIBLE);
        progressBar.setIndeterminateDrawable(wanderingCubes);
        description.setText(sharedPreferences1.getString("description", ""));
        content.setText(sharedPreferences1.getString("content",""));
        if(sharedPreferences1.getString("url","").isEmpty())
            readMore.setVisibility(View.GONE);
        else
            readMore.setVisibility(View.VISIBLE);

        if(sharedPreferences1.getString("urlToImage","").equals("noImage")){
                imageView.setVisibility(View.INVISIBLE);
        }
        else{
            if(getActivity()!=null) {
                Glide.with(this)
                        .load(sharedPreferences1.getString("urlToImage", ""))
                        .listener(new RequestListener<Drawable>() {
                            @Override
                            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                wanderingCubes.stop();
                                return false;
                            }

                            @Override
                            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                                progressBar.setVisibility(View.GONE);
                                wanderingCubes.stop();
                                return false;
                            }
                        })
                        .into(imageView);
            }
        }
    };


}
