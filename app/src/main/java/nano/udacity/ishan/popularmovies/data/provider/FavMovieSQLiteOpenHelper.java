package nano.udacity.ishan.popularmovies.data.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import nano.udacity.ishan.popularmovies.BuildConfig;
import nano.udacity.ishan.popularmovies.data.provider.favmovie.FavmovieColumns;

public class FavMovieSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = FavMovieSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "favpopmovies.db";
    private static final int DATABASE_VERSION = 1;
    private static FavMovieSQLiteOpenHelper sInstance;
    private final Context mContext;
    private final FavMovieSQLiteOpenHelperCallbacks mOpenHelperCallbacks;

    // @formatter:off
    public static final String SQL_CREATE_TABLE_FAVMOVIE = "CREATE TABLE IF NOT EXISTS "
            + FavmovieColumns.TABLE_NAME + " ( "
            + FavmovieColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + FavmovieColumns.MOVIE_ID + " TEXT NOT NULL, "
            + FavmovieColumns.TITLE + " TEXT NOT NULL, "
            + FavmovieColumns.ORIGINAL_TITLE + " TEXT, "
            + FavmovieColumns.ORIGINAL_LANGUAGE + " TEXT, "
            + FavmovieColumns.RELEASE_DATE + " TEXT NOT NULL, "
            + FavmovieColumns.OVERVIEW + " TEXT NOT NULL, "
            + FavmovieColumns.BACKDROP_PATH + " TEXT, "
            + FavmovieColumns.POSTER_PATH + " TEXT, "
            + FavmovieColumns.POPULARITY + " TEXT, "
            + FavmovieColumns.VOTE_COUNT + " TEXT, "
            + FavmovieColumns.VOTE_AVERAGE + " TEXT, "
            + FavmovieColumns.VIDEO + " TEXT, "
            + FavmovieColumns.ADULT + " TEXT "
            + ", CONSTRAINT unique_name UNIQUE (movie_id) ON CONFLICT REPLACE"
            + " );";

    // @formatter:on

    public static FavMovieSQLiteOpenHelper getInstance(Context context) {
        // Use the application context, which will ensure that you
        // don't accidentally leak an Activity's context.
        // See this article for more information: http://bit.ly/6LRzfx
        if (sInstance == null) {
            sInstance = newInstance(context.getApplicationContext());
        }
        return sInstance;
    }

    private static FavMovieSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */
    private static FavMovieSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new FavMovieSQLiteOpenHelper(context);
    }

    private FavMovieSQLiteOpenHelper(Context context) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
        mContext = context;
        mOpenHelperCallbacks = new FavMovieSQLiteOpenHelperCallbacks();
    }


    /*
     * Post Honeycomb.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static FavMovieSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new FavMovieSQLiteOpenHelper(context, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private FavMovieSQLiteOpenHelper(Context context, DatabaseErrorHandler errorHandler) {
        super(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, errorHandler);
        mContext = context;
        mOpenHelperCallbacks = new FavMovieSQLiteOpenHelperCallbacks();
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        mOpenHelperCallbacks.onPreCreate(mContext, db);
        db.execSQL(SQL_CREATE_TABLE_FAVMOVIE);
        mOpenHelperCallbacks.onPostCreate(mContext, db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            setForeignKeyConstraintsEnabled(db);
        }
        mOpenHelperCallbacks.onOpen(mContext, db);
    }

    private void setForeignKeyConstraintsEnabled(SQLiteDatabase db) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
            setForeignKeyConstraintsEnabledPreJellyBean(db);
        } else {
            setForeignKeyConstraintsEnabledPostJellyBean(db);
        }
    }

    private void setForeignKeyConstraintsEnabledPreJellyBean(SQLiteDatabase db) {
        db.execSQL("PRAGMA foreign_keys=ON;");
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setForeignKeyConstraintsEnabledPostJellyBean(SQLiteDatabase db) {
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        mOpenHelperCallbacks.onUpgrade(mContext, db, oldVersion, newVersion);
    }
}
