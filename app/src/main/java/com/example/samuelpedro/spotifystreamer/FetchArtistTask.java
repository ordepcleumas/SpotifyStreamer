package com.example.samuelpedro.spotifystreamer;

import android.os.AsyncTask;
import android.util.Log;

import java.util.List;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.Artist;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by Samuel on 23-06-2015.
 */
public class FetchArtistTask extends AsyncTask<String, Void, List> {

    private final String LOG_TAG = FetchArtistTask.class.getSimpleName();

    @Override
    protected List doInBackground(String... params) {

        try {

            SpotifyApi api = new SpotifyApi();
            //api.setAccessToken("myAccessToken");
            SpotifyService spotify = api.getService();
            ArtistsPager results = spotify.searchArtists(params[0]);
            List<Artist> la = results.artists.items;

            return la;

        } catch (Exception e) {

            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {

            return null;
        }
    }

    @Override
    protected void onPostExecute(List artistList) {
        super.onPostExecute(artistList);

        for (int i = 0; i < artistList.size(); i++) {
            System.out.println(artistList.get(i));
        }

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
