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


public final class Utils {


    public static final String LOG_TAG = Utils.class.getSimpleName();


    public static ArrayList<Feedback> fetchFeedbackData(String requestUrl) {
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
        ArrayList<Feedback> feedbacks = extractFeatureFromJson(jsonResponse);

        // Return the {@link Event}
        return feedbacks;
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


    private static ArrayList<Feedback> extractFeatureFromJson(String feedbackJSON) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(feedbackJSON)) {
            return null;
        }
        Log.e(LOG_TAG,"json json json " + feedbackJSON );
        ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();

        try {
            JSONObject baseJsonResponse = new JSONObject(feedbackJSON);
            JSONArray outputArray = baseJsonResponse.getJSONArray("output");

            // If there are results in the features array
            for(int i=0;i<outputArray.length();i++){
                JSONObject currentFeedback =outputArray.getJSONObject(i);
                String  name = currentFeedback.getString("Name");

                String schoolName = (currentFeedback.getString("SchoolName")) ;
                String description= (currentFeedback.getString("StallName"));

                String stallname =description;

                description += ":\n" +"   Depth of Knowledge: "+currentFeedback.getString("Qn1")+"\n   Quality of Content: "+currentFeedback.getString("Qn2")
                                    +"\n   Way Of Approach: "+currentFeedback.getString("Qn3")+"\n   Overall Project: "+currentFeedback.getString("Qn4");

                Feedback feedback = new Feedback(name,schoolName,description,stallname);
                feedbacks.add(feedback);


            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return feedbacks;
    }
}

