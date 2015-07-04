package com.example.samuelpedro.spotifystreamer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;
import kaaes.spotify.webapi.android.models.Image;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    private final String LOG_TAG = MainActivityFragment.class.getSimpleName();
    public BandsAdapter bandAdapter;
    public List listBand;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {

            View rootView = inflater.inflate(R.layout.fragment_main, container, false);

            EditText editText = (EditText) rootView.findViewById(R.id.fragment_main_editText_id);
            editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                @Override
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEND) {
                        if (v.getText().length() != 0) {
                            //Begin Task
                            FetchArtistTask artistTask = new FetchArtistTask();
                            artistTask.execute(v.getText().toString());
                        }
                        return true;
                    }
                    return false;
                }
            });
            /*
            editText.addTextChangedListener(new TextWatcher() {

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (s.length() != 0) {
                        //Begin Task
                        FetchArtistTask artistTask = new FetchArtistTask();
                        artistTask.execute(s.toString());
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {
                }

            });
            */

            listBand = new ArrayList<>();

            bandAdapter = new BandsAdapter(getActivity(), listBand);

            ListView listView = (ListView) rootView.findViewById(R.id.fragment_main_listView_id);
            listView.setAdapter(bandAdapter);


            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Band band = bandAdapter.getItem(position);
                    Intent intent = new Intent(getActivity(), MusicActivity.class)
                            .putExtra(Intent.EXTRA_TEXT, band.getId());
                    startActivity(intent);
                }
            });

            return rootView;

        } catch (Exception e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        }
    }

    /**
     * Created by Samuel on 23-06-2015.
     */
    public class FetchArtistTask extends AsyncTask<String, Void, List> {

        private final String LOG_TAG = FetchArtistTask.class.getSimpleName();

        @Override
        protected List doInBackground(String... params) {

            if (params.length == 0) return null;

            try {

                SpotifyApi api = new SpotifyApi();
                //api.setAccessToken("myAccessToken");
                SpotifyService spotify = api.getService();
                ArtistsPager results = spotify.searchArtists(params[0]);

                return results.artists.items;

                //List<Artist> la = results.artists.items;
                //return la;

            } catch (Exception e) {

                Log.e(LOG_TAG, "Error ", e);
                return null;
            }
        }

        @Override
        protected void onPostExecute(List artistList) {
            super.onPostExecute(artistList);

            bandAdapter.clear();

            if (artistList.isEmpty()) {
                Context context = getActivity();
                CharSequence text = "Sorry, artist not found!";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();

            } else {
                //bandAdapter.clear();
                for (int i = 0; i < artistList.size(); i++) {
                    Artist artist = (Artist) artistList.get(i);//new Artist();

                    String imgSmall = "http://png-4.findicons.com/files/icons/1676/primo/128/music.png";
                    Iterator<Image> iterator = artist.images.iterator();
                    while (iterator.hasNext()) {
                        Image img = iterator.next();

                        if (img.width >= 200) {
                            imgSmall = img.url;
                        } else if (imgSmall.isEmpty()) {
                            imgSmall = img.url;
                        }
                    }
                    bandAdapter.add(new Band(artist.id, artist.name, imgSmall));
                }

            }
        }
    }
}
