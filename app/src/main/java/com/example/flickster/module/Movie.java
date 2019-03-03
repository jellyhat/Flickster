package com.example.flickster.module;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;
@Parcel
public class Movie {
    String posterPath;
    String title;
    String overview;
    String backdropPath;
    String releaseDate;
    int movieID;
    int[] genreId;
    double rating;

    //for parcelable
    public Movie(){}

    public Movie(JSONObject jsonObject) throws JSONException {
        posterPath = jsonObject.getString("poster_path");
        title = jsonObject.getString("title");
        overview = jsonObject.getString("overview");
        backdropPath = jsonObject.getString("backdrop_path");
        rating = jsonObject.getDouble("vote_average");
        releaseDate = jsonObject.getString("release_date");
        movieID = jsonObject.getInt("id");
        JSONArray array = jsonObject.optJSONArray("genre_ids");
        genreId = new int[array.length()];
        for(int i = 0; i < array.length(); i++){
            genreId[i] = (int) array.opt(i);
        }
    }

    public static List<Movie> fromJSONArray(JSONArray movieJSONArray) throws JSONException{
        List<Movie> movies = new ArrayList<>();
        for(int i =0; i< movieJSONArray.length(); i++){
            movies.add(new Movie(movieJSONArray.getJSONObject(i)));
        }
        return movies;
    }

    public String getPosterPath() {
        //return posterPath;
        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
    }

    public String getTitle() {
        return title;
    }

    public String getOverview() {
        return overview;
    }

    public double getRating(){
        return rating;
    }

    public String getReleaseDate(){
        return releaseDate;
    }

    public int[] getGenreId(){
        return genreId;
    }

    public int getMovieID(){
        return movieID;
    }

    public String getBackdropPath(){return String.format("https://image.tmdb.org/t/p/w780/%s", backdropPath);}
}
