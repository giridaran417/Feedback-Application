package com.example.yasir.fstival;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Created by yasir on 8/9/17.
 */

public class Utilschart {
    public static final String LOG_TAG = Utils.class.getSimpleName();


    public static ArrayList<chartdetails> fetchFeedbackDatachart(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = "";
        try {
            jsonResponse = makeHttpRequest(url);

            Log.e(LOG_TAG,"json response" + jsonResponse);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        ArrayList<chartdetails> chart1 = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return chart1;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }


    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    private static ArrayList<chartdetails> extractFeatureFromJson(String feedbackJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(feedbackJSON)) {
            return null;
        }
        Log.e(LOG_TAG,"json json json " + feedbackJSON );
        ArrayList<chartdetails> chart = new ArrayList<chartdetails>();

        try {
            JSONObject baseJsonResponse = new JSONObject(feedbackJSON);
            JSONArray outputArray = baseJsonResponse.getJSONArray("output");

            // If there are results in the features array
            for(int i=0;i<outputArray.length();i++){
                JSONObject currentFeedback =outputArray.getJSONObject(i);
                String  likes = currentFeedback.getString("Likes");
                Log.d("utilschart likes",likes);

                String best1 = (currentFeedback.getString("Qn1best")) ;
                String good1 = (currentFeedback.getString("Qn1good"));
                String average1 = (currentFeedback.getString("Qn1average"));

                String best2 = (currentFeedback.getString("Qn2best")) ;
                String good2 = (currentFeedback.getString("Qn2good"));
                String average2 = (currentFeedback.getString("Qn2average"));

                String best3 = (currentFeedback.getString("Qn3best")) ;
                String good3 = (currentFeedback.getString("Qn3good"));
                String average3 = (currentFeedback.getString("Qn3average"));

                String best4 = (currentFeedback.getString("Qn4best")) ;
                String good4 = (currentFeedback.getString("Qn4good"));
                String average4 = (currentFeedback.getString("Qn4average"));

                chartdetails c = new chartdetails(likes,best1,good1,average1,best2,good2,average2
                        ,best3,good3,average3,best4,good4,average4);
                chart.add(c);


            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return chart;
    }

}
