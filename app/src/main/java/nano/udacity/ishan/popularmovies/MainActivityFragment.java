package nano.udacity.ishan.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import nano.udacity.ishan.popularmovies.adapters.MovieAdapter;
import nano.udacity.ishan.popularmovies.common.Const;
import nano.udacity.ishan.popularmovies.common.Utils;
import nano.udacity.ishan.popularmovies.data.DiscoverMovies;
import nano.udacity.ishan.popularmovies.data.Movie;

import nano.udacity.ishan.popularmovies.data.provider.favmovie.FavmovieColumns;
import nano.udacity.ishan.popularmovies.network.TMDBService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MainActivityFragment extends Fragment {
    @Bind(R.id.main_image_gridview)
    GridView imageGridView;
    @Bind(R.id.indicator_sortorder_textview)
    TextView sortOrderIndicatorTextView;

    MovieAdapter mMovieAdapter;
    ArrayList<Movie> mMovies = new ArrayList<Movie>();
    String currentSortOrder = Const.SETTING_SORT_ORDER_POPULARITY;

    Retrofit retroFit;
    TMDBService tmdbService;

    private static final String LOG_TAG = "MainActivityFragment";

    /* Interface for communicating with activity */
    public interface OnMovieSelectedListener {
        void OnMovieSelected(Movie movie);
    }

    public MainActivityFragment() {
    }

    OnMovieSelectedListener movieSelectedListener = null;
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            movieSelectedListener = (OnMovieSelectedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTutSelectedListener");
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("SAVED_MOVIES", mMovies);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    /* Utility method to refetch images from TMD and refresh the image grid */
    private void reloadImageGrid(String sortOrder) {
        switch(sortOrder) {
            case Const.SETTING_SORT_ORDER_RATING:
            case Const.SETTING_SORT_ORDER_POPULARITY:
                callDiscoverMovies(tmdbService, sortOrder);
                break;
            case Const.SETTING_SORT_ORDER_FAVORITES:
                mMovies.clear();
                mMovies.addAll(fetchFavMoviesFromDB());
                break;
        }
        showSortOrderIndicator(sortOrder);
        mMovieAdapter.notifyDataSetChanged();
    }

    private void showSortOrderIndicator(String sortOrder){
        View v = rootView==null ? imageGridView : rootView;

        switch(sortOrder){
            case Const.SETTING_SORT_ORDER_FAVORITES:
                sortOrderIndicatorTextView.setText(R.string.label_sort_favorites);
                break;
            case Const.SETTING_SORT_ORDER_POPULARITY:
                sortOrderIndicatorTextView.setText(R.string.label_sort_popular);
                break;
            case Const.SETTING_SORT_ORDER_RATING:
                sortOrderIndicatorTextView.setText(R.string.label_sort_rated);
                break;
        }
    }

    public List<Movie> fetchFavMoviesFromDB() {
        ArrayList<Movie> favMovies = new ArrayList<Movie>();

        String selectionClause = null;
        String[] selectionArgs = null;
        String sortOrder = null;
        Cursor favMoviesCursor = getActivity().getContentResolver().query(
                FavmovieColumns.CONTENT_URI,
                FavmovieColumns.ALL_COLUMNS,
                selectionClause,
                selectionArgs,
                sortOrder
        );

        if (favMoviesCursor != null) {
            if(favMoviesCursor.getCount()==0){
                Utils.showError(rootView, R.string.err_no_favorites);
            }
            while (favMoviesCursor.moveToNext()) {
                Movie aMovie = new Movie();
                aMovie.setId(favMoviesCursor.getString(favMoviesCursor.getColumnIndex(FavmovieColumns.MOVIE_ID)));
                aMovie.setAdult(favMoviesCursor.getString(favMoviesCursor.getColumnIndex(FavmovieColumns.ADULT)));
                aMovie.setBackdropPath(favMoviesCursor.getString(favMoviesCursor.getColumnIndex(FavmovieColumns.BACKDROP_PATH)));
                aMovie.setOriginalLanguage(favMoviesCursor.getString(favMoviesCursor.getColumnIndex(FavmovieColumns.ORIGINAL_LANGUAGE)));
                aMovie.setOriginalTitle(favMoviesCursor.getString(favMoviesCursor.getColumnIndex(FavmovieColumns.ORIGINAL_TITLE)));
                aMovie.setOverview(favMoviesCursor.getString(favMoviesCursor.getColumnIndex(FavmovieColumns.OVERVIEW)));
                aMovie.setPopularity(favMoviesCursor.getString(favMoviesCursor.getColumnIndex(FavmovieColumns.POPULARITY)));
                aMovie.setPosterPath(favMoviesCursor.getString(favMoviesCursor.getColumnIndex(FavmovieColumns.POSTER_PATH)));
                aMovie.setReleaseDate(favMoviesCursor.getString(favMoviesCursor.getColumnIndex(FavmovieColumns.RELEASE_DATE)));
                aMovie.setTitle(favMoviesCursor.getString(favMoviesCursor.getColumnIndex(FavmovieColumns.TITLE)));
                aMovie.setVideo(favMoviesCursor.getString(favMoviesCursor.getColumnIndex(FavmovieColumns.VIDEO)));
                aMovie.setVoteAverage(favMoviesCursor.getString(favMoviesCursor.getColumnIndex(FavmovieColumns.VOTE_AVERAGE)));
                aMovie.setVoteCount(favMoviesCursor.getString(favMoviesCursor.getColumnIndex(FavmovieColumns.VOTE_COUNT)));

                favMovies.add(aMovie);
            }
            favMoviesCursor.close();
        }
        return favMovies;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        SharedPreferences.Editor editor = sharedPreferences.edit();

        switch (item.getItemId()) {
            case R.id.sortorder_favorites:
                editor.putString(getString(R.string.key_sortorder), Const.SETTING_SORT_ORDER_FAVORITES);
                editor.commit();

                currentSortOrder = Const.SETTING_SORT_ORDER_FAVORITES;
                reloadImageGrid(currentSortOrder);
                break;

            case R.id.sortorder_popularity:
                editor.putString(getString(R.string.key_sortorder), Const.SETTING_SORT_ORDER_POPULARITY);
                editor.commit();

                currentSortOrder = Const.SETTING_SORT_ORDER_POPULARITY;
                reloadImageGrid(currentSortOrder);
                break;
            case R.id.sortorder_rating:
                editor.putString(getString(R.string.key_sortorder), Const.SETTING_SORT_ORDER_RATING);
                editor.commit();

                currentSortOrder = Const.SETTING_SORT_ORDER_RATING;
                reloadImageGrid(currentSortOrder);
                break;
            case R.id.action_refresh:
                reloadImageGrid(currentSortOrder);
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, rootView);
        setHasOptionsMenu(true);

        retroFit = new Retrofit.Builder()
                .baseUrl(Const.TMD_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tmdbService = retroFit.create(TMDBService.class);

        mMovieAdapter = new MovieAdapter(getActivity(), mMovies);
        imageGridView.setAdapter(mMovieAdapter);
        imageGridView.setChoiceMode(GridView.CHOICE_MODE_SINGLE);

        //set the item selector only for dual-pane layout
        if (Utils.isDualPaneLayout(getActivity())) {
            imageGridView.setSelector(getResources().getDrawable(R.drawable.gridview_selector));
            imageGridView.setDrawSelectorOnTop(true);
        }

        if (savedInstanceState == null || !savedInstanceState.containsKey("SAVED_MOVIES")) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
            String sortOrder = sharedPreferences.getString(getString(R.string.key_sortorder), Const.SETTING_SORT_ORDER_POPULARITY);

            callDiscoverMovies(tmdbService, sortOrder);
            showSortOrderIndicator(sortOrder);
        } else {
            mMovies.clear();
            mMovies.addAll((ArrayList) savedInstanceState.getParcelableArrayList("SAVED_MOVIES"));
            mMovieAdapter.notifyDataSetChanged();
        }
        return rootView;
    }

    @OnItemClick(R.id.main_image_gridview)
    public void openMovieDetails(AdapterView<?> parent, View view, int position, long id) {
        Movie thisMovie = mMovieAdapter.getItem(position);
        imageGridView.smoothScrollToPosition(position);

        view.startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.image_click));

        movieSelectedListener.OnMovieSelected(thisMovie);

        if (!Utils.isDualPaneLayout(getActivity()))
            getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void callDiscoverMovies(TMDBService service, String sortOrder) {
        Call<DiscoverMovies> discoverMoviesCall = service.discoverMovies(Const.TMD_API_KEY, sortOrder);
        discoverMoviesCall.enqueue(new Callback<DiscoverMovies>() {
            @Override
            public void onResponse(Response<DiscoverMovies> response) {
                mMovies.clear();
                mMovies.addAll((response.body()).getMovies());
                mMovieAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Throwable t) {
                Utils.showError(rootView, R.string.err_network);
            }
        });
    }


}
