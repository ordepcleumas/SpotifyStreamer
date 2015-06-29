package com.example.samuelpedro.spotifystreamer;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Samuel on 27-06-2015.
 */

public class BandsAdapter extends ArrayAdapter<Band> {

    public BandsAdapter(Activity context, List<Band> bandList) {

        super(context, 0, bandList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Band band = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist_item, parent, false);
        }

        TextView textViewBand = (TextView) convertView.findViewById(R.id.artist_item_textView_id);
        textViewBand.setText(band.getName());

        //ImageView ivBand = (ImageView) convertView.findViewById(R.id.artist_item_imageView_id);
        //Picasso.with(getContext()).load(band.getImage()).into(ivBand);

        // Return the completed view to render on screen
        return convertView;
    }
    //http://www.survivingwithandroid.com/2012/10/android-listviewarrayadapter-and.html
    //http://www.survivingwithandroid.com/2012/10/android-listview-custom-adapter-and.html
    //http://www.javacodegeeks.com/2013/06/android-listview-custom-adapter-with-imageview.html?utm_content=buffer44ad0&utm_medium=social&utm_source=plus.google.com&utm_campaign=buffer
    //http://www.javacodegeeks.com/2013/09/android-listview-with-adapter-example.html
}
