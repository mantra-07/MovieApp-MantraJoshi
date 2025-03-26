package com.example.assignment2_joshi;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<List<Movie>> movieList;

    public LiveData<List<Movie>> getMovies(String query, String apiKey) {
        if (movieList == null) {
            movieList = new MutableLiveData<>();
        }
        loadMovies(query, apiKey);
        return movieList;
    }

    private void loadMovies(String query, String apiKey) {
        OMDBApi api = RetrofitClient.getRetrofitInstance();
        api.searchMovies(query, apiKey).enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().getSearch() != null) {
                        List<Movie> movies = response.body().getSearch();
                        Log.d("API_RESPONSE", "Movies received: " + movies.size());
                        Log.d("API_RESPONSE", "Raw response: " + response.body().toString());
                        movieList.setValue(movies);
                    } else {
                        Log.d("API_RESPONSE", "Search field is null in response");
                        movieList.setValue(null);
                    }
                } else {
                    Log.d("API_RESPONSE", "No movies or bad response");
                    movieList.setValue(null);
                }
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e("API_ERROR", "Failed to fetch movies: " + t.getMessage());
                movieList.setValue(null);
            }
        });
    }
}
