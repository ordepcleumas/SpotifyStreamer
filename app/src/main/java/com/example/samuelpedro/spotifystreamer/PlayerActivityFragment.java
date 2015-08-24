package com.example.samuelpedro.spotifystreamer;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

/**
 * A placeholder fragment containing a simple view.
 */
public class PlayerActivityFragment extends DialogFragment {

    private static final String POSITION = "position";
    private static final String BAND_NAME = "band_name";
    private static final String TOP_10_LIST_SONGS = "TOP_10_LIST_SONGS";
    private static final String TIMEELAPSED = "timeElapsed";

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

    static PlayerActivityFragment newInstance() {
        return new PlayerActivityFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            listMusic = savedInstanceState.getParcelableArrayList(TOP_10_LIST_SONGS);
            position = savedInstanceState.getInt(POSITION);
            mBandName = savedInstanceState.getString(BAND_NAME);
            timeElapsed = savedInstanceState.getDouble(TIMEELAPSED);
        } else {
            Bundle bundle = getArguments();

            position = bundle.getInt(POSITION);
            mBandName = bundle.getString(BAND_NAME);
            listMusic = bundle.getParcelableArrayList(TOP_10_LIST_SONGS);
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // save data source
        if (listMusic != null) {
            outState.putParcelableArrayList(TOP_10_LIST_SONGS, listMusic);
            outState.putString(BAND_NAME, mBandName);
            outState.putInt(POSITION, position);
        }
        if (mMediaPlayer != null) {
            outState.putDouble(TIMEELAPSED, timeElapsed);
        }
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

        mMediaPlayer = new MediaPlayer();
        getMusic();

        //fill elements with values
        //tvArtist.setText(mBandName);
        //tvAlbum.setText(listMusic.get(position).getAlbumName());
        //Picasso.with(getActivity()).load(listMusic.get(position).getPhotoLarge()).into(ivCover);
        //tvMusic.setText(listMusic.get(position).getTrackName());

        //Create MediaPlayer
        // mMediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(listMusic.get(position).getPreview_url()));
        //set duration of Music
        finalTime = mMediaPlayer.getDuration();
        mSeekBar.setMax((int) finalTime);
        //Start Music
        //startMusic();

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
                play.setBackgroundResource(android.R.drawable.ic_media_play);
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
                timeElapsed = 0;
                getMusic();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();
                position = (position < (listMusic.size() - 1) ? position + 1 : 0);
                timeElapsed = 0;
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
        if (isNetworkAvailable()) {
            //Create MediaPlayer with music
            mMediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(listMusic.get(position).getPreview_url()));
            mMediaPlayer.seekTo((int) timeElapsed);
            //set Elements
            tvArtist.setText(mBandName);
            tvAlbum.setText(listMusic.get(position).getAlbumName());
            Picasso.with(getActivity()).load(listMusic.get(position).getPhotoLarge()).into(ivCover);
            tvMusic.setText(listMusic.get(position).getTrackName());
            //start Music
            startMusic();
        } else {
            Context context = getActivity();
            CharSequence text = "Sorry couldn't access the internet, please check your connection!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
    }

    //Check if there is Internet Connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
