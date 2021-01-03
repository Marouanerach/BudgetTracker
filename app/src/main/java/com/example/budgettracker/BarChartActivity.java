package com.example.budgettracker;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Map;

public class BarChartActivity extends AppCompatActivity {
    DBHelper DB;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);
        BarChart barChart = findViewById(R.id.barChart);
        ArrayList<BarEntry> expens  = new ArrayList<>();
        //expens.add(new BarEntry(2014,420));
        //expens.add(new BarEntry(2015, 475));
        DB = new DBHelper(this);
        Map<String,Double> sumCat = DB.expensCat();
        for(Map.Entry m:sumCat.entrySet()){

        }

         Double sum= DB.getExpenseByYear("2018");
        expens.add(new BarEntry(2018, sum.intValue()));
        sum= DB.getExpenseByYear("2019");
        expens.add(new BarEntry(2019, sum.intValue()));
        sum= DB.getExpenseByYear("2020");
        expens.add(new BarEntry(2020, sum.intValue()));
        sum= DB.getExpenseByYear("2021");
        expens.add(new BarEntry(2021, sum.intValue()));



        BarDataSet bardataset = new BarDataSet(expens,"Expenses");
        bardataset.setColors(ColorTemplate.MATERIAL_COLORS);
        bardataset.setValueTextColor(Color.BLACK);
        bardataset.setValueTextSize(16f);

        BarData barData = new BarData(bardataset);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("Bar chart expens");
        barChart.animateY(2000);



    }
}