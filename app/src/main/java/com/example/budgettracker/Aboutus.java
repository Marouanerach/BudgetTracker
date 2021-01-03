package com.example.budgettracker;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Aboutus extends AppCompatActivity {
    DrawerLayout drawerLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        drawerLayout = findViewById(R.id.drawer_layout);
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
        //Open drawer layout
        HomeActivity.redirectActivity(this,Chart.class);
    }
    public void ClickAddExpens(View view){
        HomeActivity.redirectActivity(this, AddExpens.class);
    }
    public void ClickAboutus(View view){
        recreate();
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