package com.nodz.wall.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.cooltechworks.views.shimmer.ShimmerRecyclerView;
import com.nodz.wall.Apadter.WallAdapter;
import com.nodz.wall.Model.WallModel;
import com.nodz.wall.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class ResultsActivity extends AppCompatActivity {

    ShimmerRecyclerView recyclerView;
    ArrayList<WallModel> list;
    String base = "https://pixabay.com/api/?key=21942328-3b403fd14df4f4bef82d7991b&q=";
    String json_url = "";
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        recyclerView = findViewById(R.id.recyclerView);
        //recyclerView.showShimmerAdapter();
        list = new ArrayList<>();

        json_url = base + getIntent().getStringExtra("URL");
        GetData getData = new GetData();
        getData.execute();

    }
    public class GetData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String current = "";
            try {
                URL url;
                HttpURLConnection httpURLConnection = null;
                try {
                    url = new URL(json_url);
                    httpURLConnection = (HttpURLConnection) url.openConnection();
                    InputStream is = httpURLConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    int data = isr.read();
                    while (data != -1) {
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (httpURLConnection != null) {
                        httpURLConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("hits");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    WallModel model = new WallModel(getApplicationContext(), list);
                    //model.setDown(jsonObject1.getString("downloads"));
                    model.setUrl(jsonObject1.getString("largeImageURL"));
                    list.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loadDataIntoRecyclerView(list);
        }

        private void loadDataIntoRecyclerView(ArrayList<WallModel> list) {
            WallAdapter adapter = new WallAdapter(ResultsActivity.this, list);
            recyclerView.setAdapter(adapter);
            recyclerView.setLayoutManager(new GridLayoutManager(ResultsActivity.this, 2));
        }

    }

//    public void onStart() {
//        super.onStart();
//        MainActivity.p.setVisibility(View.INVISIBLE);
//    }

}