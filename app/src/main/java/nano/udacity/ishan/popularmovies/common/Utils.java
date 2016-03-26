package nano.udacity.ishan.popularmovies.common;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import nano.udacity.ishan.popularmovies.R;
import nano.udacity.ishan.popularmovies.data.provider.favmovie.FavmovieColumns;

/**
 * Created by Ishan on 27-02-2016.
 */
public class Utils {

    public static boolean isDualPaneLayout(Activity activity) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        return (preferences.getInt(Const.KEY_FRAGMENT_DISPLAY_MODE, Const.FRAGMENT_DISPLAY_MODE_SINGLE) == Const.FRAGMENT_DISPLAY_MODE_DUAL);
    }

    public static void showError(View v, int resourceId){
        Snackbar errSnackbar = Snackbar.make(v, resourceId, Snackbar.LENGTH_LONG);
        View errSnackBarView = errSnackbar.getView();
        errSnackBarView.setBackgroundColor(v.getResources().getColor(R.color.colorError));
        errSnackbar.show();
    }

    public static void showMessage(View v, int resourceId){
        Snackbar msgSnackbar = Snackbar.make(v, resourceId, Snackbar.LENGTH_LONG);
        View msgSnackBarView = msgSnackbar.getView();
        msgSnackBarView.setBackgroundColor(v.getResources().getColor(R.color.colorAccent));
        msgSnackbar.show();
    }

    public static void showAlwaysOnSnackbar(View v, int resourceId) {
        Snackbar alwaysSnackbar = Snackbar.make(v, resourceId, Snackbar.LENGTH_INDEFINITE);
        View alwaysSnackBarView = alwaysSnackbar.getView();
        alwaysSnackBarView.setBackgroundColor(v.getResources().getColor(R.color.colorAccent2));
        alwaysSnackbar.setActionTextColor(Color.BLACK);
        alwaysSnackbar.show();
    }

    /* Borrowed from http://blog.lovelyhq.com/setting-listview-height-depending-on-the-items/ */

    /**
     * Sets ListView height dynamically based on the height of the items.
     *
     * @param listView to be resized
     * @return true if the listView is successfully resized, false otherwise
     */
    public static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                item.measure(0, 0);
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() *
                    (numberOfItems - 1);

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight;
            listView.setLayoutParams(params);
            listView.requestLayout();

            return true;

        } else {
            return false;
        }

    }

    public static boolean isMovieFavorited(Context context, String movieId) {
        String[] projection = {
                FavmovieColumns._ID,
                FavmovieColumns.MOVIE_ID
        };

        String selectionClause = FavmovieColumns.MOVIE_ID + " = ?";
        String[] selectionArgs = {""};
        selectionArgs[0] = movieId;

        Cursor cursor = context.getContentResolver().query(
                FavmovieColumns.CONTENT_URI,
                projection,
                selectionClause,
                selectionArgs,
                null);

        if (cursor != null) {
            if (cursor.getCount() > 0)
                return true;
        }
        cursor.close();
        return false;
    }
}
