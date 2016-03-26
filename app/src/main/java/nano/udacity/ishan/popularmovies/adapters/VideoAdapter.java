package nano.udacity.ishan.popularmovies.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import nano.udacity.ishan.popularmovies.R;
import nano.udacity.ishan.popularmovies.data.Video;

/**
 * Created by ishrivas on 17-Feb-16.
 */
public class VideoAdapter extends ArrayAdapter<Video> {

    List<Video> mVideos = new ArrayList<Video>();

    public VideoAdapter(Context context, List<Video> videos) {
        super(context, 0, videos);
        mVideos = (ArrayList<Video>)videos;
    }

    @Override
    public Video getItem(int position) {
        return mVideos == null ? null : mVideos.get(position);
    }

    @Override
    public int getCount() {
        return mVideos == null ? 0 : mVideos.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = inflater.inflate(R.layout.layout_trailer_item, null);
        }

        TextView trailerNameTextView = (TextView) convertView.findViewById(R.id.movie_trailer_name_textview);
        trailerNameTextView.setText(getItem(position).getName());

        return convertView;
    }
}
