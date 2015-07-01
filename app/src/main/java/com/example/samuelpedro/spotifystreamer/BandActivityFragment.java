package com.example.samuelpedro.spotifystreamer;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Track;
import kaaes.spotify.webapi.android.models.Tracks;


/**
 * A placeholder fragment containing a simple view.
 */
public class BandActivityFragment extends Fragment {

    public TrackAdapter trackAdapter;
    public List listMusic;

    public BandActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_band, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String idBand = intent.getStringExtra(Intent.EXTRA_TEXT);

            listMusic = new ArrayList<>();
            trackAdapter = new TrackAdapter(getActivity(), listMusic);

            ListView listView = (ListView) rootView.findViewById(R.id.fragment_main_listView_id);
            listView.setAdapter(trackAdapter);
        }
        return rootView;
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

                /*List tracks = new ArrayList<>();
                for (Track track : results.tracks)
                {
                    Track localTrack = track;
                    tracks.add(localTrack.album.);
                }*/
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
                trackAdapter.clear();
                for (int i = 0; i < trackstList.size(); i++) {
                    Track track = (Track) trackstList.get(i);//new Artist();
                    //a = (Artist) artistList.get(i);
                    if (track.album.images.isEmpty()) {
                        trackAdapter.add(new Music(track.id, track.album.name, track.name, "http://png-4.findicons.com/files/icons/1676/primo/128/music.png", "http://png-4.findicons.com/files/icons/1676/primo/128/music.png"));
                    } else {
                        trackAdapter.add(new Music(track.id, track.album.name, track.name, track.album.images.iterator().next().url, track.album.images.iterator().next().url));
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
