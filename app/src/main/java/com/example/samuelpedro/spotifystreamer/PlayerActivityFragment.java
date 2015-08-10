package com.example.samuelpedro.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerActivityFragment extends Fragment {

    private static final String TOP_10_LIST_SONGS = "TOP_10_LIST_SONGS";
    private static final String POSITION = "position";

    TextView textView;
    Intent intent;
    private ArrayList<Music> listMusic;
    private int position;

    public PlayerActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getActivity().getIntent().getExtras();
        listMusic = bundle.getParcelableArrayList(TOP_10_LIST_SONGS);
        position = bundle.getInt(POSITION);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_player, container, false);
        textView = (TextView) rootView.findViewById(R.id.artist_item_textView_id);

        textView.setText(listMusic);
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String idBand = intent.getStringExtra(Intent.EXTRA_TEXT);
            //FetchSongsTask fetchSongsTask = new FetchSongsTask();
            //fetchSongsTask.execute(idBand);

            listMusic = new ArrayList<Music>();
            musicAdapter = new MusicAdapter(getActivity(), listMusic);
            ListView listView = (ListView) rootView.findViewById(R.id.list_view_musics);
            listView.setAdapter(musicAdapter);
        }


        return rootView;
    }
}
