package com.nodz.wall.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.bumptech.glide.Glide;
import com.nodz.wall.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class SetWallpaperActivity extends AppCompatActivity {

    ImageView im;
    TextView tvTags;
    Button btnSetWall, btnDownWall;
    String ImgUrl = "", tags_text="";
    Bitmap bitmap;
    String imageFileName = "IMG_" + System.currentTimeMillis() + ".jpg";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_wallpaper);

        ActivityCompat.requestPermissions(SetWallpaperActivity.this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(SetWallpaperActivity.this, new String[] {Manifest.permission.READ_EXTERNAL_STORAGE},1);

        im = findViewById(R.id.imageWall);
        btnSetWall = findViewById(R.id.setwallpaper);
        btnDownWall = findViewById(R.id.getWallpaper);
        //tvTags = findViewById(R.id.tvTags);

        getSupportActionBar().hide();

        Intent i = getIntent();
        ImgUrl = i.getStringExtra("ImgUrl");
        String[] tg_text = i.getStringExtra("TagFirst").split(",");

        if(tg_text.length>1)
            tags_text = tg_text[0]+tg_text[1];
        tags_text = tg_text[0];
        //tvTags.setText(tags_text);

        try {
            Glide.with(this)
                    .load(ImgUrl)
                    .into(im);
        } catch (Exception e) {
            e.printStackTrace();
        }

        btnSetWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GetImageFromUrl getImageFromUrl = new GetImageFromUrl();
                getImageFromUrl.execute(ImgUrl);

            }
        });

        btnDownWall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
            }
        });
    }

    public void saveImage(){

        BitmapDrawable drawable = (BitmapDrawable) im.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        FileOutputStream outputStream = null;
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath()+"/IMAGES");

        if(!dir.exists())
            dir.mkdir();

        File outFile = new File(dir, imageFileName);
        try{
            outputStream = new FileOutputStream(outFile);
            bmp.compress(Bitmap.CompressFormat.PNG,100, outputStream);

        } catch (Exception e) {
            e.printStackTrace();
        }
        try{
            outputStream.flush();

        } catch (Exception e) {
            e.printStackTrace();
        }try{
            outputStream.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
        Toast.makeText(this, "Downloaded  :"+dir.getAbsolutePath(), Toast.LENGTH_LONG).show();
        Log.i("PATH :",dir.getAbsolutePath());
    }

    public class GetImageFromUrl extends AsyncTask<String, Void, Bitmap> {

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
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);
            int height = metrics.heightPixels;
            int width = metrics.widthPixels;

            int imageSize = 0;
            imageSize = (height > width) ? width : height;

            Bitmap bmp2 = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, true);
            WallpaperManager wallpaperManager = WallpaperManager.getInstance(getApplicationContext());
//            wallpaperManager.setWallpaperOffsetSteps(1, 1);
//            wallpaperManager.suggestDesiredDimensions(imageSize,imageSize);
            try {
                wallpaperManager.setBitmap(bmp2);
                Toast.makeText(SetWallpaperActivity.this, "Wallpaper Applied", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}

