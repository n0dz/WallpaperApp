package com.nodz.wall;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.RandomAccessFile;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;


public class SetWallpaperActivity extends AppCompatActivity {

    ImageView im;
    Button bt;
    String ImgUrl = "";
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallpaper);

        im = findViewById(R.id.imageWall);
        bt = findViewById(R.id.setwallpaper);

        Intent i = getIntent();
        ImgUrl = i.getStringExtra("ImgUrl");

        try {
            Glide.with(this)
                    .load(ImgUrl)
                    .into(im);
        } catch (Exception e) {
            e.printStackTrace();
        }

        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetImageFromUrl getImageFromUrl = new GetImageFromUrl();
                getImageFromUrl.execute(ImgUrl);

            }
        });
    }
    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected Bitmap doInBackground(String... url) {
            String stringUrl = url[0];
            bitmap = null;
            InputStream inputStream;
            try {
                inputStream = new URL(stringUrl).openStream();
                bitmap = BitmapFactory.decodeStream(inputStream);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap){
            super.onPostExecute(bitmap);

            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            Bitmap b = Bitmap.createScaledBitmap(bitmap, width, height, true);

            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
//            wallpaperManager.setWallpaperOffsetSteps(1, 1);
//            wallpaperManager.suggestDesiredDimensions(width, height);
//
            try {

                wallpaperManager.setBitmap(b);
                Toast.makeText(SetWallpaperActivity.this, "Wallpaper Applied", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
