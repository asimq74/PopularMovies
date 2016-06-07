package com.example.popularmovies.businessobjects;

import java.util.List;

/**
 * Created by asimqureshi on 6/5/16.
 */
public class MovieInfo {
    private String posterPath;
    private String adult;
    private String overview;
    private String releaseDate;
    private List<Integer> genreIds;
    private int id;
    private String originalTitle;
    private String originalLanguage;
    private String title;
    private String backdropPath;
    private String popularity;
    private String voteCount;
    private String video;
    private String voteAverage;

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public String getAdult() {
        return adult;
    }

    public void setAdult(String adult) {
        this.adult = adult;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<Integer> getGenreIds() {
        return genreIds;
    }

    public void setGenreIds(List<Integer> genreIds) {
        this.genreIds = genreIds;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getOriginalLanguage() {
        return originalLanguage;
    }

    public void setOriginalLanguage(String originalLanguage) {
        this.originalLanguage = originalLanguage;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBackdropPath() {
        return backdropPath;
    }

    public void setBackdropPath(String backdropPath) {
        this.backdropPath = backdropPath;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getVoteCount() {
        return voteCount;
    }

    public void setVoteCount(String voteCount) {
        this.voteCount = voteCount;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getVoteAverage() {
        return voteAverage;
    }

    public void setVoteAverage(String voteAverage) {
        this.voteAverage = voteAverage;
    }

    @Override
    public String toString() {
        return "\nMovieInfo{" +"\n" +
                "posterPath='" + posterPath + '\'' +",\n" +
                "adult='" + adult + '\'' +",\n" +
                "overview='" + overview + '\'' +",\n" +
                "releaseDate='" + releaseDate + '\'' +",\n" +
                "genreIds=" + genreIds +",\n" +
                "id=" + id +",\n" +
                "originalTitle='" + originalTitle + '\'' +",\n" +
                "originalLanguage='" + originalLanguage + '\'' +",\n" +
                "title='" + title + '\'' +",\n" +
                "backdropPath='" + backdropPath + '\'' +",\n" +
                "popularity='" + popularity + '\'' +",\n" +
                "voteCount='" + voteCount + '\'' +",\n" +
                "video='" + video + '\'' +",\n" +
                "voteAverage='" + voteAverage + '\'' +
                '}';
    }
}
