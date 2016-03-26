package nano.udacity.ishan.popularmovies;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import nano.udacity.ishan.popularmovies.common.Const;
import nano.udacity.ishan.popularmovies.data.Movie;

public class MainActivity extends AppCompatActivity
        implements MainActivityFragment.OnMovieSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        storeDisplayMode();

    }

    protected void storeDisplayMode() {
        MovieDetailActivityFragment movieDetailActivityFragment = (MovieDetailActivityFragment) getSupportFragmentManager().findFragmentById(R.id.movie_detail_fragment);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        //Set a preference for the display mode to indicate dual-pane layout if the device is a tablet
        boolean isTablet = getResources().getBoolean(R.bool.isTablet);
        if (!isTablet && (movieDetailActivityFragment == null || !movieDetailActivityFragment.isInLayout()))
            editor.putInt(Const.KEY_FRAGMENT_DISPLAY_MODE, Const.FRAGMENT_DISPLAY_MODE_SINGLE);
        else
            editor.putInt(Const.KEY_FRAGMENT_DISPLAY_MODE, Const.FRAGMENT_DISPLAY_MODE_DUAL);

        editor.commit();
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
            //TODO: Implement SettingsActivity
            Snackbar.make(getCurrentFocus(), "[TODO] No settings to implement just yet!", Snackbar.LENGTH_SHORT).show();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void OnMovieSelected(Movie movie) {

        MovieDetailActivityFragment movieDetailActivityFragment = (MovieDetailActivityFragment) getSupportFragmentManager().findFragmentById(R.id.movie_detail_fragment);

        if (movieDetailActivityFragment == null || !movieDetailActivityFragment.isInLayout()) {
            Intent openMovieIntent = new Intent(getApplicationContext(), MovieDetailActivity.class);
            openMovieIntent.putExtra("MOVIE_PARCEL", movie);
            startActivity(openMovieIntent);
        } else {
            movieDetailActivityFragment.updateMovieDetails(movie);
        }
    }
}
