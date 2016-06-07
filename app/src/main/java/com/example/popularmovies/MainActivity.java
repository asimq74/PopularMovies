package com.example.popularmovies;

import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

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

public class MainActivity extends AppCompatActivity {

    public class FetchMoviesTask extends AsyncTask<String, Void, String> {

        final String TAG = FetchMoviesTask.class.getSimpleName();

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            try {
                Log.i(TAG, String.format("json: %s", formatJson(json)));
                ((TextView) findViewById(R.id.jsonResult)).setText(json);
            } catch (JSONException e) {
                Log.e(TAG, "Error ", e);

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
        protected String doInBackground(String... params) {
//            String zipCode = params[0];
//            String units = params[1];
//            String[] weatherData = new String[7];
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
                        .appendPath("discover")
                        .appendPath("movie")
                        .appendQueryParameter("sort_by", "popularity.desc")
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
                formatJson(moviesJsonString);
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
            return moviesJsonString;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new FetchMoviesTask().execute();
    }

    @Override
    public void onStart() {
        super.onStart();
        new FetchMoviesTask().execute();
        Picasso.with(this).load("http://i.imgur.com/DvpvklR.png").into((ImageView)findViewById(R.id.posterImageView));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
