package com.example.yasir.fstival;

import android.content.AsyncTaskLoader;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by giri on 23/8/17.
 */

public class FeedbackLoader extends AsyncTaskLoader<ArrayList<Feedback>> {

    private String url;
    public FeedbackLoader(Context context,String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public ArrayList<Feedback> loadInBackground() {
        ArrayList<Feedback> feedbacks = new ArrayList<Feedback>();
        feedbacks = Utils.fetchFeedbackData(url);
        return feedbacks;
    }
}
