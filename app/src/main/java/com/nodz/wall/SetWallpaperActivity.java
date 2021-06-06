package com.nodz.wall;

import androidx.appcompat.app.AppCompatActivity;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SetWallpaperActivity extends AppCompatActivity {

    ImageView im;
    Button bt;
    String ImgUrl = "";
    Bitmap bitmap;
    ImageButton ib;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallpaper);

        im = findViewById(R.id.imageWall);
        bt = findViewById(R.id.setwallpaper);
        ib = findViewById(R.id.imageButton);

        Intent i = getIntent();
        ImgUrl = i.getStringExtra("ImgUrl");

        try {
            Glide.with(this)
                    .load(ImgUrl)
                    .into(im);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ib.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SetWallpaperActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });

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

        /*
            DisplayMetrics displayMetrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
            int height = displayMetrics.heightPixels;
            int width = displayMetrics.widthPixels;
            Bitmap b = Bitmap.createScaledBitmap(bitmap, width, height, true);
        */
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
            try {
                wallpaperManager.setBitmap(bitmap);
                Toast.makeText(SetWallpaperActivity.this, "Wallpaper Applied", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
