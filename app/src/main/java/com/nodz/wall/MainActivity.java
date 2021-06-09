package com.nodz.wall;

import androidx.appcompat.app.AppCompatActivity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
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

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ArrayList<WallModel> list;
    Context context;

    String base = "https://pixabay.com/api/?key=21942328-3b403fd14df4f4bef82d7991b&q=";
    String end ="&image_type=photo";
    String json_url="";
    EditText et;
    Button findbtn;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().hide();

        recyclerView = findViewById(R.id.recyclerView);
        list = new ArrayList<>();

        et = findViewById(R.id.query);
        findbtn = findViewById(R.id.search);

        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading Images ");
        dialog.setTitle("Loading");

        findbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                et.setVisibility(View.INVISIBLE);
                findbtn.setVisibility(View.INVISIBLE);

                //Hide the keyboard after search
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                json_url = base + et.getText();
                GetData getdata = new GetData();
                getdata.execute();

            }
        });
    }
    public class GetData extends AsyncTask<String, String, String> {
        @Override
        protected String doInBackground(String... strings) {
            String current="";
            try{
                URL url;
                HttpURLConnection httpURLConnection = null;
                try{
                    url = new URL(json_url);
                    httpURLConnection = (HttpURLConnection) url.openConnection();

                    InputStream is = httpURLConnection.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);

                    int data = isr.read();

                    while(data != -1){
                        current += (char) data;
                        data = isr.read();
                    }
                    return current;

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    if(httpURLConnection != null){
                        httpURLConnection.disconnect();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return current;
        }

        @SuppressLint("CheckResult")
        @Override
        protected void onPostExecute(String s) {

            try{
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("hits");

                for(int i = 0; i <jsonArray.length(); i++){

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    WallModel model = new WallModel(context, list);
                    //model.setDown(jsonObject1.getString("downloads"));
                    model.setTag(jsonObject1.getString("tags"));
                    model.setUrl(jsonObject1.getString("largeImageURL"));

                    list.add(model);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            loadDataIntoRecyclerView(list);
        }
    }
    private void loadDataIntoRecyclerView(ArrayList<WallModel> list){

        WallAdapter adapter = new WallAdapter(this,list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));

    }
}