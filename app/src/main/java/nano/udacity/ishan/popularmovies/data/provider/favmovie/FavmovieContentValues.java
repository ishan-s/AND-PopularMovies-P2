package nano.udacity.ishan.popularmovies.data.provider.favmovie;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import nano.udacity.ishan.popularmovies.data.Movie;
import nano.udacity.ishan.popularmovies.data.provider.base.AbstractContentValues;

/**
 * Content values wrapper for the {@code favmovie} table.
 */
public class FavmovieContentValues extends AbstractContentValues {
    @Override
    public Uri uri() {
        return FavmovieColumns.CONTENT_URI;
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param contentResolver The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(ContentResolver contentResolver, @Nullable FavmovieSelection where) {
        return contentResolver.update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    /**
     * Update row(s) using the values stored by this object and the given selection.
     *
     * @param context The content resolver to use.
     * @param where The selection to use (can be {@code null}).
     */
    public int update(Context context, @Nullable FavmovieSelection where) {
        return context.getContentResolver().update(uri(), values(), where == null ? null : where.sel(), where == null ? null : where.args());
    }

    public FavmovieContentValues initFromMovie(Movie movie){

        putAdult(movie.getAdult());
        putBackdropPath(movie.getBackdropPath());
        putMovieId(movie.getId());
        putOriginalLanguage(movie.getOriginalLanguage());
        putOriginalTitle(movie.getOriginalTitle());
        putOverview(movie.getOverview());
        putPopularity(movie.getPopularity());
        putPosterPath(movie.getPosterPath());
        putReleaseDate(movie.getReleaseDate());
        putTitle(movie.getTitle());
        putVideo(movie.getVideo());
        putVoteAverage(movie.getVoteAverage());
        putVoteCount(movie.getVoteCount());

        return this;
    }

    public FavmovieContentValues putMovieId(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("movieId must not be null");
        mContentValues.put(FavmovieColumns.MOVIE_ID, value);
        return this;
    }


    public FavmovieContentValues putTitle(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("title must not be null");
        mContentValues.put(FavmovieColumns.TITLE, value);
        return this;
    }


    public FavmovieContentValues putOriginalTitle(@Nullable String value) {
        mContentValues.put(FavmovieColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavmovieContentValues putOriginalTitleNull() {
        mContentValues.putNull(FavmovieColumns.ORIGINAL_TITLE);
        return this;
    }

    public FavmovieContentValues putOriginalLanguage(@Nullable String value) {
        mContentValues.put(FavmovieColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public FavmovieContentValues putOriginalLanguageNull() {
        mContentValues.putNull(FavmovieColumns.ORIGINAL_LANGUAGE);
        return this;
    }

    public FavmovieContentValues putReleaseDate(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("releaseDate must not be null");
        mContentValues.put(FavmovieColumns.RELEASE_DATE, value);
        return this;
    }


    public FavmovieContentValues putOverview(@NonNull String value) {
        if (value == null) throw new IllegalArgumentException("overview must not be null");
        mContentValues.put(FavmovieColumns.OVERVIEW, value);
        return this;
    }


    public FavmovieContentValues putBackdropPath(@Nullable String value) {
        mContentValues.put(FavmovieColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavmovieContentValues putBackdropPathNull() {
        mContentValues.putNull(FavmovieColumns.BACKDROP_PATH);
        return this;
    }

    public FavmovieContentValues putPosterPath(@Nullable String value) {
        mContentValues.put(FavmovieColumns.POSTER_PATH, value);
        return this;
    }

    public FavmovieContentValues putPosterPathNull() {
        mContentValues.putNull(FavmovieColumns.POSTER_PATH);
        return this;
    }

    public FavmovieContentValues putPopularity(@Nullable String value) {
        mContentValues.put(FavmovieColumns.POPULARITY, value);
        return this;
    }

    public FavmovieContentValues putPopularityNull() {
        mContentValues.putNull(FavmovieColumns.POPULARITY);
        return this;
    }

    public FavmovieContentValues putVoteCount(@Nullable String value) {
        mContentValues.put(FavmovieColumns.VOTE_COUNT, value);
        return this;
    }

    public FavmovieContentValues putVoteCountNull() {
        mContentValues.putNull(FavmovieColumns.VOTE_COUNT);
        return this;
    }

    public FavmovieContentValues putVoteAverage(@Nullable String value) {
        mContentValues.put(FavmovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavmovieContentValues putVoteAverageNull() {
        mContentValues.putNull(FavmovieColumns.VOTE_AVERAGE);
        return this;
    }

    public FavmovieContentValues putVideo(@Nullable String value) {
        mContentValues.put(FavmovieColumns.VIDEO, value);
        return this;
    }

    public FavmovieContentValues putVideoNull() {
        mContentValues.putNull(FavmovieColumns.VIDEO);
        return this;
    }

    public FavmovieContentValues putAdult(@Nullable String value) {
        mContentValues.put(FavmovieColumns.ADULT, value);
        return this;
    }

    public FavmovieContentValues putAdultNull() {
        mContentValues.putNull(FavmovieColumns.ADULT);
        return this;
    }
}
