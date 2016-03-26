package nano.udacity.ishan.popularmovies.data.provider.favmovie;

import java.util.Date;

import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import nano.udacity.ishan.popularmovies.data.provider.base.AbstractCursor;

/**
 * Cursor wrapper for the {@code favmovie} table.
 */
public class FavmovieCursor extends AbstractCursor implements FavmovieModel {
    public FavmovieCursor(Cursor cursor) {
        super(cursor);
    }

    /**
     * Primary key.
     */
    public long getId() {
        Long res = getLongOrNull(FavmovieColumns._ID);
        if (res == null)
            throw new NullPointerException("The value of '_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code movie_id} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getMovieId() {
        String res = getStringOrNull(FavmovieColumns.MOVIE_ID);
        if (res == null)
            throw new NullPointerException("The value of 'movie_id' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getTitle() {
        String res = getStringOrNull(FavmovieColumns.TITLE);
        if (res == null)
            throw new NullPointerException("The value of 'title' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code original_title} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getOriginalTitle() {
        String res = getStringOrNull(FavmovieColumns.ORIGINAL_TITLE);
        return res;
    }

    /**
     * Get the {@code original_language} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getOriginalLanguage() {
        String res = getStringOrNull(FavmovieColumns.ORIGINAL_LANGUAGE);
        return res;
    }

    /**
     * Get the {@code release_date} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getReleaseDate() {
        String res = getStringOrNull(FavmovieColumns.RELEASE_DATE);
        if (res == null)
            throw new NullPointerException("The value of 'release_date' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code overview} value.
     * Cannot be {@code null}.
     */
    @NonNull
    public String getOverview() {
        String res = getStringOrNull(FavmovieColumns.OVERVIEW);
        if (res == null)
            throw new NullPointerException("The value of 'overview' in the database was null, which is not allowed according to the model definition");
        return res;
    }

    /**
     * Get the {@code backdrop_path} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getBackdropPath() {
        String res = getStringOrNull(FavmovieColumns.BACKDROP_PATH);
        return res;
    }

    /**
     * Get the {@code poster_path} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPosterPath() {
        String res = getStringOrNull(FavmovieColumns.POSTER_PATH);
        return res;
    }

    /**
     * Get the {@code popularity} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getPopularity() {
        String res = getStringOrNull(FavmovieColumns.POPULARITY);
        return res;
    }

    /**
     * Get the {@code vote_count} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getVoteCount() {
        String res = getStringOrNull(FavmovieColumns.VOTE_COUNT);
        return res;
    }

    /**
     * Get the {@code vote_average} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getVoteAverage() {
        String res = getStringOrNull(FavmovieColumns.VOTE_AVERAGE);
        return res;
    }

    /**
     * Get the {@code video} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getVideo() {
        String res = getStringOrNull(FavmovieColumns.VIDEO);
        return res;
    }

    /**
     * Get the {@code adult} value.
     * Can be {@code null}.
     */
    @Nullable
    public String getAdult() {
        String res = getStringOrNull(FavmovieColumns.ADULT);
        return res;
    }
}
