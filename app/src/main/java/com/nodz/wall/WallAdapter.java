package com.nodz.wall;

import android.content.Context;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import com.bumptech.glide.request.RequestOptions;



import java.util.ArrayList;

public class WallAdapter extends RecyclerView.Adapter<WallAdapter.MyViewHolder>{

    Context context;
    ArrayList<WallModel> list;
    static ProgressBar progressBar;

    public WallAdapter(Context context, ArrayList<WallModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.sample_item, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull WallAdapter.MyViewHolder holder, int position) {

        WallModel model = list.get(position);
        holder.tags.setText(model.getTag());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .dontAnimate()
                .dontTransform();

        Glide.with(context).load(model.getUrl()).apply(options).into(holder.pict);

        holder.pict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SetWallpaperActivity.class);
                intent.putExtra("ImgUrl",model.getUrl());
                intent.putExtra("Tags",model.getTag());
                context.startActivity(intent);

            }
        });
    }
        @Override
        public int getItemCount () {
            return list.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tags, down;
            ImageView pict;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                tags = itemView.findViewById(R.id.tags);
                pict = itemView.findViewById(R.id.imageView);

            }
        }
    }
