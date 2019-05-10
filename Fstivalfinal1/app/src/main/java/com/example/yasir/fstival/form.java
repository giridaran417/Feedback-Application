package com.example.yasir.fstival;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class form extends AppCompatActivity {

    private Spinner Best_stall;
    private Spinner Depth,Quality,Overall,approach;
    private int Good,Average,Best;


    private int GAMEDEVELOPMENT = 1;
    private int ANDROIDSTUDIO = 2;
    private int RASPERRYPI_IOT=3;
    private int IOT_ECE = 4;
    private int IOT_EEE=5;
    private int PYRAMID = 6;
    private int DRUAPAL_AND_WORDPRESS = 7;
    private int MARIADB = 8;
    private int NETWORK_AND_SECURITY = 8;
    private int DEFAULT_STATE = 0;
    private String email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        Bundle b = getIntent().getExtras();
        email = b.getString("email");

        Average=Good=Best=0;

        Best_stall = (Spinner)findViewById(R.id.Best_stall);
        Depth = (Spinner)findViewById(R.id.Depth_of_knoweledge1);
        Quality = (Spinner)findViewById(R.id.Quality_of_content2);
        approach = (Spinner)findViewById(R.id.Way_of_approach3);
        Overall = (Spinner)findViewById(R.id.overall_project4);

        ArrayAdapter toAddSpinner = ArrayAdapter.createFromResource(this,R.array.piechart,R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter depth_spinner = ArrayAdapter.createFromResource(this,R.array.Feed_response,R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter quality_spinner = ArrayAdapter.createFromResource(this,R.array.Feed_response,R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter overall_spinner = ArrayAdapter.createFromResource(this,R.array.Feed_response,R.layout.support_simple_spinner_dropdown_item);
        ArrayAdapter approach_spinner = ArrayAdapter.createFromResource(this,R.array.Feed_response,R.layout.support_simple_spinner_dropdown_item);


        toAddSpinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        depth_spinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        quality_spinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        overall_spinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        approach_spinner.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

        Best_stall.setAdapter(toAddSpinner);
        Depth.setAdapter(depth_spinner);
        Quality.setAdapter(quality_spinner);
        approach.setAdapter(approach_spinner);
        Overall.setAdapter(overall_spinner);

        Best_stall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = (String )parent.getItemAtPosition(position);
                DEFAULT_STATE=0;
                if(!TextUtils.isEmpty(string)){
                    if(string.equals("GAME DEVELOPMENT")){
                        DEFAULT_STATE=GAMEDEVELOPMENT;
                    }
                    if(string.equals("ANDROID STUDIO")){
                        DEFAULT_STATE=ANDROIDSTUDIO;
                    }
                    if (string.equals("RASBERRY PI")){
                        DEFAULT_STATE=RASPERRYPI_IOT;
                    }
                    if(string.equals("IOT ECE")){
                        DEFAULT_STATE = IOT_ECE;

                    }
                    if (string.equals("IOT EEE")){
                        DEFAULT_STATE = IOT_EEE;
                    }
                    if(string.equals("PYRAMID")){
                        DEFAULT_STATE=PYRAMID;
                    }
                    if(string.equals("DRUPAL AND WORDPRESS")){
                        DEFAULT_STATE = DRUAPAL_AND_WORDPRESS;
                    }
                    if(string.equals("MARIADB")){
                        DEFAULT_STATE = MARIADB;
                    }
                    if (string.equals("NETWORK SECURITY")){
                        DEFAULT_STATE = NETWORK_AND_SECURITY;
                    }

                }
                else{
                    DEFAULT_STATE = 0;
                }
            }
        });

        Depth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = (String )parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        approach.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = (String )parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Overall.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = (String) parent.getItemAtPosition(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Quality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String string = (String )parent.getItemAtPosition(position);

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void gotoserver(View v){

        final EditText n,sn,stn,f;
        n = (EditText)findViewById(R.id.name);
        sn = (EditText)findViewById(R.id.school_name);

        String name = n.getText().toString();
        String schoolname = sn.getText().toString();

        String beststall = Best_stall.getSelectedItem().toString();

        String[] s = new String[4];

        s[0] = Depth.getSelectedItem().toString();
        s[2] = approach.getSelectedItem().toString();
        s[3] = Overall.getSelectedItem().toString();
        s[1] = Quality.getSelectedItem().toString();


        if((!TextUtils.isEmpty(name)) && (!TextUtils.isEmpty(schoolname)))
        {
            Background b = new Background(this);
            b.execute("givefeedback",email,name,schoolname,beststall,s[0],s[1],s[2],s[3]);
       } else{
            Toast.makeText(this,"please enter all the deteails " + DEFAULT_STATE ,Toast.LENGTH_LONG).show();
       }

       Good = Best = Average = 0;
      /*  Toast.makeText(this,"Good" + Good,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"average" + Average,Toast.LENGTH_SHORT).show();
        Toast.makeText(this,"best"+Best,Toast.LENGTH_SHORT).show();*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        Good=Best=Average=0;
    }
}
