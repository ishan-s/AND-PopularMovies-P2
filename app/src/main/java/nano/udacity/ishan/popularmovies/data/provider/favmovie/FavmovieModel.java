package nano.udacity.ishan.popularmovies.data.provider.favmovie;

import nano.udacity.ishan.popularmovies.data.provider.base.BaseModel;

import java.util.Date;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Movie info fetched by the discover/movie service
 */
public interface FavmovieModel extends BaseModel {

    /**
     * Get the {@code movie_id} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getMovieId();

    /**
     * Get the {@code title} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getTitle();

    /**
     * Get the {@code original_title} value.
     * Can be {@code null}.
     */
    @Nullable
    String getOriginalTitle();

    /**
     * Get the {@code original_language} value.
     * Can be {@code null}.
     */
    @Nullable
    String getOriginalLanguage();

    /**
     * Get the {@code release_date} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getReleaseDate();

    /**
     * Get the {@code overview} value.
     * Cannot be {@code null}.
     */
    @NonNull
    String getOverview();

    /**
     * Get the {@code backdrop_path} value.
     * Can be {@code null}.
     */
    @Nullable
    String getBackdropPath();

    /**
     * Get the {@code poster_path} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPosterPath();

    /**
     * Get the {@code popularity} value.
     * Can be {@code null}.
     */
    @Nullable
    String getPopularity();

    /**
     * Get the {@code vote_count} value.
     * Can be {@code null}.
     */
    @Nullable
    String getVoteCount();

    /**
     * Get the {@code vote_average} value.
     * Can be {@code null}.
     */
    @Nullable
    String getVoteAverage();

    /**
     * Get the {@code video} value.
     * Can be {@code null}.
     */
    @Nullable
    String getVideo();

    /**
     * Get the {@code adult} value.
     * Can be {@code null}.
     */
    @Nullable
    String getAdult();
}
