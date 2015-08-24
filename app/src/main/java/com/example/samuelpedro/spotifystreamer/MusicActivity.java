package com.example.samuelpedro.spotifystreamer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MusicActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_music);
        if (savedInstanceState == null) {
            Bundle extras = new Bundle();
            extras.putString(MusicActivityFragment.BAND_ID, getIntent().getStringExtra("id"));
            extras.putString(MusicActivityFragment.BAND_NAME, getIntent().getStringExtra("band"));
            extras.putInt("TwoPane", getIntent().getIntExtra("TwoPane", 1));

            MusicActivityFragment fragment = new MusicActivityFragment();
            fragment.setArguments(extras);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.music_container, fragment)
                    .commit();
        }
    }

    //To return to the previous activity that is already running
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        //FLAG_ACTIVITY_CLEAR_TOP - If set, and the activity being launched is already running in the current task,
        // then instead of launching a new instance of that activity, all of the other activities
        // on top of it will be closed and this Intent will be delivered to the (now on top)
        // old activity as a new Intent.
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_band, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
