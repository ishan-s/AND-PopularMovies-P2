package nano.udacity.ishan.popularmovies;

import android.content.ClipData;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnItemClick;
import nano.udacity.ishan.popularmovies.adapters.ReviewAdapter;
import nano.udacity.ishan.popularmovies.common.Const;
import nano.udacity.ishan.popularmovies.common.Utils;
import nano.udacity.ishan.popularmovies.data.Review;
import nano.udacity.ishan.popularmovies.data.ReviewList;
import nano.udacity.ishan.popularmovies.network.TMDBService;
import retrofit.Call;
import retrofit.Callback;
import retrofit.GsonConverterFactory;
import retrofit.Response;
import retrofit.Retrofit;

public class ViewReviewsActivity extends AppCompatActivity {
    List<Review> reviewList = new ArrayList<Review>();
    boolean isReviewFetchNeeded = false;

    @Bind(R.id.movie_review_listview)
    ListView reviewListView;

    ReviewAdapter reviewAdapter;
    TMDBService tmdbService;
    Retrofit retrofit;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("SAVED_REVIEWS", (ArrayList<Review>) reviewList);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_reviews);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        ButterKnife.bind(this);
        Intent intent = getIntent();
        retrofit = new Retrofit.Builder()
                .baseUrl(Const.TMD_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        tmdbService = retrofit.create(TMDBService.class);


        reviewAdapter = new ReviewAdapter(this, reviewList);
        reviewListView.setAdapter(reviewAdapter);
        reviewListView.setDivider(null);
        reviewListView.setDividerHeight(0);

        this.setTitle(getString(R.string.title_reviews) + " - " + intent.getStringExtra("MOVIE_TITLE"));

        ArrayList<Review> fetchedReviews = intent.getParcelableArrayListExtra("REVIEW_LIST");
        if(fetchedReviews==null || fetchedReviews.size()==0)
            isReviewFetchNeeded = true;
        else{
            reviewList.clear();
            reviewList.addAll(fetchedReviews);
            reviewAdapter.notifyDataSetChanged();
        }

        if(savedInstanceState==null || !savedInstanceState.containsKey("SAVED_REVIEWS")){
            if(isReviewFetchNeeded) {
                Toast.makeText(this, "Fetching reviews from the web...", Toast.LENGTH_LONG).show();
                fetchMovieReviews(intent.getStringExtra("MOVIE_ID"));
            }
        }
        else{
            ArrayList<Review> savedReviewList = savedInstanceState.getParcelableArrayList("SAVED_REVIEWS");
            reviewList.clear();
            reviewList.addAll(savedReviewList);
            reviewAdapter.notifyDataSetChanged();
        }

    }

    private void fetchMovieReviews(String movie_id) {
        Call<ReviewList> reviewListCall = tmdbService.getReviews(movie_id, Const.TMD_API_KEY);
        reviewListCall.enqueue(new Callback<ReviewList>() {
            @Override
            public void onResponse(Response<ReviewList> response) {
                ReviewList reviewListObj = response.body();
                reviewList.clear();
                reviewList.addAll(reviewListObj.getReviews());
                reviewAdapter.notifyDataSetChanged();

                if(reviewList.size()==0){
                    Utils.showError(reviewListView, R.string.msg_no_reviews);
                }
            }

            @Override
            public void onFailure(Throwable t) {
                Utils.showError(reviewListView, R.string.err_network);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        // Just Experimenting with transition :)
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnItemClick(R.id.movie_review_listview)
    public void openReviewWeb(int position){
        String url = reviewList.get(position).getUrl();
        Intent openWebIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url.trim()));
        startActivity(openWebIntent);
    }

}
