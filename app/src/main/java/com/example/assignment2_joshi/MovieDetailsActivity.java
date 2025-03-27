package com.example.assignment2_joshi;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MovieDetailsActivity extends AppCompatActivity {

    private String imdbID;
    private OMDBApi api;
    private String apiKey = "fc6b6a0e";

    private TextView tvTitle, tvPlot;
    private ImageView posterImage;
    private Button btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        imdbID = getIntent().getStringExtra("imdbID");

        tvTitle = findViewById(R.id.tvTitle);
        tvPlot = findViewById(R.id.tvPlot);
        posterImage = findViewById(R.id.posterImage);
        btnBack = findViewById(R.id.btnBack);

        api = RetrofitClient.getRetrofitInstance();

        api.getMovieDetails(imdbID, apiKey).enqueue(new Callback<MovieDetails>() {
            @Override
            public void onResponse(Call<MovieDetails> call, Response<MovieDetails> response) {
                if (response.isSuccessful() && response.body() != null) {
                    MovieDetails movie = response.body();
                    tvTitle.setText(movie.getTitle());
                    tvPlot.setText(movie.getPlot());
                    Picasso.get().load(movie.getPoster()).into(posterImage);
                }
            }

            @Override
            public void onFailure(Call<MovieDetails> call, Throwable t) {
                tvPlot.setText("Error loading movie details.");
            }
        });

        btnBack.setOnClickListener(v -> finish());
    }
}
