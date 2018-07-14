package net.tecgurus.app.tecgurusapp.services;

import android.annotation.SuppressLint;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import net.tecgurus.app.tecgurusapp.widget.TecGurusWidget;

import java.util.Date;

public class TimerService extends Service{
    //region Service Methods
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        new Time().execute();
        return super.onStartCommand(intent, flags, startId);
    }
    //endregion

    //region Classes
    @SuppressLint("StaticFieldLeak")
    public class Time extends AsyncTask<String, String, String>{

        @Override
        protected void onProgressUpdate(String... values) {
            super.onProgressUpdate(values);

            int[] ids = AppWidgetManager.getInstance(getApplicationContext())
                    .getAppWidgetIds(new ComponentName(getApplicationContext(), TecGurusWidget.class));
            AppWidgetManager manager = AppWidgetManager.getInstance(getApplicationContext());
            for (int id: ids) {
                TecGurusWidget.updateAppWidget(getApplicationContext(), manager, id);
            }
        }

        @SuppressWarnings("InfiniteLoopStatement")
        @Override
        protected String doInBackground(String... strings) {
            while (true){
                try {
                    Time.this.publishProgress("");
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    //endregion
}
