package com.example.moviedb.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    private MovieViewModel viewModel;
    private Button btn_hit;
    private TextInputLayout til_movie_id;
    private ImageView img_poster;
    private TextView txt_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        txt_show = findViewById(R.id.txt_show);
        til_movie_id = findViewById(R.id.til_movie_id);
        img_poster = findViewById(R.id.img_poster);
        viewModel = new ViewModelProvider(MainActivity.this).get(MovieViewModel.class);

        btn_hit = findViewById(R.id.btn_hit_main);
        btn_hit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //viewModel.getMovieById("350");
                //viewModel.getResultGetMovieById().observe(MainActivity.this, showResultMovie);
                //            }
                String movieId = til_movie_id.getEditText().getText().toString().trim();
                if (movieId.isEmpty()) {
                    til_movie_id.setError("Please fill movie id field!");
                } else {
                    til_movie_id.setError(null);
                    viewModel.getMovieById(movieId);
                    viewModel.getResultGetMovieById().observe(MainActivity.this, showResultMovie);
                }
            }
        });
    }

    private Observer<Movies> showResultMovie = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            if (movies == null){
                txt_show.setText("Movie ID is not available in MovieDB");
            }else{
                String title = movies.getTitle();
                String img_path = movies.getPoster_path().toString();
                String full_path = "https://image.tmdb.org/t/p/w500/" + img_path;
                Glide.with(MainActivity.this).load(full_path).into(img_poster);
                txt_show.setText(title);
            }
        }
    };

}