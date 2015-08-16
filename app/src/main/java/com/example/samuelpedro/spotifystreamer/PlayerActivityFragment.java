package com.example.samuelpedro.spotifystreamer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerActivityFragment extends Fragment {

    private static final String POSITION = "position";
    private static final String BAND_NAME = "band_name";
    private static final String TOP_10_LIST_SONGS = "TOP_10_LIST_SONGS";

    TextView tvArtist;
    TextView tvAlbum;
    ImageView ivCover;
    TextView tvMusic;

    Intent intent;
    String img;
    Button play;
    Button previous;
    Button next;
    private ArrayList<Music> listMusic;
    private int position;
    private String mBandName;
    private MediaPlayer mediaPlayer;

    public PlayerActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = getActivity().getIntent().getExtras();

        position = bundle.getInt(POSITION);
        mBandName = bundle.getString(BAND_NAME);
        listMusic = bundle.getParcelableArrayList(TOP_10_LIST_SONGS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //Fill view with fragment
        View rootView = inflater.inflate(R.layout.fragment_player, container, false);
        //findElements
        tvArtist = (TextView) rootView.findViewById(R.id.tvArtist);
        tvAlbum = (TextView) rootView.findViewById(R.id.tvAlbum);
        ivCover = (ImageView) rootView.findViewById(R.id.ivCover);
        tvMusic = (TextView) rootView.findViewById(R.id.tvMusic);

        img = (listMusic.get(position).getPhotoLarge().isEmpty()
                ? listMusic.get(position).getPhotoSmall()
                : listMusic.get(position).getPhotoLarge());


        tvArtist.setText(mBandName);
        tvAlbum.setText(listMusic.get(position).getAlbumName());
        Picasso.with(getActivity()).load(img).into(ivCover);
        tvMusic.setText(listMusic.get(position).getTrackName());

        mediaPlayer = new MediaPlayer();
        mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(listMusic.get(position).getPreview_url()));

        mediaPlayer.start();

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });

        play = (Button) rootView.findViewById(R.id.btPlay);
        previous = (Button) rootView.findViewById(R.id.btPrevious);
        next = (Button) rootView.findViewById(R.id.btNext);

        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mediaPlayer.isPlaying()) {
                    pauseMusic();
                } else {
                    startMusic();
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (position > 0) {
                    position = position - 1;
                } else {
                    position = listMusic.size() - 1;
                }
                getMusic();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mediaPlayer.isPlaying()) mediaPlayer.stop();

                if (position < (listMusic.size() - 1)) {
                    position = position + 1;
                } else {
                    position = 0;
                }
                getMusic();
            }
        });

        return rootView;
    }

    public void pauseMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.pause();
            play.setBackgroundResource(android.R.drawable.ic_media_play);
        }
    }

    public void startMusic() {
        if (mediaPlayer != null) {
            mediaPlayer.start();
            play.setBackgroundResource(android.R.drawable.ic_media_pause);
        }
    }

    public void getMusic() {
        mediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(listMusic.get(position).getPreview_url()));

        tvArtist.setText(mBandName);
        tvAlbum.setText(listMusic.get(position).getAlbumName());
        Picasso.with(getActivity()).load(listMusic.get(position).getPhotoLarge()).into(ivCover);
        tvMusic.setText(listMusic.get(position).getTrackName());
        startMusic();

    }

}
