package com.ahmety.movieapplicationproject.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahmety.movieapplicationproject.R
import com.ahmety.movieapplicationproject.databinding.ActivityMovieBinding
import com.bumptech.glide.Glide

class MovieActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMovieBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMovieBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setText()
    }

    private fun setText() {
        binding.apply {
            Glide.with(imageViewMovie).load(intent.getStringExtra("poster")).error(R.drawable.ic_baseline_warning_24)
                .into(imageViewMovie)
            textViewMYear.text = intent.getStringExtra("year")
            textViewMCountry.text = intent.getStringExtra("country")
            textViewMRuntime.text = intent.getStringExtra("runtime")
            textViewMImdb.text = getString(R.string.textImdb, intent.getStringExtra("imdb"))
            textViewMGenre.text = intent.getStringExtra("genre")
            textViewMTitle.text = intent.getStringExtra("title")
            textViewMPlot.text = intent.getStringExtra("plot")
        }
    }
}