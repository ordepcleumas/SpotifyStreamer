package com.example.samuelpedro.spotifystreamer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerActivityFragment extends Fragment {

    private static final String POSITION = "position";
    private static final String BAND_NAME = "band_name";
    private static final String TOP_10_LIST_SONGS = "TOP_10_LIST_SONGS";

    Intent intent;
    MediaPlayer mMediaPlayer;
    private TextView tvArtist;
    private TextView tvAlbum;
    private ImageView ivCover;
    private TextView tvMusic;
    private SeekBar mSeekBar;
    private TextView tvTimeLeft;
    private TextView tvTimePassed;
    private Button play;
    private Button previous;
    private Button next;
    //Seekbar
    private double timeElapsed = 0, finalTime = 0;
    private Handler durationHandler = new Handler();

    private ArrayList<Music> listMusic;
    private int position;
    private String mBandName;

    private Runnable updateSeekBarTime = new Runnable() {
        @Override
        public void run() {

            //get current position
            timeElapsed = mMediaPlayer.getCurrentPosition();
            //set seekbar progress
            mSeekBar.setProgress((int) timeElapsed);
            //set time remaing
            double timeRemaining = finalTime - timeElapsed;
            tvTimePassed.setText(String.format("%02d:%02d", TimeUnit.MILLISECONDS.toMinutes((long) timeElapsed), TimeUnit.MILLISECONDS.toSeconds((long) timeElapsed)));
            tvTimeLeft.setText(String.format("%02d:%02d",
                    TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining)
                            - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
            //repeat yourself that again in 100 miliseconds
            durationHandler.postDelayed(this, 100);

        }
    };


    public PlayerActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mMediaPlayer = new MediaPlayer();
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
        mSeekBar = (SeekBar) rootView.findViewById(R.id.sbMusic);
        tvTimeLeft = (TextView) rootView.findViewById(R.id.tvTimeLeft);
        tvTimePassed = (TextView) rootView.findViewById(R.id.tvTimePassed);
        play = (Button) rootView.findViewById(R.id.btPlay);
        previous = (Button) rootView.findViewById(R.id.btPrevious);
        next = (Button) rootView.findViewById(R.id.btNext);

        //fill elements with values
        tvArtist.setText(mBandName);
        tvAlbum.setText(listMusic.get(position).getAlbumName());
        Picasso.with(getActivity()).load(listMusic.get(position).getPhotoLarge()).into(ivCover);
        tvMusic.setText(listMusic.get(position).getTrackName());

        //Create MediaPlayer
        mMediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(listMusic.get(position).getPreview_url()));
        //set duration of Music
        finalTime = mMediaPlayer.getDuration();
        mSeekBar.setMax((int) finalTime);
        //Start Music
        startMusic();

        //Config SeekBarChange
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mMediaPlayer != null && fromUser) {
                    mMediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                //play.setBackgroundResource(android.R.drawable.ic_media_play);
                //or

            }
        });

        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    pauseMusic();
                } else {
                    startMusic();
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();

                position = (position > 0) ? position - 1 : listMusic.size() - 1;
                getMusic();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();
                position = (position < (listMusic.size() - 1) ? position + 1 : 0);
                getMusic();
            }
        });

        return rootView;
    }


    public void pauseMusic() {
        if (mMediaPlayer != null) {
            mMediaPlayer.pause();
            play.setBackgroundResource(android.R.drawable.ic_media_play);
        }
    }

    public void startMusic() {
        if (mMediaPlayer != null) {
            mMediaPlayer.start();
            play.setBackgroundResource(android.R.drawable.ic_media_pause);

            //http://examples.javacodegeeks.com/android/android-mediaplayer-example/
            //SeekBar example
            timeElapsed = mMediaPlayer.getCurrentPosition();
            mSeekBar.setProgress((int) timeElapsed);

            durationHandler.postDelayed(updateSeekBarTime, 100);

        }
    }

    @Override
    public void onDestroy() {
        if (mMediaPlayer != null && mMediaPlayer.isPlaying()) {
            //Stop Runnable
            durationHandler.removeCallbacks(updateSeekBarTime);
            //Stop Media Player
            mMediaPlayer.stop();
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
        super.onDestroy();
    }

    public void getMusic() {
        //Create MediaPlayer with music
        mMediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(listMusic.get(position).getPreview_url()));
        //set Elements
        tvArtist.setText(mBandName);
        tvAlbum.setText(listMusic.get(position).getAlbumName());
        Picasso.with(getActivity()).load(listMusic.get(position).getPhotoLarge()).into(ivCover);
        tvMusic.setText(listMusic.get(position).getTrackName());
        //start Music
        startMusic();
    }


}
