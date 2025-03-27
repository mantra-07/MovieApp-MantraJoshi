package com.example.assignment2_joshi;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.assignment2_joshi.databinding.ActivityMainBinding;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private SearchViewModel viewModel;
    private MovieAdapter adapter;
    private final String API_KEY = "fc6b6a0e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        viewModel = new ViewModelProvider(this).get(SearchViewModel.class);

        adapter = new MovieAdapter(this, new ArrayList<>());
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerView.setAdapter(adapter);

        binding.btnSearch.setOnClickListener(v -> {
            String query = binding.etSearch.getText().toString().trim();
            if (!query.isEmpty()) {
                searchMovies(query);
            } else {
                Toast.makeText(this, "Please enter a movie name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void searchMovies(String query) {
        viewModel.getMovies(query, API_KEY).observe(this, movies -> {
            if (movies != null && !movies.isEmpty()) {
                adapter.setMovieList(movies);
            } else {
                Toast.makeText(this, "No results found", Toast.LENGTH_SHORT).show();
                adapter.setMovieList(new ArrayList<>()); // clear list if no result
            }
        });
    }
}
