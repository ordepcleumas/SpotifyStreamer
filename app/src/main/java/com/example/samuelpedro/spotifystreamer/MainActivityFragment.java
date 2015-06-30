package com.example.samuelpedro.spotifystreamer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;


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

            listBand = new ArrayList<>();

            bandAdapter = new BandsAdapter(getActivity(), listBand);

            ListView listView = (ListView) rootView.findViewById(R.id.fragment_main_listView_id);
            listView.setAdapter(bandAdapter);

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

            if (!artistList.isEmpty()) {
                bandAdapter.clear();
                for (int i = 0; i < artistList.size(); i++) {
                    Artist a = (Artist) artistList.get(i);//new Artist();
                    //a = (Artist) artistList.get(i);
                    if (a.images.isEmpty()) {
                        bandAdapter.add(new Band(a.id, a.name, "http://png-4.findicons.com/files/icons/1676/primo/128/music.png"));
                    } else {
                        bandAdapter.add(new Band(a.id, a.name, a.images.iterator().next().url));
                    }
                }
            }

            //listBand = bandList;

            //bandAdapter = new BandsAdapter<Band>(getActivity(),bandList);


            //bandAdapter = new BandsAdapter(getActivity(), listBand);
            //for (Object band: bandList){
            //
            //}

            //for (int i = 0; i < artistList.size() ; i++) {
            //    System.out.println(artistList.get(i));
            //}

            //Iterator<Artist> it = artistsPager.artists.items.iterator();

            //List<Band> listBands = new ArrayList<>();
//
            //while (it.hasNext()) {
            //    Artist art = it.next();
            //    listBands.add(new Band(art.id, art.name, art.images.listIterator(3).next().url.toString()));
            //}
//
            ////Attaching the Adapter to a ListView
//
            //// Construct the data source
            ////ArrayList<User> arrayOfUsers = new ArrayList<User>();
//
            //// Create the adapter to convert the array to views
            //BandsAdapter adapter;
            //adapter = new BandsAdapter(, list);
            //// Attach the adapter to a ListView
            //ListView listView = (ListView) findViewById(fragment_main_listView_id);
            //listView.setAdapter(adapter);
//
//
            //adapter.clear();
            //adapter.addAll(list);
        }

    }
}
