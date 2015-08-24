package com.example.samuelpedro.spotifystreamer;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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

    //For bundle
    private static final String BAND_NAME = "band_name";
    private static final String BAND_ID = "band_id";
    private BandsAdapter bandAdapter;
    private ArrayList<Band> listBand;
    private ListView listView;

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        listBand = new ArrayList<>();
        bandAdapter = new BandsAdapter(getActivity(), listBand);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

            //get view
        View rootView = inflater.inflate(R.layout.fragment_band, container, false);

        //Search...
        EditText editText = (EditText) rootView.findViewById(R.id.fragment_main_editText_id);

        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    if (v.getText().length() != 0) {
                        if (isNetworkAvailable()) {
                            //Begin Task
                            FetchArtistTask artistTask = new FetchArtistTask();
                            artistTask.execute(v.getText().toString());
                        } else {
                            Context context = getActivity();
                            CharSequence text = "Sorry no internet, please check your connection!";
                            int duration = Toast.LENGTH_SHORT;

                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                    return true;
                }
                return false;
            }
        });

        //get listView
        listView = (ListView) rootView.findViewById(R.id.fragment_main_listView_id);
        //set adapter with list updated
        listView.setAdapter(bandAdapter);

        //create
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Band band = bandAdapter.getItem(position);
                Bundle bundle = new Bundle();
                bundle.putString(BAND_ID, band.getId());
                bundle.putString(BAND_NAME, band.getName());

                ((Callback) getActivity()).onItemSelected(bundle);
            }
        });

            return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (savedInstanceState != null) {
            listBand = savedInstanceState.getParcelableArrayList("bands");
            bandAdapter = new BandsAdapter(getActivity(), listBand);
            listView.setAdapter(bandAdapter);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (listBand != null) {
            outState.putParcelableArrayList("bands", listBand);
        }
    }

    //Check if there is Internet Connection
    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    /**
     * A callback interface that all activities containing this fragment must
     * implement. This mechanism allows activities to be notified of item
     * selections.
     */
    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        void onItemSelected(Bundle extra);
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
