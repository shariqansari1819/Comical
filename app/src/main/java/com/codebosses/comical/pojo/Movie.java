package com.codebosses.comical.pojo;

public class Movie {

    private String movieId;
    private String comicId;
    private String movieTitle;
    private String moviePosterPath;
    private String movieDescription;

    public Movie(String movieId, String comicId, String movieTitle, String moviePosterPath, String movieDescription) {
        this.movieId = movieId;
        this.comicId = comicId;
        this.movieTitle = movieTitle;
        this.moviePosterPath = moviePosterPath;
        this.movieDescription = movieDescription;
    }

    public Movie() {
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public String getComicId() {
        return comicId;
    }

    public void setComicId(String comicId) {
        this.comicId = comicId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMoviePosterPath() {
        return moviePosterPath;
    }

    public void setMoviePosterPath(String moviePosterPath) {
        this.moviePosterPath = moviePosterPath;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }
}
