package com.example.samuelpedro.spotifystreamer;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Samuel on 30-06-2015.
 */
public class TrackAdapter extends ArrayAdapter<Music> {

    private final String LOG_TAG = TrackAdapter.class.getSimpleName();

    /**
     * Constructor
     *
     * @param context  The current context.
     * @param resource The resource ID for a layout file containing a TextView to use when
     *                 instantiating views.
     * @param objects  The objects to represent in the ListView.
     */
    public TrackAdapter(Context context, List musicList) {
        super(context, 0, musicList);
    }

    /**
     * {@inheritDoc}
     *
     * @param position
     * @param convertView
     * @param parent
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        super.getView(position, convertView, parent);
        try {
            Music m = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.track_item, parent, false);
            }

            ImageView imgViewTrack = (ImageView) convertView.findViewById(R.id.track_item_imageView_id);
            Picasso.with(getContext()).load(m.getPhotoSmall()).into(imgViewTrack);

            TextView txtView1 = (TextView) convertView.findViewById(R.id.track_item_textView1_id);
            txtView1.setText(m.getAlbumName());

            TextView txtView2 = (TextView) convertView.findViewById(R.id.track_item_textView2_id);
            txtView1.setText(m.getTrackName());

            return convertView;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }
    }
}
