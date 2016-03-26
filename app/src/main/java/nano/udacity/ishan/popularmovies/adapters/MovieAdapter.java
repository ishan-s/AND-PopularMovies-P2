package nano.udacity.ishan.popularmovies.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import nano.udacity.ishan.popularmovies.R;
import nano.udacity.ishan.popularmovies.common.Const;
import nano.udacity.ishan.popularmovies.common.Utils;
import nano.udacity.ishan.popularmovies.data.Movie;
import nano.udacity.ishan.popularmovies.views.CheckableImageView;

/**
 * Created by Ishan on 06-02-2016.
 */
public class MovieAdapter extends ArrayAdapter<Movie> {
    private static final String LOG_TAG = "DEBUG";
    ArrayList<Movie> mMovies = new ArrayList<Movie>();

    public int getSelectedMoviePosition() {
        return selectedMoviePosition;
    }

    public void setSelectedMoviePosition(int selectedMoviePosition) {
        this.selectedMoviePosition = selectedMoviePosition;
    }

    int selectedMoviePosition = -1;

    public void setmMovies(ArrayList<Movie> mMovies) {
        this.mMovies = mMovies;
    }

    public MovieAdapter(Activity context, List<Movie> movies) {
        super(context, 0, movies);

        mMovies = (ArrayList<Movie>) movies;
    }

    @Override
    public int getCount() {
        return mMovies == null ? 0 : mMovies.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Movie getItem(int position) {
        return mMovies == null ? null : mMovies.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie viewMovie = getItem(position);

        if (convertView == null) {
            convertView = new CheckableImageView(getContext());
        }
        final CheckableImageView view = (CheckableImageView) convertView;
        view.setFocusable(false);


        /* Construct the poster download url - Hardcoded the poster resolution for now.
           TODO: Make the poster resolution i.e. poster size configurable via a setting
         */
        String url = Const.URL_MOVIE_POSTER + "/" + Const.SETTING_POSTER_SIZE_THUMBNAIL + getItem(position).getPosterPath();

        Picasso.Builder builder = new Picasso.Builder(getContext());

        /*
        DEBUG purposes
        builder.indicatorsEnabled(true);
         */

        //TODO: Better optimized way to store/retrieve favorites. Need to move the flag to the Movie object itself.
        boolean isFavorited = Utils.isMovieFavorited(getContext(), viewMovie.getId());

        //If movie is favorited, we'll draw a heart on those movie posters.
        //This is attempted to be done here by using a callback that Picasso provides,
        //and drawing a layer with the heart on top of the existing image that Picasso loaded.
        //There might be bette ways of doing this.. but I found this one to work well.
        if (isFavorited) {
            builder.build()
                    .load(url)
                    .memoryPolicy(MemoryPolicy.NO_STORE) //Picasso OOM Bug Fix - Not sure if this is the correct fix, but without this memory policy in place app FC's after some scroll events on the gridview
                    .fit()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .tag(getContext())
                    .into(view, new Callback() {
                        @Override
                        public void onSuccess() {
                            Resources r = getContext().getResources();

                            Drawable[] layers = new Drawable[2];
                            layers[0] = view.getDrawable();

                            BitmapDrawable heart = (BitmapDrawable) r.getDrawable(R.drawable.ic_heart_set);
                            heart.setGravity(Gravity.TOP | Gravity.RIGHT);
                            layers[1] = heart;

                            LayerDrawable movieFavLayer = new LayerDrawable(layers);
                            view.setImageDrawable(movieFavLayer);
                        }

                        @Override
                        public void onError() {

                        }
                    });

        } else {
            builder.build()
                    .load(url)
                    .memoryPolicy(MemoryPolicy.NO_STORE) //Picasso OOM Bug Fix - Not sure if this is the correct fix, but without this memory policy in place app FC's after some scroll events on the gridview
                    .fit()
                    .placeholder(R.drawable.placeholder)
                    .error(R.drawable.error)
                    .tag(getContext())
                    .into(view);

        }

        return view;
    }
}
