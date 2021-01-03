package com.example.budgettracker;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    EditText username, password;
    Button btnlogin;
    DBHelper DB;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        createNotificationChannel(); // Create our notification

        username = (EditText) findViewById(R.id.username1);
        password = (EditText) findViewById(R.id.password1);
        btnlogin = (Button) findViewById(R.id.btnsignin1);
        DB = new DBHelper(this);

        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Start Authentication");
                String user = username.getText().toString();
                String pass = password.getText().toString();

                if(user.equals("")||pass.equals(""))

                    Toast.makeText(LoginActivity.this, "PLease enter all the fields", Toast.LENGTH_SHORT).show();
                else{
                    // here we check the username and password if they are valid so we can give to the user access to his account

                    Boolean checkuserpass = DB.checkusernamepassword(user, pass);
                    if(checkuserpass==true){
                        scheduleJob( v);
                        //if yes
                        Log.d(TAG, " Authentication Success ");
                        Toast.makeText(LoginActivity.this, "Sign in successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);

                    }else{
                        // if not
                        Log.d(TAG, " Authentication Failed ");
                        Toast.makeText(LoginActivity.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                }

                //create scheduled notification that inform the user about his account statement (repeated every week 7days )
                Log.d(TAG, "create scheduled notification");
                Toast.makeText(LoginActivity.this, "Registered reminder successfully", Toast.LENGTH_SHORT).show(); //to make sure that the notification works fine

                Intent intent = new Intent(LoginActivity.this,ReminderExpens.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(LoginActivity.this,0,intent,0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                long tenSec = 1000*10;
                // WEEKLY reminder of user expens
                //alarmManager.setRepeating(alarmManager.RTC_WAKEUP, System.currentTimeMillis(), alarmManager.INTERVAL_DAY*7, pendingIntent);
                //for test we reduce the schedules to 10 Seconds
                alarmManager.setRepeating(alarmManager.RTC_WAKEUP, System.currentTimeMillis(),tenSec, pendingIntent);

            }

        });
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    // Some parameters related to the notification
    private void createNotificationChannel(){
        // Create a Channel for notification
        int importance = NotificationManager.IMPORTANCE_DEFAULT;
        NotificationChannel mChannel = new NotificationChannel("notifyMeLater", "Article channel ",importance);
        mChannel.setDescription(" Description channel");
        mChannel.enableLights(true);
        mChannel.setLightColor(Color.RED);
        mChannel.enableVibration(true);
        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

        NotificationManager mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.createNotificationChannel(mChannel);
    }
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void scheduleJob(View v){
        ComponentName componentName = new ComponentName(this, JobCheckService.class);
        JobInfo info = new JobInfo.Builder(123,componentName)
                .setPersisted(true)
                .setPeriodic(15*60*1000)  //check state balance every 15 min
                .build();
        JobScheduler scheduler = (JobScheduler) getSystemService(Context.JOB_SCHEDULER_SERVICE);
        int resultCode = scheduler.schedule(info);
        if (resultCode  == JobScheduler.RESULT_SUCCESS){
            Log.d(TAG,"Job scheduled");
        }else {
            Log.d(TAG,"Job scheduling Failed");
        }
    }

}