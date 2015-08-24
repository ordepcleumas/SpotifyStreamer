package com.example.samuelpedro.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Image;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class MusicActivityFragment extends Fragment {

    //For send receive bundle data
    public static final String BAND_ID = "band_id";
    public static final String BAND_NAME = "band_name";
    public static final String POSITION = "position";
    public static final String TOP_10_LIST_SONGS = "TOP_10_LIST_SONGS";
    private static final String STATE_MUSIC = "music";
    //For Log
    //private final String LOG_TAG = MusicActivityFragment.class.getSimpleName();
    private MusicAdapter musicAdapter;
    private ArrayList<Music> listMusic;
    private String mIdBand;
    private String mNameBand;
    private Bundle bundleR; //Receive
    private Bundle bundleS; //Send
    private int twoPane;

    public MusicActivityFragment() {
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        listMusic = new ArrayList<>();
        musicAdapter = new MusicAdapter(getActivity(), listMusic);
        View rootView = inflater.inflate(R.layout.fragment_music, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.list_view_musics);

        bundleR = getArguments();

        if (bundleR != null) {
            //get values
            mIdBand = bundleR.getString(BAND_ID);
            mNameBand = bundleR.getString(BAND_NAME);
            twoPane = bundleR.getInt("TwoPane");

            musicAdapter = new MusicAdapter(getActivity(), listMusic);
            listView.setAdapter(musicAdapter);

            if (savedInstanceState != null) {
                listMusic = savedInstanceState.getParcelableArrayList(STATE_MUSIC);
                musicAdapter.setMusic(listMusic);
            } else {
                FetchSongsTask fetchSongsTask = new FetchSongsTask();
                fetchSongsTask.execute(mIdBand);
            }
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //Band band = bandAdapter.getItem(position);

                //Intent intent = new Intent(getActivity(), PlayerActivity.class);

                bundleS = new Bundle();

                bundleS.putInt(POSITION, position);
                bundleS.putString(BAND_NAME, mNameBand);
                bundleS.putParcelableArrayList(TOP_10_LIST_SONGS, listMusic);

                if (twoPane == 1) {
                    // Create the fragment and show it as a dialog.
                    FragmentManager fm = getFragmentManager();
                    PlayerActivityFragment dialogFragment = new PlayerActivityFragment();
                    dialogFragment.setArguments(bundleS);
                    dialogFragment.show(fm, "Sample Fragment");
                } else {
                    //Create an intent to open the PlayerActivity, passing the data with the Bundle
                    Intent intent = new Intent(getActivity(), PlayerActivity.class);
                    intent.putExtras(bundleS);

                    startActivity(intent);
                }

                //intent.putExtras(bundleS);
                //startActivity(intent);
            }
        });

        musicAdapter.setMusic(listMusic);
        listView.setAdapter(musicAdapter);
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        if (listMusic != null) {
            outState.putParcelableArrayList(STATE_MUSIC, listMusic);
        }
    }

    public class FetchSongsTask extends AsyncTask<String, Void, List> {

        private final String LOG_TAG = FetchSongsTask.class.getSimpleName();

        @Override
        protected List doInBackground(String... params) {

            if (params.length == 0) return null;

            try {

                Map<String, Object> options = new Hashtable<String, Object>();
                options.put("country", "PT"); //Replace here

                SpotifyApi api = new SpotifyApi();
                //api.setAccessToken("myAccessToken");
                SpotifyService spotify = api.getService();
                Tracks results = spotify.getArtistTopTrack(params[0], options);

                return results.tracks;

            } catch (Exception e) {

                Log.e(LOG_TAG, "Error ", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List trackstList) {
            super.onPostExecute(trackstList);

            if (!trackstList.isEmpty()) {
                musicAdapter.clear();
                for (int i = 0; i < trackstList.size(); i++) {
                    Track track = (Track) trackstList.get(i);//new Artist();
                    //a = (Artist) artistList.get(i);
                    if (track.album.images.isEmpty()) {
                        musicAdapter.add(new Music(track.id, track.album.name, track.name, "http://png-4.findicons.com/files/icons/1676/primo/128/music.png", "http://png-4.findicons.com/files/icons/1676/primo/128/music.png"));
                    } else {


                        String imgLarge = "http://png-4.findicons.com/files/icons/1676/primo/128/music.png";
                        String imgSmall = "http://png-4.findicons.com/files/icons/1676/primo/128/music.png";
                        Iterator<Image> iterator = track.album.images.iterator();
                        while (iterator.hasNext()) {
                            Image img = iterator.next();

                            if (img.width >= 640) {
                                imgLarge = img.url;
                            }

                            if (img.width >= 200) {
                                imgSmall = img.url;
                            } else if (imgSmall.isEmpty() && img.width < 200) {
                                imgSmall = img.url;
                            }
                        }

                        musicAdapter.add(new Music(track.id, track.album.name, track.name, imgLarge, imgSmall, track.preview_url));
                    }
                }
            }
        }

    }
}
