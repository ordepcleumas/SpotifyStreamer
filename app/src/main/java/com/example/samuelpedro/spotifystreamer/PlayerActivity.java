package com.example.samuelpedro.spotifystreamer;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

public class PlayerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);

        if (savedInstanceState == null) {

            Bundle extras = new Bundle();
            extras.putParcelableArrayList(MusicActivityFragment.TOP_10_LIST_SONGS, getIntent().getParcelableArrayListExtra(MusicActivityFragment.TOP_10_LIST_SONGS));
            extras.putInt(MusicActivityFragment.POSITION, getIntent().getIntExtra(MusicActivityFragment.POSITION, 1));
            extras.putString(MusicActivityFragment.BAND_NAME, getIntent().getStringExtra(MusicActivityFragment.BAND_NAME));

            PlayerActivityFragment fragment = new PlayerActivityFragment().newInstance();
            fragment.setArguments(extras);

            getSupportFragmentManager().beginTransaction()
                    .add(R.id.player_container, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_player, menu);
        return true;
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public Intent getParentActivityIntent() {
        return super.getParentActivityIntent().addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
