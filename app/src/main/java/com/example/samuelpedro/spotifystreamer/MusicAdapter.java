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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Samuel on 30-06-2015.
 */
public class MusicAdapter extends ArrayAdapter<Music> {

    private final String LOG_TAG = MusicAdapter.class.getSimpleName();
    public ArrayList<Music> listMusic = new ArrayList<>();

    public MusicAdapter(Context context, List<Music> musicList) {
        super(context, 0, musicList);
    }

    public void setMusic(ArrayList<Music> listMusic) {
        this.listMusic = listMusic;

        Log.d(MusicAdapter.class.getName(), " 13 ");
        notifyDataSetChanged();

        Log.d(MusicAdapter.class.getName(), " 14 ");
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        try {
            Music m = getItem(position);

            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.music_item, parent, false);
            }

            ImageView imgViewTrack = (ImageView) convertView.findViewById(R.id.track_item_imageView_id);
            Picasso.with(getContext()).load(m.getPhotoSmall()).into(imgViewTrack);

            TextView txtView1 = (TextView) convertView.findViewById(R.id.music_item_tvMusicTitle_id);
            txtView1.setText(m.getTrackName());

            TextView txtView2 = (TextView) convertView.findViewById(R.id.music_item_tvAlbumTitle_id);
            txtView2.setText(m.getAlbumName());

            return convertView;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }
    }
}
