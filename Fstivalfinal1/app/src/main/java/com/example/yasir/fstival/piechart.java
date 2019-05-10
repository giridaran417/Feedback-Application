package com.example.yasir.fstival;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;

import java.io.Serializable;
import java.util.ArrayList;

import io.fabric.sdk.android.services.concurrency.AsyncTask;

public class piechart extends AppCompatActivity {

    private PieChart pie;
    public ArrayList<chartdetails> charts = new ArrayList<chartdetails>();
    ArrayList<Integer> colors = new ArrayList<>();
    ProgressBar progressBar;
    TextView textView;

    private String []stalls;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pie);
        String url = getResources().getString(R.string.chart_link);
        Log.d("url",url);

        progressBar = (ProgressBar)findViewById(R.id.progress_bar_chart);
        textView = (TextView)findViewById(R.id.textview);



        //////////////////////////////////////////


        ChartAsyncTask chartAsyncTask = new ChartAsyncTask();
        chartAsyncTask.execute(url);

        //////////////////////////////////////////


    }

    private  void code(ArrayList<chartdetails> chartdetails){
        stalls = getResources().getStringArray(R.array.piechart);

        pie = (PieChart)findViewById(R.id.piechart);
        pie.setRotationEnabled(true);
        pie.setHoleRadius(30f);
        pie.setTransparentCircleAlpha(0);
        Description d = new Description();
        d.setText("Fstival17 Reports");
        d.setTextSize(12);
        pie.setDescription(d);
        pie.setCenterText("Fstival17 Stalls");
        pie.setCenterTextSize(16f);
        fun(chartdetails);
    }

    void fun(final ArrayList<chartdetails> charts){
        Log.d("fun",stalls[0]);

        ArrayList<PieEntry> data = new ArrayList<>();


        String temp;
        int likes;
        temp = Integer.toString(charts.size());
        Log.d("size",temp);



        for(int i=0;i<charts.size();i++){
            temp = charts.get(i).getlikes();
            Log.d("value of like",temp);
            likes = Integer.parseInt(temp);
            data.add(new PieEntry(likes, stalls[i]));

        }

        Log.d("fun","plotting completed");



        PieDataSet piedataset = new PieDataSet(data,"");
        piedataset.setSliceSpace(1);
        piedataset.setValueTextSize(14f);
        //Setting colors
        setcolor(colors);
        piedataset.setColors(colors);

        Legend l = pie.getLegend();
        l.setForm(Legend.LegendForm.CIRCLE);
        l.setFormSize(15f);
        l.setTextColor(Color.parseColor("#330000"));
        l.setTextSize(16f);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
        l.setWordWrapEnabled(true);


        PieData piedata = new PieData(piedataset);
        piedata.setValueTextSize(13f);

        pie.setDrawEntryLabels(false);    //Hiding Legend inside piechart
        pie.setData(piedata);
        pie.invalidate();

        pie.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, Highlight h) {
                Log.d("Touched"," "+e.toString());
                Log.d("Touched"," "+h.toString());

                int pos = (int)h.getX();
                String s = stalls[pos] + "\n" + h.getY();
                //Toast.makeText(piechart.this,s, Toast.LENGTH_SHORT).show();

                Intent i = new Intent(getApplicationContext(),stall.class);
                i.putExtra("id",pos+1);
                i.putExtra("colors",colors);
                i.putExtra("arraylist",charts);
                startActivity(i);

            }

            @Override
            public void onNothingSelected() {

            }
        });

    }


    void setcolor(ArrayList<Integer> colors){
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.parseColor("#D2B48C"));
        colors.add(Color.CYAN);
        colors.add(Color.parseColor("#FFCC66"));
        colors.add(Color.LTGRAY);
        colors.add(Color.parseColor("#800080"));
        colors.add(Color.parseColor("#32CD32"));
        colors.add(Color.parseColor("#FF6666"));

    }



    private class ChartAsyncTask extends AsyncTask<String,Void,ArrayList<chartdetails>>{

        ArrayList<chartdetails> chart = new ArrayList<chartdetails>();
        @Override
        protected ArrayList<chartdetails> doInBackground(String... strings) {
            progressBar.setVisibility(View.VISIBLE);
            chart = Utilschart.fetchFeedbackDatachart(strings[0]);
            return chart;
        }

        @Override
        protected void onPostExecute(ArrayList<chartdetails> chart) {
            textView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
            code(chart);
        }
    }

}


