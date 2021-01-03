package com.example.budgettracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.util.ArrayList;
import java.util.List;

public class ViewExpens extends AppCompatActivity {
    DrawerLayout drawerLayout;
    private static final String TAG = "ViewExpens";

    private DBHelper db;
    public static String EVENT_EXTRA = "expens";
    //
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_expens);
        drawerLayout = findViewById(R.id.drawer_layout);
        //
        db = new DBHelper(this);
        //
        listEvents();
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
        HomeActivity.redirectActivity(this, Aboutus.class);
    }
    public void ClickExpens(View view){
        recreate();
    }
    public void ClickAddincome(View view){
        HomeActivity.redirectActivity(this,Addincome.class);
    }
    public void ClickLogout(View view){
        HomeActivity.logout(this);
    }
    public void onPause() {

        super.onPause();
        HomeActivity.closeDrawer(drawerLayout);
    }

    private void listEvents() {
        Log.d(TAG, "Listing the expens..");

        // Get list from database
        final List<Expens> expens = db.getAllRows();

        // Copy to new list
        List<String> list = new ArrayList<>();
        for (Expens e : expens) {

            list.add(e.getName()+" \n " +"  " + e.getAmount() + "  Euros");
        }

        // Create ListView
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(new ArrayAdapter<>(ViewExpens.this, android.R.layout.simple_list_item_1, list));






        // Handle onClick event
       listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                Intent intent = new Intent(ViewExpens.this, ExpensActivity.class);
                intent.putExtra(EVENT_EXTRA, expens.get(arg2));
                startActivity(intent);
            }
        });

    }
}