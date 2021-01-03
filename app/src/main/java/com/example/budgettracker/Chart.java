package com.example.budgettracker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Chart extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chart);
        drawerLayout= findViewById(R.id.drawer_layout);

        findViewById(R.id.ReportBarChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity( new Intent(getApplicationContext(), BarChartActivity.class));
            }
        });

        findViewById(R.id.ReportPieChart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              startActivity( new Intent(getApplicationContext(), PieChartActivity.class));
            }
        });



    }
    public void ClickMenu(View view){
        HomeActivity.openDrawer(drawerLayout);
    }


    public void ClickLogo(View view){
        //close drawer
        HomeActivity.closeDrawer(drawerLayout);
    }
    public void ClickHome(View view){
        HomeActivity.redirectActivity(this, HomeActivity.class);
    }
    public  void ClickStat(View view){

        recreate();
    }
    public void ClickAddExpens(View view){
        HomeActivity.redirectActivity(this, AddExpens.class);
    }
    public void ClickAboutus(View view){
        HomeActivity.redirectActivity(this, Aboutus.class);
    }
    public void ClickAddincome(View view){
        HomeActivity.redirectActivity(this,Addincome.class);
    }
    public void ClickLogout(View view){
        HomeActivity.logout(this);
    }
    public void ClickExpens(View view){
        HomeActivity.redirectActivity(this,ViewExpens.class);
    }
    public void onPause() {

        super.onPause();
        HomeActivity.closeDrawer(drawerLayout);
    }
}