package nano.udacity.ishan.popularmovies;

import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;

import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.CardView;


import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

import nano.udacity.ishan.popularmovies.adapters.ReviewAdapter;
import nano.udacity.ishan.popularmovies.adapters.VideoAdapter;
import nano.udacity.ishan.popularmovies.common.Const;
import nano.udacity.ishan.popularmovies.common.Utils;
import nano.udacity.ishan.popularmovies.data.Movie;
import nano.udacity.ishan.popularmovies.data.MovieDetail;
import nano.udacity.ishan.popularmovies.data.MovieVideoList;
import nano.udacity.ishan.popularmovies.data.Review;
import nano.udacity.ishan.popularmovies.data.ReviewList;
import nano.udacity.ishan.popularmovies.data.Video;

import nano.udacity.ishan.popularmovies.data.provider.favmovie.FavmovieColumns;
import nano.udacity.ishan.popularmovies.data.provider.favmovie.FavmovieContentValues;

import nano.udacity.ishan.popularmovies.network.TMDBService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class MovieDetailActivityFragment extends Fragment {
    @Bind(R.id.movie_poster_large_imageview)
    ImageView posterLargeImageView;
    @Bind(R.id.movie_name_textview)
    TextView movieNameTextView;
    @Bind(R.id.movie_overview_textview)
    TextView movieOverviewTextView;
    @Bind(R.id.movie_release_date_textview)
    TextView movieReleaseDateTextView;
    @Bind(R.id.movie_userrating_textview)
    TextView movieUserRatingTextView;
    @Bind(R.id.movie_duration_textview)
    TextView movieDurationTextView;
    @Bind(R.id.favorite_button)
    FloatingActionButton favoriteButton;
    @Bind(R.id.card_view_reviews)
    CardView reviewCardView;
    @Bind(R.id.card_view_trailers)
    CardView trailerCardView;
    @Bind(R.id.movie_trailers_listview)
    ListView trailerListView;
    @Bind(R.id.movie_detail_fragment_scrollview)
    ScrollView movieDetailFragmentScrollView;
    @Bind(R.id.movie_review_listview)
    ListView reviewListView;

    Retrofit retroFit;
    TMDBService tmdbService;

    boolean isMovieFavorite = false;
    Movie thisMovie;
    MovieDetail thisMovieDetail;
    MovieVideoList thisMovieVideoList;
    ArrayList<Video> thisMovieVideos = new ArrayList<Video>();
    VideoAdapter mVideoAdapter;

    ReviewAdapter reviewAdapter;
    List<Review> reviewList = new ArrayList<Review>();

    private static final String LOG_TAG = "MovieDetail";

    public MovieDetailActivityFragment() {
    }

    public void updateMovieDetails(Movie movie) {
        thisMovie = movie;
        updateUI(null);
    }


    View rootView;


    ShareActionProvider shareActionProvider;
    Intent shareIntent;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_detail_fragment, menu);
        super.onCreateOptionsMenu(menu, inflater);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);
        shareActionProvider.setShareIntent(createShareIntent());
    }

    private Intent createShareIntent() {
        shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Movie Trailer");

        /* Set a dummy share intent to begin with, the actual trailer url will be set when they are fetched*/
        String trailerURL = Const.YOUTUBE_BASE_VIEW_URL;
        if (thisMovieVideoList != null && thisMovieVideoList.getVideos().size() > 0) {
            String trailerKey = thisMovieVideoList.getVideos().get(0).getKey();
            trailerURL = Const.YOUTUBE_BASE_VIEW_URL + "?v=" + trailerKey;
        }
        shareIntent.putExtra(Intent.EXTRA_TEXT, trailerURL);
        return shareIntent;
    }

    private void setShareIntent(Intent shareIntent) {
        if (shareActionProvider != null)
            shareActionProvider.setShareIntent(shareIntent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    LinearLayout emptyLayoutView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);

        retroFit = new Retrofit.Builder()
                .baseUrl(Const.TMD_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tmdbService = retroFit.create(TMDBService.class);

        if (thisMovie != null) {
            updateUI(savedInstanceState);
        } else {
            emptyLayoutView = (LinearLayout) rootView.findViewById(R.id.empty_layout);
            emptyLayoutView.setVisibility(View.VISIBLE);
            emptyLayoutView.setMinimumHeight(getActivity().getWindowManager().getDefaultDisplay().getWidth());
            emptyLayoutView.setMinimumHeight(getActivity().getWindowManager().getDefaultDisplay().getHeight());
        }

        return rootView;
    }

    public void updateUI(Bundle savedInstanceState) {
        emptyLayoutView.setVisibility(View.GONE);


        mVideoAdapter = new VideoAdapter(getActivity(), thisMovieVideos);
        trailerListView.setAdapter(mVideoAdapter);
        trailerListView.setDivider(null);
        trailerListView.setDividerHeight(0);
        trailerListView.setFocusable(false);

        reviewAdapter = new ReviewAdapter(getActivity(), reviewList, true);
        reviewListView.setAdapter(reviewAdapter);
        reviewListView.setFocusable(false);

        if (savedInstanceState == null || !savedInstanceState.containsKey("SAVED_MOVIE_DETAILS")) {
            fetchMovieDetails(thisMovie.getId());

            if (thisMovieDetail == null)
                fetchMovieVideos(thisMovie.getId());

            fetchMovieReviews(thisMovie.getId());
        } else {
            thisMovieDetail = (MovieDetail) savedInstanceState.get("SAVED_MOVIE_DETAILS");
            if (thisMovieDetail != null) {
                movieDurationTextView.setText(String.valueOf(thisMovieDetail.getRuntime()) + " mins");
            }


            ArrayList<Video> saveVideos = savedInstanceState.getParcelableArrayList("SAVED_MOVIE_VIDEOS");
            thisMovieVideos.clear();
            thisMovieVideos.addAll(saveVideos);
            mVideoAdapter.notifyDataSetChanged();
            Utils.setListViewHeightBasedOnItems(trailerListView);

            ArrayList<Review> savedReviewList = savedInstanceState.getParcelableArrayList("SAVED_REVIEWS");
            reviewList.clear();
            reviewList.addAll(savedReviewList);
            reviewAdapter.notifyDataSetChanged();
            Utils.setListViewHeightBasedOnItems(reviewListView);
        }
        //Log.v(LOG_TAG, "Movie from parcel: " + thisMovie.toString());

        isMovieFavorite = Utils.isMovieFavorited(this.getContext(), thisMovie.getId());
        Log.v(LOG_TAG, "isMovieFavorited = " + isMovieFavorite);

        if (isMovieFavorite)
            favoriteButton.setImageResource(R.drawable.ic_heart_set);
        else
            favoriteButton.setImageResource(R.drawable.ic_heart_unset);

        movieNameTextView.setText(thisMovie.getTitle());
        movieOverviewTextView.setText(thisMovie.getOverview());
        movieUserRatingTextView.setText(thisMovie.getVoteAverage() + " / 10");
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date releaseDate = simpleDateFormat.parse(thisMovie.getReleaseDate());
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(releaseDate);
            movieReleaseDateTextView.setText(String.valueOf(calendar.get(Calendar.YEAR)));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        /* Constructing the movie poster url
         * TODO: make the poster resolution configurable via a setting
         */
        String moviePosterUrl = Const.URL_MOVIE_POSTER + "/" + Const.SETTING_POSTER_SIZE_THUMBNAIL + thisMovie.getPosterPath();
        Picasso.Builder builder = new Picasso.Builder(getActivity());
        //builder.indicatorsEnabled(true);
        builder.build()
                .load(moviePosterUrl)
                .fit()
                .placeholder(R.drawable.placeholder_w185)
                .error(R.drawable.error)
                .into(posterLargeImageView);


        movieDetailFragmentScrollView.setSmoothScrollingEnabled(true);
        movieDetailFragmentScrollView.smoothScrollTo(0, 0);
        movieDetailFragmentScrollView.fullScroll(ScrollView.FOCUS_UP);


    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("SAVED_MOVIE_DETAILS", thisMovieDetail);
        outState.putParcelableArrayList("SAVED_MOVIE_VIDEOS", thisMovieVideos);
        outState.putParcelableArrayList("SAVED_REVIEWS", (ArrayList<Review>) reviewList);
        super.onSaveInstanceState(outState);
    }

    @OnClick(R.id.favorite_button)
    public void favorite(View v) {
        if (!isMovieFavorite) {
            FavmovieContentValues contentValues = new FavmovieContentValues();
            contentValues.initFromMovie(thisMovie);

            Uri uri = contentValues.insert(getActivity().getContentResolver());
            Log.i(LOG_TAG, "Inserted movie in db with ID:" + ContentUris.parseId(uri));

            Utils.showMessage(v, R.string.message_favorite_added);

            favoriteButton.setImageResource(R.drawable.ic_heart_set);
            isMovieFavorite = true;
        } else {

            //Remove entry from the favorites database
            String selectionClause = FavmovieColumns.MOVIE_ID + " = ?";
            String[] selectionArgs = {""};
            selectionArgs[0] = thisMovie.getId();

            int rowsDeleted = getActivity().getContentResolver().delete(
                    FavmovieColumns.CONTENT_URI,
                    selectionClause,
                    selectionArgs
            );
            if (rowsDeleted > 0) {
                isMovieFavorite = false;
                favoriteButton.setImageResource(R.drawable.ic_heart_unset);
                Utils.showMessage(v, R.string.message_favorite_removed);
            } else {
                Snackbar.make(v, "No rows deleted", Snackbar.LENGTH_SHORT).show();
            }

        }
    }

    @OnClick(R.id.card_view_reviews)
    public void startFullReviewActivity(View v) {
        Intent fullReviewIntent = new Intent(getActivity(), ViewReviewsActivity.class);
        fullReviewIntent.putExtra("MOVIE_TITLE", thisMovieDetail.getTitle());
        fullReviewIntent.putExtra("MOVIE_ID", thisMovieDetail.getId());
        fullReviewIntent.putParcelableArrayListExtra("REVIEW_LIST", (ArrayList<Review>)reviewList);

        startActivity(fullReviewIntent);

        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    private void fetchMovieDetails(String movieId) {
        Call<MovieDetail> movieDetailCall = tmdbService.getMovieDetails(movieId, Const.TMD_API_KEY);
        movieDetailCall.enqueue(new Callback<MovieDetail>() {
            @Override
            public void onResponse(Response<MovieDetail> response) {
                thisMovieDetail = response.body();
                if (thisMovieDetail != null) {
                    movieDurationTextView.setText(String.valueOf(thisMovieDetail.getRuntime()) + " mins");
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Utils.showError(rootView, R.string.err_network);
            }
        });

    }

    private void fetchMovieReviews(String movie_id) {
        Call<ReviewList> reviewListCall = tmdbService.getReviews(movie_id, Const.TMD_API_KEY);
        reviewListCall.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(Response<ReviewList> response) {
                ReviewList reviewListObj = response.body();
                reviewList.clear();
                reviewList.addAll(reviewListObj.getReviews());
                reviewAdapter.notifyDataSetChanged();

                if(reviewList.size()==0){
                    Utils.showError(reviewListView, R.string.msg_no_reviews);
                    reviewCardView.setVisibility(View.GONE);
                }
                Utils.setListViewHeightBasedOnItems(reviewListView);
            }

            @Override
            public void onFailure(Throwable t) {
                Utils.showError(reviewListView, R.string.err_network);
                reviewCardView.setVisibility(View.GONE);
            }
        });
    }

    private void fetchMovieVideos(String movieId) {
        Call<MovieVideoList> movieVideoListCall = tmdbService.getMovieVideos(movieId, Const.TMD_API_KEY);
        movieVideoListCall.enqueue(new Callback<MovieVideoList>() {
            @Override
            public void onResponse(Response<MovieVideoList> response) {
                thisMovieVideoList = response.body();
                if (thisMovieVideoList != null) {
                    thisMovieVideos.clear();
                    thisMovieVideos.addAll(thisMovieVideoList.getVideos());
                    mVideoAdapter.notifyDataSetChanged();

                    /* Do not display the trailer card if no videos were fetched*/
                    if (thisMovieVideoList == null || thisMovieVideoList.getVideos().size() == 0) {
                        trailerCardView.setVisibility(View.GONE);
                    }
                    Utils.setListViewHeightBasedOnItems(trailerListView);

                    /* Update the shareIntent for the movie trailer*/
                    shareIntent.removeExtra(Intent.EXTRA_TEXT);
                    shareIntent.removeExtra(Intent.EXTRA_SUBJECT);
                    String trailerURL = Const.YOUTUBE_BASE_VIEW_URL;
                    if (thisMovieVideoList != null && thisMovieVideoList.getVideos().size() > 0) {
                        String trailerKey = thisMovieVideoList.getVideos().get(0).getKey();
                        trailerURL = Const.YOUTUBE_BASE_VIEW_URL + "?v=" + trailerKey;
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, thisMovie.getTitle() + " - " + thisMovieVideoList.getVideos().get(0).getName());
                        shareIntent.putExtra(Intent.EXTRA_TEXT, trailerURL);
                    }
                    else{
                        // No videos
                        trailerCardView.setVisibility(View.GONE);
                        setShareIntent(null);
                    }


                }
            }

            @Override
            public void onFailure(Throwable t) {
                /* Do not display the trailer card if something bad happened while fetching the video list */
                trailerCardView.setVisibility(View.GONE);
                setShareIntent(null);
            }
        });
    }

    @OnItemClick(R.id.movie_trailers_listview)
    public void launchVideoActivity(int position) {
        Video thisVideo = mVideoAdapter.getItem(position);
        Intent videoIntent = new Intent(Intent.ACTION_VIEW);
        if ("YouTube".equalsIgnoreCase(thisVideo.getSite())) {
            videoIntent.setData(Uri.parse(Const.YOUTUBE_BASE_VIEW_URL + "?v=" + thisVideo.getKey()));
            startActivity(videoIntent);
        } else {
            //Do Nothing?
        }
    }

}
