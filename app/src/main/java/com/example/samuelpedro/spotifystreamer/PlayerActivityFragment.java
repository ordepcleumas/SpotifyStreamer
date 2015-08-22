package com.example.samuelpedro.spotifystreamer;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

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
    SeekBar mSeekBar;
    TextView tvTimeLeft;
    TextView tvTimePassed;

    Intent intent;
    String img;
    Button play;
    Button previous;
    Button next;
    double timeElapsed = 0, finalTime = 0;
    private ArrayList<Music> listMusic;
    private int position;
    private String mBandName;
    private MediaPlayer mMediaPlayer;
    private Handler durationHandler = new Handler();
    private Runnable updateSeekBarTime = new Runnable() {
        @Override
        public void run() {

            //get current position
            timeElapsed = mMediaPlayer.getCurrentPosition();
            //set seekbar progress
            mSeekBar.setProgress((int) timeElapsed);
            //set time remaing
            double timeRemaining = finalTime - timeElapsed;
            tvTimeLeft.setText(String.format("%d:%d", TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining), TimeUnit.MILLISECONDS.toSeconds((long) timeRemaining) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
            //tvTimePassed.setText(String.format("%d:%d", TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes((long) timeRemaining))));
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

        img = listMusic.get(position).getPhotoLarge();


        tvArtist.setText(mBandName);
        tvAlbum.setText(listMusic.get(position).getAlbumName());
        Picasso.with(getActivity()).load(img).into(ivCover);
        tvMusic.setText(listMusic.get(position).getTrackName());

        mMediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(listMusic.get(position).getPreview_url()));
        //mMediaPlayer.start();
        startMusic();


        finalTime = mMediaPlayer.getDuration();
        mSeekBar.setMax((int) finalTime);
        mSeekBar.setClickable(false);


        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            int musicProgress = 0;

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                Log.d("AA", "AAAAAAAAAAAAAAAAAA");
                if (mMediaPlayer != null && fromUser) {
                    Log.d("AA", "BBBBBBBBBBBBB");
                    //mMediaPlayer.seekTo(progress * 1000);
                    musicProgress = progress;
                    getTimeText(musicProgress);
                }
                Log.d("AA", "CCCCCCCCCCCCC");
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

                Log.d("AA", "EEEEEEEEEEEEEEE");
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Log.d("AA", "FFFFFFFFFFFF");

            }
        });

        mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

            }
        });

        play.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) {
                    pauseMusic();
                } else {
                    startMusic();
                    //timeElapsed = media
                }
            }
        });

        previous.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();

                position = (position > 0) ? position - 1 : listMusic.size() - 1;
                //if (position > 0) {
                //    position = position - 1;
                //} else {
                //    position = listMusic.size() - 1;
                //}
                getMusic();
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mMediaPlayer.isPlaying()) mMediaPlayer.stop();

                position = (position < (listMusic.size() - 1) ? position + 1 : 0);
                //if (position < (listMusic.size() - 1)) {
                //    position = position + 1;
                //} else {
                //    position = 0;
                //}
                getMusic();
            }
        });

        return rootView;
    }

    public String getTimeText(int progress) {
        DateFormat format = new SimpleDateFormat("mm:ss");

        try {
            Time time = new Time(format.parse(String.valueOf(progress)).getTime());
            return time.toString();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
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

    public void getMusic() {
        mMediaPlayer = MediaPlayer.create(getActivity(), Uri.parse(listMusic.get(position).getPreview_url()));

        tvArtist.setText(mBandName);
        tvAlbum.setText(listMusic.get(position).getAlbumName());
        Picasso.with(getActivity()).load(listMusic.get(position).getPhotoLarge()).into(ivCover);
        tvMusic.setText(listMusic.get(position).getTrackName());
        startMusic();

    }

}
