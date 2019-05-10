package com.example.yasir.fstival;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import static android.content.DialogInterface.BUTTON_POSITIVE;


public class Background extends AsyncTask<String,Void,String> {
    Context c;
    AlertDialog a;
    Background(Context ctx){
        c = ctx;
    }

    @Override
    protected String doInBackground(String... params) {
        String check = params[0];
        String email,name,school_name,stall_name,feed_back;
        String qn1,qn2,qn3,qn4;
        String feedbackurl = c.getResources().getString(R.string.feedback_link);
        if(check == "givefeedback"){
            email = params[1];
            name = params[2];
            school_name = params[3];
            stall_name = params[4];
            qn1 = params[5];
            qn2 = params[6];
            qn3 = params[7];
            qn4 = params[8];



            try {
                URL url =createUrl(feedbackurl);
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("POST");
                connection.setDoOutput(true);
                connection.setDoInput(true);


                OutputStream out;

                BufferedWriter bw;

                out = connection.getOutputStream();

                bw = new BufferedWriter(new OutputStreamWriter(out,"UTF-8"));


                String input = URLEncoder.encode("email","UTF-8")+"="+URLEncoder.encode(email,"UTF-8")+"&"
                        +URLEncoder.encode("name","UTF-8")+"="+URLEncoder.encode(name,"UTF-8")+"&"
                        +URLEncoder.encode("school_name","UTF-8")+"="+URLEncoder.encode(school_name,"UTF-8")+"&"
                        +URLEncoder.encode("stall_name","UTF-8")+"="+URLEncoder.encode(stall_name,"UTF-8")+"&"
                        +URLEncoder.encode("qn1","UTF-8")+"="+URLEncoder.encode(qn1,"UTF-8")+"&"
                        +URLEncoder.encode("qn2","UTF-8")+"="+URLEncoder.encode(qn2,"UTF-8")+"&"
                        +URLEncoder.encode("qn3","UTF-8")+"="+URLEncoder.encode(qn3,"UTF-8")+"&"
                        +URLEncoder.encode("qn4","UTF-8")+"="+URLEncoder.encode(qn4,"UTF-8");


                Log.d("Encode",input);
                bw.write(input);
                bw.flush();
                bw.close();
                out.close();


                BufferedReader br;
                InputStream in;
                in = connection.getInputStream();
                br = new BufferedReader(new InputStreamReader(in,"iso-8859-1"));
                String status="",line;

                while((line = br.readLine()) != null){
                    status = status + line;
                }

                br.close();
                in.close();

                return status;


            } catch (MalformedURLException e) {
                Log.d("url","URL PROBLEM");
                e.printStackTrace();
            } catch (IOException e) {
                Log.d("IO","IO PROBLEM");
                e.printStackTrace();
            }
        }
        return null;
    }

    private  URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            //Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    @Override
    protected void onPostExecute(String s) {
        if(!((Activity) c).isFinishing())
        {
            a.setMessage(s);
            a.setButton(BUTTON_POSITIVE,"OK",new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog,
                                            int which) {
                            dialog.dismiss();
                            ((Activity) c).finish();
                        }
            });
            a.show();
        }

       // context.startActivity(new Intent(context, ResultsQueryActivity.class));
        //c.startActivity(new Intent(c,MainActivity.class));

    }

    @Override
    protected void onPreExecute() {
        a = new AlertDialog.Builder(c).create();
        a.setTitle("Feedback Status");
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }
}
