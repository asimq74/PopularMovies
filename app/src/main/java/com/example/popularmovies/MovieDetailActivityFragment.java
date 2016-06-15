package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.popularmovies.businessobjects.MovieConstants;
import com.example.popularmovies.businessobjects.MovieInfo;
import com.example.popularmovies.task.ListMovieImagesAsyncTask;

/**
 * A placeholder fragment containing a simple view.
 */
public class MovieDetailActivityFragment extends Fragment implements MovieConstants {

    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();

    private MovieInfo movieInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_movie_detail, container, false);

        // The detail Activity called via intent.  Inspect the intent for forecast data.
        Intent intent = getActivity().getIntent();
        if (intent != null && intent.hasExtra(MOVIE_INFO_PARCELABLE_KEY)) {
            movieInfo = (MovieInfo) intent.getParcelableExtra(MOVIE_INFO_PARCELABLE_KEY);
            Log.i(LOG_TAG, String.format("movieInfo=\n%s", movieInfo.toString()));
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        ListMovieImagesAsyncTask listMoviesTask = new ListMovieImagesAsyncTask();
        listMoviesTask.execute(movieInfo.getId());
    }
}
