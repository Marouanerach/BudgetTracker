package com.example.budgettracker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AddExpens extends AppCompatActivity {
    private static final String TAG = "AddExpens";
    DrawerLayout drawerLayout;
    EditText expensename,expensAmount;
    TextView expensdate;
    Spinner category;

    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_expens);

        mDisplayDate = (TextView) findViewById(R.id.expensdate);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(
                        AddExpens.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        mDateSetListener,
                        year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);

                String date = day  + "/" +  month + "/" + year;
                mDisplayDate.setText(date);
            }
        };
        //

        drawerLayout= findViewById(R.id.drawer_layout);
        //Spinner
        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.Category, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        // retrieve data
        expensename  = (EditText) findViewById(R.id.expensename);
        expensAmount  = (EditText) findViewById(R.id.expensAmount);
       expensdate  = (TextView) findViewById(R.id.expensdate);
        category = (Spinner) findViewById(R.id.spinner);


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
    public void ClickAddincome(View view) {
        HomeActivity.redirectActivity(this, Addincome.class);
    }
    public void ClickAddExpens(View view){
        recreate();
    }
    public void ClickExpens(View view){
        HomeActivity.redirectActivity(this,ViewExpens.class);
    }
    public void ClickAboutus(View view){
        HomeActivity.redirectActivity(this,Aboutus.class);
    }
    public void ClickLogout(View view){
        HomeActivity.logout(this);
    }
    public void onPause() {

        super.onPause();
        HomeActivity.closeDrawer(drawerLayout);
    }
    public void addLocation(View view) {

        // Create an intent for the activity
        // Retrieve data from the different fields completed
        Intent i = new Intent(this, MapsActivity.class);

        String name = expensename.getText().toString();
        String amount=  expensAmount.getText().toString();
        String date = expensdate.getText().toString();
        String cat = category.getSelectedItem().toString();



        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);



        String newDateString;
        SimpleDateFormat sdf = new SimpleDateFormat("dd/mm/yyyy");
        Date d = null;
        try {
            d = sdf.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        sdf.applyPattern("YYYY-MM-DD");
        newDateString = sdf.format(d);

        //test log
        Log.w(TAG, "values " +name +" " +amount+" "+newDateString+"  " +cat );

        i.putExtra("name",name);
        i.putExtra("amount",amount);
        i.putExtra("date",newDateString);
        i.putExtra("cat",cat);


        // Start the activity
        startActivity(i);

    }
}