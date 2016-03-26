package nano.udacity.ishan.popularmovies.data.provider.favmovie;

import java.util.Date;

import android.content.Context;
import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;

import nano.udacity.ishan.popularmovies.data.provider.base.AbstractSelection;

/**
 * Selection for the {@code favmovie} table.
 */
public class FavmovieSelection extends AbstractSelection<FavmovieSelection> {
    @Override
    protected Uri baseUri() {
        return FavmovieColumns.CONTENT_URI;
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param contentResolver The content resolver to query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavmovieCursor} object, which is positioned before the first entry, or null.
     */
    public FavmovieCursor query(ContentResolver contentResolver, String[] projection) {
        Cursor cursor = contentResolver.query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavmovieCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(contentResolver, null)}.
     */
    public FavmovieCursor query(ContentResolver contentResolver) {
        return query(contentResolver, null);
    }

    /**
     * Query the given content resolver using this selection.
     *
     * @param context The context to use for the query.
     * @param projection A list of which columns to return. Passing null will return all columns, which is inefficient.
     * @return A {@code FavmovieCursor} object, which is positioned before the first entry, or null.
     */
    public FavmovieCursor query(Context context, String[] projection) {
        Cursor cursor = context.getContentResolver().query(uri(), projection, sel(), args(), order());
        if (cursor == null) return null;
        return new FavmovieCursor(cursor);
    }

    /**
     * Equivalent of calling {@code query(context, null)}.
     */
    public FavmovieCursor query(Context context) {
        return query(context, null);
    }


    public FavmovieSelection id(long... value) {
        addEquals("favmovie." + FavmovieColumns._ID, toObjectArray(value));
        return this;
    }

    public FavmovieSelection idNot(long... value) {
        addNotEquals("favmovie." + FavmovieColumns._ID, toObjectArray(value));
        return this;
    }

    public FavmovieSelection orderById(boolean desc) {
        orderBy("favmovie." + FavmovieColumns._ID, desc);
        return this;
    }

    public FavmovieSelection orderById() {
        return orderById(false);
    }

    public FavmovieSelection movieId(String... value) {
        addEquals(FavmovieColumns.MOVIE_ID, value);
        return this;
    }

    public FavmovieSelection movieIdNot(String... value) {
        addNotEquals(FavmovieColumns.MOVIE_ID, value);
        return this;
    }

    public FavmovieSelection movieIdLike(String... value) {
        addLike(FavmovieColumns.MOVIE_ID, value);
        return this;
    }

    public FavmovieSelection movieIdContains(String... value) {
        addContains(FavmovieColumns.MOVIE_ID, value);
        return this;
    }

    public FavmovieSelection movieIdStartsWith(String... value) {
        addStartsWith(FavmovieColumns.MOVIE_ID, value);
        return this;
    }

    public FavmovieSelection movieIdEndsWith(String... value) {
        addEndsWith(FavmovieColumns.MOVIE_ID, value);
        return this;
    }

    public FavmovieSelection orderByMovieId(boolean desc) {
        orderBy(FavmovieColumns.MOVIE_ID, desc);
        return this;
    }

    public FavmovieSelection orderByMovieId() {
        orderBy(FavmovieColumns.MOVIE_ID, false);
        return this;
    }

    public FavmovieSelection title(String... value) {
        addEquals(FavmovieColumns.TITLE, value);
        return this;
    }

    public FavmovieSelection titleNot(String... value) {
        addNotEquals(FavmovieColumns.TITLE, value);
        return this;
    }

    public FavmovieSelection titleLike(String... value) {
        addLike(FavmovieColumns.TITLE, value);
        return this;
    }

    public FavmovieSelection titleContains(String... value) {
        addContains(FavmovieColumns.TITLE, value);
        return this;
    }

    public FavmovieSelection titleStartsWith(String... value) {
        addStartsWith(FavmovieColumns.TITLE, value);
        return this;
    }

    public FavmovieSelection titleEndsWith(String... value) {
        addEndsWith(FavmovieColumns.TITLE, value);
        return this;
    }

    public FavmovieSelection orderByTitle(boolean desc) {
        orderBy(FavmovieColumns.TITLE, desc);
        return this;
    }

    public FavmovieSelection orderByTitle() {
        orderBy(FavmovieColumns.TITLE, false);
        return this;
    }

    public FavmovieSelection originalTitle(String... value) {
        addEquals(FavmovieColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavmovieSelection originalTitleNot(String... value) {
        addNotEquals(FavmovieColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavmovieSelection originalTitleLike(String... value) {
        addLike(FavmovieColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavmovieSelection originalTitleContains(String... value) {
        addContains(FavmovieColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavmovieSelection originalTitleStartsWith(String... value) {
        addStartsWith(FavmovieColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavmovieSelection originalTitleEndsWith(String... value) {
        addEndsWith(FavmovieColumns.ORIGINAL_TITLE, value);
        return this;
    }

    public FavmovieSelection orderByOriginalTitle(boolean desc) {
        orderBy(FavmovieColumns.ORIGINAL_TITLE, desc);
        return this;
    }

    public FavmovieSelection orderByOriginalTitle() {
        orderBy(FavmovieColumns.ORIGINAL_TITLE, false);
        return this;
    }

    public FavmovieSelection originalLanguage(String... value) {
        addEquals(FavmovieColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public FavmovieSelection originalLanguageNot(String... value) {
        addNotEquals(FavmovieColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public FavmovieSelection originalLanguageLike(String... value) {
        addLike(FavmovieColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public FavmovieSelection originalLanguageContains(String... value) {
        addContains(FavmovieColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public FavmovieSelection originalLanguageStartsWith(String... value) {
        addStartsWith(FavmovieColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public FavmovieSelection originalLanguageEndsWith(String... value) {
        addEndsWith(FavmovieColumns.ORIGINAL_LANGUAGE, value);
        return this;
    }

    public FavmovieSelection orderByOriginalLanguage(boolean desc) {
        orderBy(FavmovieColumns.ORIGINAL_LANGUAGE, desc);
        return this;
    }

    public FavmovieSelection orderByOriginalLanguage() {
        orderBy(FavmovieColumns.ORIGINAL_LANGUAGE, false);
        return this;
    }

    public FavmovieSelection releaseDate(String... value) {
        addEquals(FavmovieColumns.RELEASE_DATE, value);
        return this;
    }

    public FavmovieSelection releaseDateNot(String... value) {
        addNotEquals(FavmovieColumns.RELEASE_DATE, value);
        return this;
    }

    public FavmovieSelection releaseDateLike(String... value) {
        addLike(FavmovieColumns.RELEASE_DATE, value);
        return this;
    }

    public FavmovieSelection releaseDateContains(String... value) {
        addContains(FavmovieColumns.RELEASE_DATE, value);
        return this;
    }

    public FavmovieSelection releaseDateStartsWith(String... value) {
        addStartsWith(FavmovieColumns.RELEASE_DATE, value);
        return this;
    }

    public FavmovieSelection releaseDateEndsWith(String... value) {
        addEndsWith(FavmovieColumns.RELEASE_DATE, value);
        return this;
    }

    public FavmovieSelection orderByReleaseDate(boolean desc) {
        orderBy(FavmovieColumns.RELEASE_DATE, desc);
        return this;
    }

    public FavmovieSelection orderByReleaseDate() {
        orderBy(FavmovieColumns.RELEASE_DATE, false);
        return this;
    }

    public FavmovieSelection overview(String... value) {
        addEquals(FavmovieColumns.OVERVIEW, value);
        return this;
    }

    public FavmovieSelection overviewNot(String... value) {
        addNotEquals(FavmovieColumns.OVERVIEW, value);
        return this;
    }

    public FavmovieSelection overviewLike(String... value) {
        addLike(FavmovieColumns.OVERVIEW, value);
        return this;
    }

    public FavmovieSelection overviewContains(String... value) {
        addContains(FavmovieColumns.OVERVIEW, value);
        return this;
    }

    public FavmovieSelection overviewStartsWith(String... value) {
        addStartsWith(FavmovieColumns.OVERVIEW, value);
        return this;
    }

    public FavmovieSelection overviewEndsWith(String... value) {
        addEndsWith(FavmovieColumns.OVERVIEW, value);
        return this;
    }

    public FavmovieSelection orderByOverview(boolean desc) {
        orderBy(FavmovieColumns.OVERVIEW, desc);
        return this;
    }

    public FavmovieSelection orderByOverview() {
        orderBy(FavmovieColumns.OVERVIEW, false);
        return this;
    }

    public FavmovieSelection backdropPath(String... value) {
        addEquals(FavmovieColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavmovieSelection backdropPathNot(String... value) {
        addNotEquals(FavmovieColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavmovieSelection backdropPathLike(String... value) {
        addLike(FavmovieColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavmovieSelection backdropPathContains(String... value) {
        addContains(FavmovieColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavmovieSelection backdropPathStartsWith(String... value) {
        addStartsWith(FavmovieColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavmovieSelection backdropPathEndsWith(String... value) {
        addEndsWith(FavmovieColumns.BACKDROP_PATH, value);
        return this;
    }

    public FavmovieSelection orderByBackdropPath(boolean desc) {
        orderBy(FavmovieColumns.BACKDROP_PATH, desc);
        return this;
    }

    public FavmovieSelection orderByBackdropPath() {
        orderBy(FavmovieColumns.BACKDROP_PATH, false);
        return this;
    }

    public FavmovieSelection posterPath(String... value) {
        addEquals(FavmovieColumns.POSTER_PATH, value);
        return this;
    }

    public FavmovieSelection posterPathNot(String... value) {
        addNotEquals(FavmovieColumns.POSTER_PATH, value);
        return this;
    }

    public FavmovieSelection posterPathLike(String... value) {
        addLike(FavmovieColumns.POSTER_PATH, value);
        return this;
    }

    public FavmovieSelection posterPathContains(String... value) {
        addContains(FavmovieColumns.POSTER_PATH, value);
        return this;
    }

    public FavmovieSelection posterPathStartsWith(String... value) {
        addStartsWith(FavmovieColumns.POSTER_PATH, value);
        return this;
    }

    public FavmovieSelection posterPathEndsWith(String... value) {
        addEndsWith(FavmovieColumns.POSTER_PATH, value);
        return this;
    }

    public FavmovieSelection orderByPosterPath(boolean desc) {
        orderBy(FavmovieColumns.POSTER_PATH, desc);
        return this;
    }

    public FavmovieSelection orderByPosterPath() {
        orderBy(FavmovieColumns.POSTER_PATH, false);
        return this;
    }

    public FavmovieSelection popularity(String... value) {
        addEquals(FavmovieColumns.POPULARITY, value);
        return this;
    }

    public FavmovieSelection popularityNot(String... value) {
        addNotEquals(FavmovieColumns.POPULARITY, value);
        return this;
    }

    public FavmovieSelection popularityLike(String... value) {
        addLike(FavmovieColumns.POPULARITY, value);
        return this;
    }

    public FavmovieSelection popularityContains(String... value) {
        addContains(FavmovieColumns.POPULARITY, value);
        return this;
    }

    public FavmovieSelection popularityStartsWith(String... value) {
        addStartsWith(FavmovieColumns.POPULARITY, value);
        return this;
    }

    public FavmovieSelection popularityEndsWith(String... value) {
        addEndsWith(FavmovieColumns.POPULARITY, value);
        return this;
    }

    public FavmovieSelection orderByPopularity(boolean desc) {
        orderBy(FavmovieColumns.POPULARITY, desc);
        return this;
    }

    public FavmovieSelection orderByPopularity() {
        orderBy(FavmovieColumns.POPULARITY, false);
        return this;
    }

    public FavmovieSelection voteCount(String... value) {
        addEquals(FavmovieColumns.VOTE_COUNT, value);
        return this;
    }

    public FavmovieSelection voteCountNot(String... value) {
        addNotEquals(FavmovieColumns.VOTE_COUNT, value);
        return this;
    }

    public FavmovieSelection voteCountLike(String... value) {
        addLike(FavmovieColumns.VOTE_COUNT, value);
        return this;
    }

    public FavmovieSelection voteCountContains(String... value) {
        addContains(FavmovieColumns.VOTE_COUNT, value);
        return this;
    }

    public FavmovieSelection voteCountStartsWith(String... value) {
        addStartsWith(FavmovieColumns.VOTE_COUNT, value);
        return this;
    }

    public FavmovieSelection voteCountEndsWith(String... value) {
        addEndsWith(FavmovieColumns.VOTE_COUNT, value);
        return this;
    }

    public FavmovieSelection orderByVoteCount(boolean desc) {
        orderBy(FavmovieColumns.VOTE_COUNT, desc);
        return this;
    }

    public FavmovieSelection orderByVoteCount() {
        orderBy(FavmovieColumns.VOTE_COUNT, false);
        return this;
    }

    public FavmovieSelection voteAverage(String... value) {
        addEquals(FavmovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavmovieSelection voteAverageNot(String... value) {
        addNotEquals(FavmovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavmovieSelection voteAverageLike(String... value) {
        addLike(FavmovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavmovieSelection voteAverageContains(String... value) {
        addContains(FavmovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavmovieSelection voteAverageStartsWith(String... value) {
        addStartsWith(FavmovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavmovieSelection voteAverageEndsWith(String... value) {
        addEndsWith(FavmovieColumns.VOTE_AVERAGE, value);
        return this;
    }

    public FavmovieSelection orderByVoteAverage(boolean desc) {
        orderBy(FavmovieColumns.VOTE_AVERAGE, desc);
        return this;
    }

    public FavmovieSelection orderByVoteAverage() {
        orderBy(FavmovieColumns.VOTE_AVERAGE, false);
        return this;
    }

    public FavmovieSelection video(String... value) {
        addEquals(FavmovieColumns.VIDEO, value);
        return this;
    }

    public FavmovieSelection videoNot(String... value) {
        addNotEquals(FavmovieColumns.VIDEO, value);
        return this;
    }

    public FavmovieSelection videoLike(String... value) {
        addLike(FavmovieColumns.VIDEO, value);
        return this;
    }

    public FavmovieSelection videoContains(String... value) {
        addContains(FavmovieColumns.VIDEO, value);
        return this;
    }

    public FavmovieSelection videoStartsWith(String... value) {
        addStartsWith(FavmovieColumns.VIDEO, value);
        return this;
    }

    public FavmovieSelection videoEndsWith(String... value) {
        addEndsWith(FavmovieColumns.VIDEO, value);
        return this;
    }

    public FavmovieSelection orderByVideo(boolean desc) {
        orderBy(FavmovieColumns.VIDEO, desc);
        return this;
    }

    public FavmovieSelection orderByVideo() {
        orderBy(FavmovieColumns.VIDEO, false);
        return this;
    }

    public FavmovieSelection adult(String... value) {
        addEquals(FavmovieColumns.ADULT, value);
        return this;
    }

    public FavmovieSelection adultNot(String... value) {
        addNotEquals(FavmovieColumns.ADULT, value);
        return this;
    }

    public FavmovieSelection adultLike(String... value) {
        addLike(FavmovieColumns.ADULT, value);
        return this;
    }

    public FavmovieSelection adultContains(String... value) {
        addContains(FavmovieColumns.ADULT, value);
        return this;
    }

    public FavmovieSelection adultStartsWith(String... value) {
        addStartsWith(FavmovieColumns.ADULT, value);
        return this;
    }

    public FavmovieSelection adultEndsWith(String... value) {
        addEndsWith(FavmovieColumns.ADULT, value);
        return this;
    }

    public FavmovieSelection orderByAdult(boolean desc) {
        orderBy(FavmovieColumns.ADULT, desc);
        return this;
    }

    public FavmovieSelection orderByAdult() {
        orderBy(FavmovieColumns.ADULT, false);
        return this;
    }
}
