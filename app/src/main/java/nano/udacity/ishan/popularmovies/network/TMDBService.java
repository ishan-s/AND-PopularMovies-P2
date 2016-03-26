package nano.udacity.ishan.popularmovies.network;

import nano.udacity.ishan.popularmovies.data.DiscoverMovies;
import nano.udacity.ishan.popularmovies.data.MovieDetail;
import nano.udacity.ishan.popularmovies.data.MovieVideoList;
import nano.udacity.ishan.popularmovies.data.ReviewList;
import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Ishan on 17-02-2016.
 */
public interface TMDBService {

    @GET("discover/movie")
    Call<DiscoverMovies> discoverMovies(
            @Query("api_key") String spiKey,
            @Query("sort_by") String sortOrder
    );

    @GET("movie/{id}")
    Call<MovieDetail> getMovieDetails(
            @Path("id") String movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/{id}/reviews")
    Call<ReviewList> getReviews(
            @Path("id") String movieId,
            @Query("api_key") String apiKey
    );

    @GET("movie/{id}/videos")
    Call<MovieVideoList> getMovieVideos(
            @Path("id") String movieId,
            @Query("api_key") String apiKey
    );
}
