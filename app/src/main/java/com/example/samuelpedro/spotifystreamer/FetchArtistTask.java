package com.example.samuelpedro.spotifystreamer;

import android.os.AsyncTask;
import android.util.Log;

import kaaes.spotify.webapi.android.SpotifyApi;
import kaaes.spotify.webapi.android.SpotifyService;
import kaaes.spotify.webapi.android.models.ArtistsPager;

/**
 * Created by Samuel on 23-06-2015.
 */
public class FetchArtistTask extends AsyncTask<Void, Void, String> {

    private final String LOG_TAG = FetchArtistTask.class.getSimpleName();

    @Override
    protected String doInBackground(Void... params) {

        try {

            SpotifyApi api = new SpotifyApi();
            //api.setAccessToken("myAccessToken");
            SpotifyService spotify = api.getService();
            ArtistsPager results = spotify.searchArtists("Beyonce");

            Log.d(LOG_TAG, "Error " + results);
            Log.i(LOG_TAG, "Error " + results);
            System.out.println("*******************************************");
            System.out.println("*******************************************");
            System.out.println("*******************************************");
            System.out.println("*******************************************");
            System.out.println("*******************************************");
            System.out.println("*******************************************");
            System.out.println("*******************************************");

            System.out.println("" + results);
            System.out.println("" + results.toString());
            System.out.println("" + results.artists.toString());
            System.out.println("" + results.artists.items.iterator().next().name);
            System.out.println("" + results.artists.items.iterator().next().id);
            System.out.println("" + results.artists.items.iterator().next().images.iterator().next().url);
            System.out.println("" + results.artists.items.iterator().next().images.iterator().next().toString());
            System.out.println("" + results.artists.items.iterator().toString());

            System.out.println("*******************************************");
            System.out.println("*******************************************");
            System.out.println("*******************************************");
            System.out.println("*******************************************");
            System.out.println("*******************************************");
            System.out.println("*******************************************");

            return results.artists.items.iterator().next().name;

        } catch (Exception e) {

            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {

            return null;
        }
    }

    @Override
    protected String onPostExecute(String s) {
        super.onPostExecute(s);
        return s;
    }
}
