package com.example.yasir.fstival;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;

public class stall extends AppCompatActivity {

    String stalls[];
    int pos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stall);
        Log.d("pichart", "bundle creation");

        Bundle b = getIntent().getExtras();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        ArrayList<chartdetails> charts = new ArrayList<chartdetails>();
        if (b != null) {
            Log.d("stall.java", "null");
            pos = b.getInt("id");
            colors = b.getIntegerArrayList("colors");
            charts = (ArrayList<chartdetails>) getIntent().getSerializableExtra("arraylist");
        }

        stalls = getResources().getStringArray(R.array.piechart);
        ScrollView s = (ScrollView) findViewById(R.id.scrollview);
        s.setBackgroundColor(colors.get(pos - 1));

        TextView t0 = (TextView) findViewById(R.id.snamevalue);
        String str = stalls[pos - 1];
        t0.setText(str);

        /*Log.d("stall.java",charts.get(pos-1).getgood());
        //////////////////////////////////////////
        TextView t1 = (TextView)findViewById(R.id.feedbackvalue);
        str = charts.get(pos-1).getlikes();
        t1.setText(str);*/

        TextView t1 = (TextView) findViewById(R.id.bestvalue1);
        TextView t2 = (TextView) findViewById(R.id.goodvalue1);
        TextView t3 = (TextView) findViewById(R.id.averagevalue1);

        TextView t4 = (TextView) findViewById(R.id.bestvalue2);
        TextView t5 = (TextView) findViewById(R.id.goodvalue2);
        TextView t6 = (TextView) findViewById(R.id.averagevalue2);

        TextView t7 = (TextView) findViewById(R.id.bestvalue3);
        TextView t8 = (TextView) findViewById(R.id.goodvalue3);
        TextView t9 = (TextView) findViewById(R.id.averagevalue3);

        TextView t11 = (TextView) findViewById(R.id.bestvalue4);
        TextView t12 = (TextView) findViewById(R.id.goodvalue4);
        TextView t13 = (TextView) findViewById(R.id.averagevalue4);

        str = charts.get(pos - 1).get1best();
        t1.setText(str);

        str = charts.get(pos - 1).get1good();
        t2.setText(str);

        str = charts.get(pos - 1).get1average();
        t3.setText(str);

        str = charts.get(pos - 1).get2best();
        t4.setText(str);

        str = charts.get(pos - 1).get2good();
        t5.setText(str);

        str = charts.get(pos - 1).get2average();
        t6.setText(str);

        str = charts.get(pos - 1).get3best();
        t7.setText(str);

        str = charts.get(pos - 1).get3good();
        t8.setText(str);

        str = charts.get(pos - 1).get3average();
        t9.setText(str);

        str = charts.get(pos - 1).get4best();
        t11.setText(str);

        str = charts.get(pos - 1).get4good();
        t12.setText(str);

        str = charts.get(pos - 1).get4average();
        t13.setText(str);

    }

}
