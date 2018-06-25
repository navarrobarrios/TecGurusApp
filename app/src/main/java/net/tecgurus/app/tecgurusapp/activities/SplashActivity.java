package net.tecgurus.app.tecgurusapp.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ProgressBar;

import net.tecgurus.app.tecgurusapp.R;

public class SplashActivity extends AppCompatActivity {
    private ProgressBar mProgress;
    //endregion

    //region Activity Methods
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mProgress = findViewById(R.id.splash_activity_progress);
        mProgress.getProgressDrawable().setColorFilter(
                Color.BLUE, android.graphics.PorterDuff.Mode.SRC_IN);
        new Contador().execute();
    }
    //endregion

    //region Classes
    @SuppressLint("StaticFieldLeak")
    public class Contador extends AsyncTask<Integer, Integer, Integer>{

        //region Variable
        private Integer counter;
        //endregion

        @Override
        protected void onPreExecute() {
            Log.e(SplashActivity.class.getSimpleName(), "onPreExecute");
            counter = 0;
            super.onPreExecute();
        }

        @Override
        protected Integer doInBackground(Integer... integers) {
            Log.e(SplashActivity.class.getSimpleName(), "doInBackground");
            do {
                counter++;
                try {
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                publishProgress(counter);
            }while (counter < 100);
            return null;
        }

        @SuppressLint("SetTextI18n")
        @Override
        protected void onProgressUpdate(Integer... values) {
            Log.e(SplashActivity.class.getSimpleName(), "onProgressUpdate");
            super.onProgressUpdate(values);
            mProgress.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            Log.e(SplashActivity.class.getSimpleName(), "onPostExecute");
            super.onPostExecute(integer);
            Intent callMainActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(callMainActivity);
            SplashActivity.this.finish();
        }
    }

    //endregion
}
