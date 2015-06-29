package com.example.samuelpedro.spotifystreamer;

import android.content.Context;
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

    private List<Band> bandList;
    private Context context;

    public BandsAdapter(Context context, List<Band> bandList) {

        super(context, 0, bandList);
        this.bandList = bandList;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        //Band a = new Band(1,"asd");
        //Band band = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            // This a new view we inflate the new layout
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.artist_item, parent, false);
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.artist_item, parent, false);
        }
        // Lookup view for data population
        TextView tvBand = (TextView) convertView.findViewById(R.id.artist_item_textView_id);
        //ImageView ivBand = (ImageView) convertView.findViewById(R.id.artist_item_imageView_id);

        Band band = bandList.get(position);

        // Populate the data into the template view using the data object
        tvBand.setText(band.getName());
        //Picasso.with(getContext()).load(band.getImage()).into(ivBand);

        // Return the completed view to render on screen
        return convertView;
    }
    //http://www.survivingwithandroid.com/2012/10/android-listviewarrayadapter-and.html
    //http://www.survivingwithandroid.com/2012/10/android-listview-custom-adapter-and.html
    //http://www.javacodegeeks.com/2013/06/android-listview-custom-adapter-with-imageview.html?utm_content=buffer44ad0&utm_medium=social&utm_source=plus.google.com&utm_campaign=buffer
    //http://www.javacodegeeks.com/2013/09/android-listview-with-adapter-example.html
}
