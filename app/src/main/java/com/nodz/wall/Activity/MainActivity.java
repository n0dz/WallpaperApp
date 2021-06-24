package com.nodz.wall.Activity;

import androidx.appcompat.app.AppCompatActivity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;


import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.nodz.wall.R;

public class MainActivity extends AppCompatActivity {

    TextInputEditText et;
    MaterialButton findbtn;
    //public  static ProgressBar p;

    @Override
    public void onBackPressed() {
        // Do Here what ever you want do on back press;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();


        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

        et = findViewById(R.id.etQuery);
        findbtn = findViewById(R.id.searchBtn);

        findbtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (et.getText().toString().isEmpty()) {
                    return;
                } else {

//                    p = (ProgressBar)findViewById(R.id.progressBar1);
//                    if(p.getVisibility() != View.VISIBLE){ // check if it is visible
//                        p.setVisibility(View.VISIBLE); // if not set it to visible
//                        v.setVisibility(View.VISIBLE); // use 1 or 2 as parameters.. arg0 is the view(your button) from the onclick listener
//                    }

                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

                    Intent intent = new Intent(MainActivity.this, ResultsActivity.class);
                    intent.putExtra("URL", et.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }
}
