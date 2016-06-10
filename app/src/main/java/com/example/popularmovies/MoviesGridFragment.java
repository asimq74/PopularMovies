package com.example.popularmovies;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.popularmovies.businessobjects.MovieConstants;
import com.example.popularmovies.businessobjects.MovieInfo;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Large screen devices (such as tablets) are supported by replacing the ListView
 * with a GridView.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnFragmentInteractionListener}
 * interface.
 */
public class MoviesGridFragment extends Fragment implements AbsListView.OnItemClickListener, MovieConstants {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    /**
     * The fragment's ListView/GridView.
     */
    private AbsListView mListView;

    /**
     * The Adapter which will be used to populate the ListView/GridView with
     * Views.
     */
    private ListAdapter mAdapter;
    private ArrayAdapter<MovieInfo> moviePostersArrayAdapter;
    private MovieInfoAdapter movieInfoAdapter;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MoviesGridFragment() {
    }

    // TODO: Rename and change types of parameters
    public static MoviesGridFragment newInstance(String param1, String param2) {
        MoviesGridFragment fragment = new MoviesGridFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movieinfo, container, false);

        // Construct the data source
        List<MovieInfo> movieInfoList = new ArrayList<>();
// Create the movieInfoAdapter to convert the array to views
        movieInfoAdapter = new MovieInfoAdapter(getActivity(), movieInfoList);
// Attach the movieInfoAdapter to a ListView
        GridView gridView = (GridView) view.findViewById(android.R.id.list);
        gridView.setAdapter(movieInfoAdapter);
        gridView.setClickable(true);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MovieInfo movieInfo = movieInfoAdapter.getItem(position);
                Intent detailIntent = new Intent(getActivity(), MovieDetailActivity.class);
                detailIntent.putExtra(EXTRAS_FORECAST, movieInfo);
                startActivity(detailIntent);
            }
        });
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        updateMovies();
    }

    protected void updateMovies() {
        FetchMoviesTask updateMoviesTask = new FetchMoviesTask();
        updateMoviesTask.execute();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        if (null != mListener) {
//            // Notify the active callbacks interface (the activity, if the
//            // fragment is attached to one) that an item has been selected.
//            mListener.onFragmentInteraction(DummyContent.ITEMS.get(position).id);
//        }
    }

    /**
     * The default content for this Fragment has a TextView that is shown when
     * the list is empty. If you would like to change the text, call this method
     * to supply the text it should use.
     */
    public void setEmptyText(CharSequence emptyText) {
        View emptyView = mListView.getEmptyView();

        if (emptyView instanceof TextView) {
            ((TextView) emptyView).setText(emptyText);
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(String id);
    }

    public class MovieInfoAdapter extends ArrayAdapter<MovieInfo> {

        public MovieInfoAdapter(Context context, List<MovieInfo> movieInfos) {
            super(context, 0, movieInfos);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            MovieInfo movieInfo = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.movie_poster_item, parent, false);
            }
            // Lookup view for data population
            ImageView moviePosterImageView = (ImageView) convertView.findViewById(R.id.movie_poster);
            TextView movieTitleView = (TextView) convertView.findViewById(R.id.movie_title);
            // Populate the data into the template view using the data object
            http://image.tmdb.org/t/p/w500/8uO0gUM8aNqYLs1OsTBQiXu0fEv.jpg?api_key=1ecc7763c72271156bf6004d6edc2e1d&language=en&include_image_language=en,null


            Picasso.with(getActivity()).load("http://image.tmdb.org/t/p/w500/"
                    +movieInfo.getPosterPath()
                    +"?api_key="
                    +BuildConfig.THE_MOVIE_DB_API_KEY
                    +"&language=en&include_image_language=en,null").into(moviePosterImageView);
            movieTitleView.setText(movieInfo.getTitle());
            // Return the completed view to render on screen
            return convertView;
        }
    }

    public class FetchMoviesTask extends AsyncTask<String, Void, List<MovieInfo>> {

        final String TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected void onPostExecute(List<MovieInfo> movieInfos) {
            super.onPostExecute(movieInfos);
            if (movieInfos != null) {
                movieInfoAdapter.clear();
                for (MovieInfo movieInfo : movieInfos) {
                    movieInfoAdapter.add(movieInfo);
                }
            }
        }


        protected List<MovieInfo> formatJson(String json) throws JSONException {
            // These are the names of the JSON objects that need to be extracted.
            final String RESULTS = "results";
            final String POSTER_PATH = "poster_path";
            final String ADULT = "adult";
            final String OVERVIEW = "overview";
            final String RELEASE_DATE = "release_date";
            final String GENRE_IDS = "genre_ids";
            final String ID = "id";
            final String ORIGINAL_TITLE = "original_title";
            final String ORIGINAL_LANGUAGE = "original_language";
            final String TITLE = "title";
            final String BACKDROP_PATH = "backdrop_path";
            final String POPULARITY = "popularity";
            final String VOTE_COUNT = "vote_count";
            final String VIDEO = "video";
            final String VOTE_AVERAGE = "vote_average";

            JSONObject forecastJson = new JSONObject(json);
            JSONArray moviesArray = forecastJson.getJSONArray(RESULTS);
            List<MovieInfo> movies = new ArrayList<>();
            for (int i = 0; i < moviesArray.length(); i++) {
                // Get the JSON object representing the day
                JSONObject movieInfoJsonObject = moviesArray.getJSONObject(i);
                MovieInfo movieInfo = new MovieInfo();
                movieInfo.setPosterPath(movieInfoJsonObject.getString(POSTER_PATH));
                movieInfo.setAdult(movieInfoJsonObject.getString(ADULT));
                movieInfo.setOverview(movieInfoJsonObject.getString(OVERVIEW));
                movieInfo.setReleaseDate(movieInfoJsonObject.getString(RELEASE_DATE));
                movieInfo.setId(movieInfoJsonObject.getInt(ID));
                movieInfo.setOriginalTitle(movieInfoJsonObject.getString(ORIGINAL_TITLE));
                movieInfo.setOriginalLanguage(movieInfoJsonObject.getString(ORIGINAL_LANGUAGE));
                movieInfo.setTitle(movieInfoJsonObject.getString(TITLE));
                movieInfo.setBackdropPath(movieInfoJsonObject.getString(BACKDROP_PATH));
                movieInfo.setPopularity(movieInfoJsonObject.getString(POPULARITY));
                movieInfo.setVoteCount(movieInfoJsonObject.getString(VOTE_COUNT));
                movieInfo.setVideo(movieInfoJsonObject.getString(VIDEO));
                movieInfo.setVoteAverage(movieInfoJsonObject.getString(VOTE_AVERAGE));
                movies.add(movieInfo);
            }
            return movies;
        }

        @Override
        protected List<MovieInfo> doInBackground(String... params) {
//            String zipCode = params[0];
//            String units = params[1];
            List<MovieInfo> movieInfos = new ArrayList<>();
            // These two need to be declared outside the try/catch
// so that they can be closed in the finally block.
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;

// Will contain the raw JSON response as a string.
            String moviesJsonString = null;

            try {
                // Construct the URL for the OpenWeatherMap query
                // Possible parameters are available at OWM's forecast API page, at
                // http://openweathermap.org/API#forecast
//                URL url = new URL("http://api.openweathermap.org/data/2.5/forecast/daily?q=94043&mode=json&units=metric&cnt=7&APPID="
//                        + BuildConfig.THE_MOVIE_DB_API_KEY);
//                http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=<BuildConfig.THE_MOVIE_DB_API_KEY>
                Uri.Builder builder = new Uri.Builder();
                builder.scheme("http")
                        .authority("api.themoviedb.org")
                        .appendPath("3")
                        .appendPath("movie")
//                        .appendPath("popular")
                        .appendPath("top_rated")
//                        .appendQueryParameter("primary_release_date.gte", "2016-04-15")
//                        .appendQueryParameter("primary_release_date.lte", "2016-06-05")
                        .appendQueryParameter("sort_by", "popularity.desc")
//                        .appendQueryParameter("sort_by", "vote_average")
                        .appendQueryParameter("api_key", BuildConfig.THE_MOVIE_DB_API_KEY);
                URL url = new URL(builder.build().toString());
                // Create the request to OpenWeatherMap, and open the connection
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.connect();

                // Read the input stream into a String
                InputStream inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null) {
                    // Nothing to do.
                    moviesJsonString = null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));

                String line;
                while ((line = reader.readLine()) != null) {
                    // Since it's JSON, adding a newline isn't necessary (it won't affect parsing)
                    // But it does make debugging a *lot* easier if you print out the completed
                    // buffer for debugging.
                    buffer.append(line + "\n");
                }

                if (buffer.length() == 0) {
                    Log.d(TAG, "Stream was empty");
                    // Stream was empty.  No point in parsing.
                    moviesJsonString = null;
                }
                moviesJsonString = buffer.toString();
                movieInfos = formatJson(moviesJsonString);
            } catch (JSONException e) {
                Log.e(TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                moviesJsonString = null;

            } catch (IOException e) {
                Log.e(TAG, "Error ", e);
                // If the code didn't successfully get the weather data, there's no point in attempting
                // to parse it.
                moviesJsonString = null;
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
                if (reader != null) {
                    try {
                        reader.close();
                    } catch (final IOException e) {
                        Log.e(TAG, "Error closing stream", e);
                    }
                }
            }
            return movieInfos;
        }
    }

}
