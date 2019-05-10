package com.example.yasir.fstival;

import android.content.Context;
import android.preference.TwoStatePreference;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by giri on 20/8/17.
 */

public class FeedbackAdapter extends ArrayAdapter<Feedback> {
    public FeedbackAdapter(Context context, ArrayList<Feedback> feedbacks){
        super(context,0,feedbacks);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listview = convertView;

        if(listview==null){
            listview = LayoutInflater.from(getContext()).inflate(
                    R.layout.activity_listview, parent, false);
        }

        Feedback currentFeedback = getItem(position);

        TextView name = (TextView)listview.findViewById(R.id.name);
        name.setText(currentFeedback.getName());

        TextView timestamp = (TextView)listview.findViewById(R.id.timestamp);
        timestamp.setText(currentFeedback.getTimestamp());

        TextView description = (TextView)listview.findViewById(R.id.description);
        description.setText(currentFeedback.getDescription());

        ImageView image = (ImageView) listview.findViewById(R.id.logo);

        String []Stall = getContext().getResources().getStringArray(R.array.piechart);

        Log.d("Equal",currentFeedback.getStallname()+" b");
        Log.d("Equal",Stall[0]);

        String check = currentFeedback.getStallname();
        Log.d("check",check);

        if(Stall[0].equalsIgnoreCase(check)){
            image.setImageResource(R.drawable.game);
        }else if(Stall[1].equalsIgnoreCase(check)){
            image.setImageResource(R.drawable.androidstudio);
        }else if(Stall[2].equalsIgnoreCase(check)){
            image.setImageResource(R.drawable.raspberry);
        }else if(Stall[3].equalsIgnoreCase(check)){
            image.setImageResource(R.drawable.iotece);
        }else if(Stall[4].equalsIgnoreCase(check)){
            image.setImageResource(R.drawable.ioteee);
        }else if(Stall[5].equalsIgnoreCase(check)){
            image.setImageResource(R.drawable.pyramid);
        }else if(Stall[6].equalsIgnoreCase(check)){
            image.setImageResource(R.drawable.wordpress);
        }else if(Stall[7].equalsIgnoreCase(check)){
            image.setImageResource(R.drawable.mariadb);
        }else{
            image.setImageResource(R.drawable.networksecurity);
        }


        return listview;
    }
}
