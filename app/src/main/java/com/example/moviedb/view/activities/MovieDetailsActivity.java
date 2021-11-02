package com.example.moviedb.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.moviedb.R;
import com.example.moviedb.helper.Const;
import com.example.moviedb.model.Movies;
import com.example.moviedb.viewmodel.MovieViewModel;

public class MovieDetailsActivity extends AppCompatActivity {

    private TextView lbl_movie_title, lbl_movie_releaseDate, lbl_movie_synopsis, lbl_movie_genre, lbl_movie_duration, lbl_movie_rating;
    private MovieViewModel movieViewModel;
    private ImageView img_details;
    private String movie_id = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);

        movieViewModel = new ViewModelProvider(MovieDetailsActivity.this).get(MovieViewModel.class);

        Intent intent = getIntent();
        movie_id = intent.getStringExtra("movie_id");

        img_details = findViewById(R.id.img_details_fragment);
        lbl_movie_title = findViewById(R.id.lbl_movie_title_fragment);
        lbl_movie_releaseDate = findViewById(R.id.lbl_movie_releaseDate_fragment);
        lbl_movie_synopsis = findViewById(R.id.lbl_movie_synopsis_fragment);
        lbl_movie_genre = findViewById(R.id.lbl_movie_genre_fragment);
        lbl_movie_duration = findViewById(R.id.lbl_movie_duration_fragment);
        lbl_movie_rating = findViewById(R.id.lbl_movie_rating_fragment);

        movieViewModel.getMovieById(movie_id);
        movieViewModel.getResultGetMovieById().observe(MovieDetailsActivity.this, showMovieDetails);
    }

    private Observer<Movies> showMovieDetails = new Observer<Movies>() {
        @Override
        public void onChanged(Movies movies) {
            String title = movies.getTitle();
            String releaseDate = movies.getRelease_date();
            String synopsis = movies.getOverview();
            String duration = String.valueOf(movies.getRuntime());
            String rating = String.valueOf(movies.getVote_average());
            String genre = "";
            String img_path = Const.IMG_URL + movies.getPoster_path().toString();

            Glide.with(MovieDetailsActivity.this).load(img_path).into(img_details);
            for (int i = 0; i<movies.getGenres().size(); i++) {
                if (i == movies.getGenres().size() - 1) {
                    genre += movies.getGenres().get(i).getName();
                }else{
                    genre += movies.getGenres().get(i).getName()+", ";
                }
            }

            lbl_movie_title.setText(title);
            lbl_movie_releaseDate.setText(releaseDate);
            lbl_movie_synopsis.setText(synopsis);
            lbl_movie_duration.setText(duration);
            lbl_movie_rating.setText(rating);
            lbl_movie_genre.setText(genre);
        }
    };

    @Override
    public void onBackPressed() {

        finish();
    }



    private void GetDataFromIntent(){

    }

}