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
        //holder.down.setText(model.getDown());
        holder.likes.setText(model.getLike());

        RequestOptions options = new RequestOptions()
                .centerCrop()
                .placeholder(R.drawable.progress_animation)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .dontAnimate()
                .dontTransform();

        Glide.with(context).load(model.getUrl()).apply(options).into(holder.pict);


       /*Glide.with(context)
                .asBitmap()
                .load(model.getUrl())
                .into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap bitmap,
                                                Transition<? super Bitmap> transition) {
                        int w = bitmap.getWidth();
                        int h = bitmap.getHeight();
                        holder.pict.setImageBitmap(bitmap);
                    }
                });*/

        holder.pict.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SetWallpaperActivity.class);
                intent.putExtra("ImgUrl",model.getUrl());
                context.startActivity(intent);

            }
        });
    }
        @Override
        public int getItemCount () {
            //list.size();
            return list.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {
            TextView likes, down;
            ImageView pict;

            public MyViewHolder(@NonNull View itemView) {
                super(itemView);

                likes = itemView.findViewById(R.id.tags);
                //down = itemView.findViewById(R.id.down);
                pict = itemView.findViewById(R.id.imageView);

            }
        }
    }
