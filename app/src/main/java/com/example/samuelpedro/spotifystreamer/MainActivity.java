package com.example.samuelpedro.spotifystreamer;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity implements MainActivityFragment.Callback {

    public static final String MUSIC_TAG = "music_tag";
    boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //see if is table ou phone version
        //if view have fragment music_container
        if (findViewById(R.id.music_container) != null) {
            mTwoPane = true;
            // In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.music_container, new MusicActivityFragment(), MUSIC_TAG)
                        .commit();
            }
        } else {
            mTwoPane = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onItemSelected(Bundle extra) {

        int myInt = (mTwoPane) ? 1 : 0;
        extra.putInt("TwoPane", myInt);
        //onItemSelecte3d from MainactivityFragment
        if (mTwoPane) {
            //instanciate and put values
            MusicActivityFragment fragment = new MusicActivityFragment();
            fragment.setArguments(extra);

            //replace empty FrameLayout with fragment
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.music_container, fragment, MUSIC_TAG)
                    .commit();

        } else {
            //Phone version start activity
            Intent intent = new Intent(this, MusicActivity.class)
                    .putExtras(extra);
            startActivity(intent);
        }
    }
}
