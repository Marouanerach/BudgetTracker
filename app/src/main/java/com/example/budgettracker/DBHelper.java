package com.example.budgettracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DBNAME="budgettracker.db";
    private static final String TABLE_EXPENS = "expens";
    private static final String TAG = "DBHelper";
    private static final String TABLE_income = "income";
    public DBHelper(@Nullable Context context) {
        super(context,"budgettracker.db", null, 8);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("create Table users(iduser INTEGER PRIMARY KEY AUTOINCREMENT , username TEXT, password TEXT)");
        MyDB.execSQL("create Table expens(id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,category TEXT,date TEXT,amount DOUBLE,latitude TEXT,longitude TEXT, receiptPath TEXT)");
        MyDB.execSQL("create Table income(idincome INTEGER PRIMARY KEY AUTOINCREMENT, nameincome TEXT,amountincome DOUBLE)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
            MyDB.execSQL("drop Table if exists users");
            MyDB.execSQL("drop Table if exists expens");
            MyDB.execSQL("drop Table if exists income");
    }

    public Boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users",null, contentValues);
        if(result==-1) return false;
        else
            return true;

    }
    public Boolean insertDataincome(String nameincome, String amountincome){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("nameincome", nameincome);
        contentValues.put("amountincome", amountincome);
        long result = MyDB.insert("income",null, contentValues);
        if(result==-1) return false;
        else
            return true;

    }

    public Boolean insertDataExpens(String name, String category, String date,Double amount , String latitude, String longitude){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("name", name);
        contentValues.put("category", category);
        contentValues.put("date", date);
        contentValues.put("amount", amount);
        contentValues.put("latitude", latitude);
        contentValues.put("longitude", longitude);

        long result = MyDB.insert("expens",null, contentValues);
        if(result==-1) return false;
        else
            return true;

    }
    public Boolean checkusername(String username){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ?",new String[] {username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;

    }
    public Boolean checkusernamepassword(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from users where username = ? and password = ?",new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;

    }
    //
    public Double getAllincome() {
        Log.w(TAG, "TEST getAll income");

        String selectQuery = "SELECT SUM(amountincome) FROM " + TABLE_income;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Double l=0.0;

        if (cursor.moveToFirst()) {
            do {

                l = cursor.getDouble(0);


            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // Return the list
        return l;
    }
    public Double getAllexpens() {
        Log.w(TAG, "TEST getAll expens");

        String selectQuery = "SELECT SUM(amount) FROM " + TABLE_EXPENS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        Double l=0.0;

        if (cursor.moveToFirst()) {
            do {

                l = cursor.getDouble(0);


            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        // Return the list
        return l;
    }
    public Map<String,Double> expensCat(){
        Log.w(TAG, "expens by Category ");
        Map<String,Double> map=new HashMap<String,Double>();
        String selectQuery = "SELECT category,SUM(amount) FROM expens GROUP BY category";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Double sum = cursor.getDouble(1);
                String str =cursor.getString(0);
                map.put(str,sum);

            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return map;

    }


    public Double getExpenseByYear(String year) {

        String selectQuery = "SELECT SUM(amount)  FROM expens  WHERE date LIKE  ? " ;
        SQLiteDatabase db = this.getWritableDatabase();
        String tmp = year+"%";
        Cursor cursor = db.rawQuery(selectQuery,new String[] {tmp});
        Double l=0.0;

        if (cursor.moveToFirst()) {
            do {
                Log.w("getExpenseByYear", "testresultgetExpenseByYear");
                l = cursor.getDouble(0);

                Log.w("getExpenseByYear", "testresultgetExpenseByYear " + cursor.getDouble(0));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        Log.w("getExpenseByYear", String.valueOf(l));
        Log.w("getExpenseByYear", String.valueOf(l));
        // Return the list
        return l;
    }

    public List<Expens> getAllRows() {
        List<Expens> l = new ArrayList<>();
        String selectQuery = "SELECT * FROM expens ORDER BY category";

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // Loop through all the rows and addi the to the list
        if (cursor.moveToFirst()) {
            do {
                Expens e = new Expens();
                e.setId(Integer.parseInt(cursor.getString(0)));
                e.setName(cursor.getString(1));
                e.setCategory(cursor.getString(2));
                e.setDate(cursor.getString(3));
                e.setAmount(cursor.getDouble(4));
                e.setLatitude(cursor.getString(5));
                e.setLongitude(cursor.getString(6));
                e.setPathReceipt(cursor.getString(7));


                // Add row to list
                l.add(e);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        // Return the list

         return l;
    }



    public void exDataTest(){
        insertDataExpens("depense1", "Food", "2020-01-31",50.0, "48.77387333333333", "2.036015");
        insertDataExpens("depense2", "Food", "2020-01-31",125.0, "48.77387333333333", "2.036015");
        insertDataExpens("depense3", "Health", "2020-01-31",350.0, "48.77387333333333", "2.036015");
        insertDataExpens("depense4", "Health", "2020-01-31",400.0, "48.77387333333333", "2.036015");
        insertDataExpens("depense5", "Health", "2020-01-31",20.0, "48.77387333333333", "2.036015");
        insertDataExpens("depense6", "Home", "2020-01-31",130.0, "48.77387333333333", "2.036015");
        insertDataExpens("depense7", "Home", "2020-01-31",700.0, "48.77387333333333", "2.036015");
        insertDataExpens("depense8", "Other", "2020-01-31",100.0, "48.77387333333333", "2.036015");
        insertDataExpens("depense9", "Other", "2020-01-31",60.0, "48.77387333333333", "2.036015");
        insertDataExpens("depense10", "Other", "2020-01-31",130.0, "48.77387333333333", "2.036015");
    }


    public Boolean updateExpens(Integer id ,String path)
    {
        Log.d(TAG, "updateInteger: " + id +"/" +path);
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("receiptPath", path);
        // db.update(TABLE_NAME, contentValues, COLUMN_ID + "=?", new String[]{String.valueOf(id)}) == 1;
        long result = MyDB.update("expens",contentValues, "id=?", new String[]{String.valueOf(id)});

        if(result==-1) return false;
        else
            return true;



    }


}
