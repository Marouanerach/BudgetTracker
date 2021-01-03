package com.example.budgettracker;

import android.Manifest;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class ExpensActivity extends AppCompatActivity {
    ImageView imageView;
    private static final String TAG ="ExpensActivity" ;
    private Expens e;
    Button addReceipt;
    DrawerLayout drawerLayout;
    DBHelper DB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expens);
        drawerLayout= findViewById(R.id.drawer_layout);
        addReceipt = findViewById(R.id.Addreceipt);

        Intent intent = getIntent();
        e = (Expens) intent.getSerializableExtra(ViewExpens.EVENT_EXTRA);

        TextView textView = findViewById(R.id.name);
        textView.setText(e.getName());
        //
        TextView Cat = findViewById(R.id.category);
        Cat.setText(e.getCategory());

        TextView date = findViewById(R.id.date);
        date.setText(e.getDate());

        TextView amount = findViewById(R.id.amount);
        String s=String.valueOf(e.getAmount());
        amount.setText(s);
        //
        textView = findViewById(R.id.Address);

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());

        imageView = findViewById(R.id.imageview);


         String path = e.getPathReceipt();
            Log.d(TAG, "on create " + path );
// we verify first if the path is empty or not before showing the image
       if (path != null){
           File imgFile = new  File(path);
        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
            imageView.setImageBitmap(myBitmap);

        }
       }

        try {
            // here we translate the longitude and the latitude to a real address (Geocoder)

            Double lat =Double.parseDouble(e.getLatitude());
            Double lon= Double.parseDouble(e.getLongitude());

            List<Address> addresses = geocoder.getFromLocation(lat ,lon, 1);
            if (addresses.isEmpty()) {
                textView.setText(e.getLatitude() + ", " + e.getLongitude());
            } else {

                String address = addresses.get(0).getAddressLine(0);
                String city = addresses.get(0).getLocality();
                String country = addresses.get(0).getCountryName();
                String postalCode = addresses.get(0).getPostalCode();

                String text = address + ", ";
                if (postalCode != null && city != null) {
                    text += postalCode + " " + city + ", ";
                }
                text += country;

                textView.setText(text);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //take picture
        if(ContextCompat.checkSelfPermission(ExpensActivity.this, Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(ExpensActivity.this,new String[]{
                    Manifest.permission.CAMERA
            },100 );}
        addReceipt.setOnClickListener( new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,100);


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
        //Open drawer layout
        HomeActivity.redirectActivity(this,Chart.class);
    }
    public void ClickAddExpens(View view){
        HomeActivity.redirectActivity(this, AddExpens.class);
    }
    public void ClickAddincome(View view) {
        HomeActivity.redirectActivity(this, Addincome.class);
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


    @Override
    // Saving the path of the picture taken
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==100){

            Bitmap bitmap = (Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(bitmap);
            String directory=  saveToInternalStorage(bitmap);
            Log.d(TAG," Saved picture " + directory );
            DB = new DBHelper(this);
            DB.updateExpens(e.getId(),directory);

        }
    }


    private String saveToInternalStorage(Bitmap bitmapImage){
        ContextWrapper cw = new ContextWrapper(getApplicationContext());
        // path to /data/data/yourapp/app_data/imageDir
        File directory = cw.getDir("imageDir", Context.MODE_PRIVATE);
        // Create imageDir
        Log.d(TAG,"  saveToInternalStorage"  );
        String name= e.getId()+"_"+"receipt.jpg";
        File mypath=new File(directory,name);

        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(mypath);

            bitmapImage.compress(Bitmap.CompressFormat.PNG, 100, fos);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return directory.getAbsolutePath()  + "/" + name;
    }



}