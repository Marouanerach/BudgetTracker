package com.example.budgettracker;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class ReminderExpens  extends BroadcastReceiver {
    DBHelper DB;
    @Override
    public void onReceive(Context context, Intent intent) {
        DB = new DBHelper(context);
        Double sumIn = DB.getAllincome();
        Double sumExpens= DB.getAllexpens();
        String a =String.valueOf(sumIn - sumExpens);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "notifyMeLater")
                .setSmallIcon(R.drawable.ic_money)
                .setContentTitle("Account statement")
                .setContentText("Balance : "+a+" Euros" )
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        notificationManager.notify(300,builder.build());


    }
}
