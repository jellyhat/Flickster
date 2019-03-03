package com.example.flickster;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.flickster.module.Movie;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class DetailActivity extends YouTubeBaseActivity {

    public static final String YOUTUBE_API_KEY = "AIzaSyC6HoSSMJAcyf8aCTpcYkOKljOBhICCUs0";
    private static final String TRAILERS_API = "https://api.themoviedb.org/3/movie/%d/videos?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    private static final String MOVIE_GENRES = "http://api.themoviedb.org/3/genre/movie/list?api_key=a07e22bc18f5cb106bfe4cc1f83ad8ed";
    TextView tvTitle;
    TextView tvOverview;
    RatingBar ratingBar;
    TextView tvRelease;
    TextView tvGenres;
    int[] genres;
    Movie movie;
    YouTubePlayerView youTubePlayerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        tvTitle = findViewById(R.id.tvTitle);
        tvOverview = findViewById(R.id.tvOverview);
        ratingBar = findViewById(R.id.ratingBar);
        tvRelease = findViewById(R.id.release);
        tvGenres = findViewById(R.id.Genres);
        //String title = getIntent().getStringExtra("title");
        youTubePlayerView = findViewById(R.id.player);
        movie = (Movie) Parcels.unwrap(getIntent().getParcelableExtra("movie"));
        tvTitle.setText(movie.getTitle());
        tvOverview.setText(movie.getOverview());
        ratingBar.setRating((float) movie.getRating());
        tvRelease.append(movie.getReleaseDate());
        genres = movie.getGenreId();
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(MOVIE_GENRES, new JsonHttpResponseHandler() {
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    JSONArray movieJsonArray = response.getJSONArray("genres");
                    JSONObject temp;
                    int id;
                    int genNum = 0;
                    int genMax = genres.length;
                    for (int i = 0; i < movieJsonArray.length(); i++) {
                        temp = movieJsonArray.getJSONObject(i);
                        id = temp.optInt("id");
                        for (int j = 0; j < genres.length; j++) {
                            if (id == genres[j]) {
                                tvGenres.append(temp.optString("name"));
                                genNum++;
                                if(genNum < genMax){
                                    tvGenres.append(", ");
                                }
                            }
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
        client = new AsyncHttpClient();
        client.get(String.format(TRAILERS_API, movie.getMovieID()), new JsonHttpResponseHandler(){
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    JSONArray results = response.getJSONArray("results");
                    if(results.length() == 0) return;
                    JSONObject movieTrailer = results.getJSONObject(0);
                    String youTubeKey = movieTrailer.getString("key");
                    initializeYoutube(youTubeKey);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    private void initializeYoutube(final String youTubeKey) {
        youTubePlayerView.initialize(YOUTUBE_API_KEY, new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                Log.d("Linda", "on init success for youtubeplayer");
                youTubePlayer.cueVideo(youTubeKey);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Log.d("Linda", "on init fail for youtubeplayer");
            }
        });
    }
}
