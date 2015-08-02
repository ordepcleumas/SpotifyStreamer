package com.example.samuelpedro.spotifystreamer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerActivityFragment extends Fragment {

    public PlayerActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //return inflater.inflate(R.layout.fragment_player, container, false);

        View rootView = inflater.inflate(R.layout.fragment_player, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.artist_item_textView_id);
        //textView.setText(listMusic);
        //Intent intent = getActivity().getIntent();
        //if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
        //    String idBand = intent.getStringExtra(Intent.EXTRA_TEXT);
//
        //    //FetchSongsTask fetchSongsTask = new FetchSongsTask();
        //    //fetchSongsTask.execute(idBand);
//
        //
//
        //    listMusic = new ArrayList<Music>();
        //    musicAdapter = new MusicAdapter(getActivity(), listMusic);
//
        //    ListView listView = (ListView) rootView.findViewById(R.id.list_view_musics);
        //    listView.setAdapter(musicAdapter);
        //}


        return rootView;
    }
}
