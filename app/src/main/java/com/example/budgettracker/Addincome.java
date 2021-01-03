package com.example.budgettracker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

public class Addincome extends AppCompatActivity {
    private static final String TAG = "Addincome";
    DrawerLayout drawerLayout;
    EditText nameincome, amountincome;
    Button saveincome;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addincome);

        drawerLayout = findViewById(R.id.drawer_layout);

        //Retrieve Data
        nameincome = (EditText) findViewById(R.id.incomename);
        amountincome = (EditText) findViewById(R.id.incomeamount);
        saveincome = (Button) findViewById(R.id.saveincome);
        DB = new DBHelper(this);

        //
        saveincome.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String name = nameincome.getText().toString();
                String amount = amountincome.getText().toString();

                //we check if all the  field are not empty before inserting them into the database
                if(name.equals("")||amount.equals(""))
                    Toast.makeText(Addincome.this,"please enter all the fields",Toast.LENGTH_SHORT).show();
                else{
                        Boolean insert = DB.insertDataincome(name, amount);
                            if(insert==true){
                                Log.d(TAG, " Add income");
                                Toast.makeText(Addincome.this, "Saved successfully", Toast.LENGTH_SHORT).show();
                                Intent intent  = new Intent(getApplicationContext(),HomeActivity.class);
                                startActivity(intent);
                            }else{
                                Log.d(TAG, " No income was added");
                                Toast.makeText(Addincome.this, "Save Failed", Toast.LENGTH_SHORT).show();
                            }
                }


            }
        });








    }
    //Open drawer layout
    public void ClickMenu(View view){
        HomeActivity.openDrawer(drawerLayout);
    }

    //close drawer layout
    public void ClickLogo(View view){ HomeActivity.closeDrawer(drawerLayout); }
    public void ClickHome(View view){ HomeActivity.redirectActivity(this, HomeActivity.class); }
    public  void ClickStat(View view){ HomeActivity.redirectActivity(this,Chart.class); }
    public void ClickAddExpens(View view){ HomeActivity.redirectActivity(this, AddExpens.class); }
    public void ClickAboutus(View view){ HomeActivity.redirectActivity(this, Aboutus.class); }
    public void ClickAddincome(View view){ recreate(); }
    public void ClickLogout(View view){ HomeActivity.logout(this); }
    public void ClickExpens(View view){ HomeActivity.redirectActivity(this,ViewExpens.class); }
    public void onPause() {

        super.onPause();
        HomeActivity.closeDrawer(drawerLayout);
    }
}