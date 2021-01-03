package com.example.budgettracker;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = "HomeActivity";

    DrawerLayout drawerLayout;
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // test if we get all the data fromm the database
        Log.d(TAG, "calcul the amount all expenses, incoms and the diffrence betwenen them " );

        //Assign variable
        drawerLayout=findViewById(R.id.drawer_layout);
        DB = new DBHelper(this);

        Double sumIn = DB.getAllincome();
        //Test if we get all the sum of incoms
        Log.d(TAG, "Sum of income" + sumIn);
        TextView textView = findViewById(R.id.Textincome);
        textView.setText(String.valueOf(sumIn));


        Double sumExpens= DB.getAllexpens();
        //Test if we get all the sum of expens from the database
        Log.d(TAG, "Sum of Expens" + sumExpens);
        TextView sumE = findViewById(R.id.Textexpens);
        sumE.setText(String.valueOf(sumExpens));

        //Sold
        TextView total = findViewById(R.id.TextTotal);
        Double d=sumIn - sumExpens;
        if(d<0){  String a =String.valueOf(sumIn - sumExpens); // SOLD
            total.setText(a);
        total.setTextColor(Color.RED);}
        if(d>0){  String a =String.valueOf(sumIn - sumExpens);
            total.setText(a);
            total.setTextColor(Color.GREEN);}



    }

    public void ClickMenu(View view){
        //open drawer
        openDrawer(drawerLayout);
    }
    public static void openDrawer(DrawerLayout drawerLayout){
        //Open drawer layout
        drawerLayout.openDrawer(GravityCompat.START);
    }
    public  void ClickStat(View view){
        //Open drawer layout
        redirectActivity(this,Chart.class);
    }

    public void ClickLogo(View view){
        //Close drawer
        closeDrawer(drawerLayout);
    }
    public static void closeDrawer(DrawerLayout drawerLayout){
        //close drawer layout
        //check condition
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            //when drawer is open close the drawer
            drawerLayout.closeDrawer(GravityCompat.START);
        }
    }
    public void ClickHome(View view){
           //recreate activity
        recreate();
    }
    public void ClickAddincome(View view) {
        redirectActivity(this, Addincome.class);
    }

        public void ClickAddExpens(View view){
        //redirect activity to add expense
        redirectActivity(this,AddExpens.class);
    }
    public void ClickAboutus(View view){
        //redirect activity to about us
        redirectActivity(this,Aboutus.class);
    }
    public void ClickExpens(View view){
        //redirect activity to about us
        redirectActivity(this,ViewExpens.class);
    }
    public void ClickLogout(View view){
        //redirect activity to about us
       logout(this);
    }

    public static void logout(Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("Logout");
        builder.setMessage("Are you sure you want to logout ?");
        //positive answer
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish activity
                activity.finishAffinity();
                System.exit(0);
            }
        });
        //negative answer
        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        //show dialog
        builder.show();
    }

    public static void redirectActivity(Activity activity, Class aClass){
        //initialize intent
        Intent intent = new Intent(activity, aClass);
        //set flag
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //start activity
        activity.startActivity(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        closeDrawer(drawerLayout);
    }
}