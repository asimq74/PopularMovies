package com.example.popularmovies.task;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.popularmovies.BuildConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by asimqureshi on 6/14/16.
 */
public class ListMovieImagesAsyncTask extends AsyncTask<Integer, Void, List<String>> {

    final String TAG = ListMovieImagesAsyncTask.class.getSimpleName();
//    https://api.themoviedb.org/3/movie/550/images?api_key=1ecc7763c72271156bf6004d6edc2e1d&language=en&include_image_language=en,null


    @Override
    protected List<String> doInBackground(Integer... params) {
        Integer id = params[0];
        // These two need to be declared outside the try/catch
// so that they can be closed in the finally block.
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

// Will contain the raw JSON response as a string.
        String moviesJsonString = null;

        try {
            // Construct the URL for the OpenWeatherMap query
            Uri.Builder builder = new Uri.Builder();
            builder.scheme("http")
                    .authority("api.themoviedb.org")
                    .appendPath("3")
                    .appendPath("movie")
                    .appendPath(id.toString())
                    .appendPath("images")
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
            Log.i(TAG, moviesJsonString);
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
        return new ArrayList<>();

    }
}
