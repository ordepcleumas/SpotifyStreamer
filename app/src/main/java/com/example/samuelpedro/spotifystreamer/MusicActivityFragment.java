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

    public MusicAdapter musicAdapter;
    public List listMusic;

    public MusicActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_music, container, false);

        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(Intent.EXTRA_TEXT)) {
            String idBand = intent.getStringExtra(Intent.EXTRA_TEXT);

            FetchSongsTask fetchSongsTask = new FetchSongsTask();
            fetchSongsTask.execute(idBand);


            listMusic = new ArrayList<Music>();
            musicAdapter = new MusicAdapter(getActivity(), listMusic);

            ListView listView = (ListView) rootView.findViewById(R.id.list_view_musics);
            listView.setAdapter(musicAdapter);
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


                        String urlLarge = "http://png-4.findicons.com/files/icons/1676/primo/128/music.png";
                        String urlSmall = "http://png-4.findicons.com/files/icons/1676/primo/128/music.png";
                        Iterator<Image> iterator = track.album.images.iterator();
                        while (iterator.hasNext()) {
                            Image img = iterator.next();

                            if (img.width >= 640) {
                                urlLarge = img.url;
                            }

                            if (img.width >= 200) {
                                urlSmall = img.url;
                            } else if (urlSmall.isEmpty() && img.width < 200) {
                                urlSmall = img.url;
                            }
                        }

                        musicAdapter.add(new Music(track.id, track.album.name, track.name, track.album.images.iterator().next().url, track.album.images.iterator().next().url));
                    }
                }
            }
        }

    }
}
