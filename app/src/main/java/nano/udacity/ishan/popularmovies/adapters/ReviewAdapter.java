package nano.udacity.ishan.popularmovies.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import nano.udacity.ishan.popularmovies.R;
import nano.udacity.ishan.popularmovies.data.Review;

/**
 * Created by Ishan on 17-02-2016.
 */
public class ReviewAdapter extends ArrayAdapter<Review> {
    private static final String LOG_TAG = "ReviewAdapter";
    ArrayList<Review> mReviews = new ArrayList<Review>();
    boolean isCompactLayout = false;
    @Bind(R.id.movie_review_author) TextView authorTextView;
    @Bind(R.id.movie_review_content) TextView contentTextView;

    public ReviewAdapter(Context context, List<Review> reviews){
        super(context, 0, reviews);
        mReviews = (ArrayList<Review>) reviews;
    }

    public ReviewAdapter(Context context, List<Review> reviews, boolean isCompactLayout){
        super(context, 0, reviews);
        mReviews = (ArrayList<Review>) reviews;
        this.isCompactLayout = isCompactLayout;
    }

    @Override
    public int getCount() {
        return mReviews==null ? 0 : mReviews.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView==null){
            convertView = inflater.inflate(isCompactLayout ? R.layout.layout_review_item_compact : R.layout.layout_review_card, null);
        }

        //ButterKnife.bind(getContext(), convertView);
        authorTextView = (TextView) convertView.findViewById(R.id.movie_review_author);
        contentTextView = (TextView) convertView.findViewById(R.id.movie_review_content);
        
        Review review = mReviews.get(position);
        authorTextView.setText(review.getAuthor());
        contentTextView.setText(review.getContent());

        return convertView;
    }

    @Override
    public Review getItem(int position) {
        return mReviews==null ? null : mReviews.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
}
