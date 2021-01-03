package com.example.budgettracker;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobCheckService extends JobService {

    private static final String TAG = "JobCheckService";
    private boolean jobCancelled = false;
    DBHelper DB;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "Job Starts");
        doBackgroundwork(params);

        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void doBackgroundwork(JobParameters params) {
        Log.d(TAG, "doBackgroundwork : " );


        DB = new DBHelper(this);
        Double sumIn = DB.getAllincome();
        Double sumExpens= DB.getAllexpens();




        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);;
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel channel = new NotificationChannel("channel1", "Article channel ",importance);
        nm.createNotificationChannel(channel);


        new Thread(new Runnable() {
            @Override
            public void run() {

                if(sumExpens>sumIn) {


                    Log.d(TAG, "algo : ");


                    NotificationCompat.Builder nb = new NotificationCompat.Builder(JobCheckService.this, "channel1")
                            .setSmallIcon(R.drawable.ic_warn)
                            .setContentText("Your expenses are greater than your income")
                            .setContentTitle("Be careful, you are spending too much!")
                            .setPriority(NotificationCompat.PRIORITY_MAX);
                    nm.notify(555, nb.build());
                }


                Log.d(TAG, "Job finished");
                jobFinished(params,false);
            }

        }).start();
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }




}