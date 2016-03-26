package nano.udacity.ishan.popularmovies.common;

import nano.udacity.ishan.popularmovies.BuildConfig;

/**
 * Created by Ishan on 31-01-2016.
 */
public class Const {
    public static final String TMD_API_KEY = BuildConfig.TMD_API_KEY;

    public static final String TMD_BASE_URL = "http://api.themoviedb.org/3/";
    public static final String URL_MOVIE_DISCOVER = "http://api.themoviedb.org/3/discover/movie";
    public static final String URL_MOVIE_INFO = "http://api.themoviedb.org/3/movie/";
    public static final String URL_MOVIE_POSTER = "http://image.tmdb.org/t/p";
    public static final String URL_PARAM_SORT_ORDER = "sort_by";
    public static final String URL_PARAM_API_KEY = "api_key";
    public static final String YOUTUBE_BASE_VIEW_URL = "http://www.youtube.com/watch/";


    public static final String SETTING_POSTER_SIZE_THUMBNAIL = "w185";
    public static final String SETTING_POSTER_SIZE_LARGE = "w500";
    public static final String SETTING_POSTER_SIZE_XLARGE = "w780";
    public static final String SETTING_POSTER_SIZE_ORIGINAL = "original";
    public static final String SETTING_SORT_ORDER_POPULARITY = "popularity.desc";
    public static final String SETTING_SORT_ORDER_RATING = "vote_average.desc";
    public static final String SETTING_SORT_ORDER_FAVORITES = "favorites";


    public static final String KEY_FRAGMENT_DISPLAY_MODE = "fragement_display_mode";
    public static final int FRAGMENT_DISPLAY_MODE_SINGLE = 0;
    public static final int FRAGMENT_DISPLAY_MODE_DUAL = 1;


}
