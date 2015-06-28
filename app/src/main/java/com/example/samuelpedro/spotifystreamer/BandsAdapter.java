package com.example.samuelpedro.spotifystreamer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Samuel on 27-06-2015.
 */

public class BandsAdapter<B> extends ArrayAdapter<Band> {

    public BandsAdapter(Context context, List<Band> bands) {
        super(context, 0, bands);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Band band = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist_item, parent, false);
        }
        // Lookup view for data population
        TextView tvBand = (TextView) convertView.findViewById(R.id.artist_item_textView_id);
        ImageView ivBand = (ImageView) convertView.findViewById(R.id.artist_item_imageView_id);

        // Populate the data into the template view using the data object
        tvBand.setText(band.getName());
        Picasso.with(getContext()).load(band.getImage()).into(ivBand);

        // Return the completed view to render on screen
        return convertView;
    }
}
