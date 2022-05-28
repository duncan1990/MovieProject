package com.ahmety.movieapplicationproject.ui

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.ahmety.movieapplicationproject.adapter.HomeAdapter
import com.ahmety.movieapplicationproject.databinding.ActivityMainBinding
import com.ahmety.movieapplicationproject.model.Movie
import com.ahmety.movieapplicationproject.util.Resource
import com.ahmety.movieapplicationproject.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.*


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val homeViewModel: HomeViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding
    private var adapter: HomeAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setPageTransitionAnimation()
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        welcomeAnimation()
        setupOnClickListener()
        observeUI()
    }

    private fun setPageTransitionAnimation() {
        val slide = Slide()
        slide.slideEdge = Gravity.END
        slide.duration = 300
        slide.interpolator = AccelerateDecelerateInterpolator()
        window.exitTransition = slide
        window.enterTransition = slide
    }

    private fun welcomeAnimation() {
        binding.animationViewLoading.isVisible = true
        Timer().schedule(
            object : TimerTask() {
                override fun run() {
                    runOnUiThread {
                        binding.animationViewLoading.isVisible = false
                        homeViewModel.getMovie("a")
                    }
                }
            },
            3000
        )
    }

    private fun setupOnClickListener() {
        binding.apply {
            buttonSearch.setOnClickListener {
                homeViewModel.getMovie(searchEditText.text.toString())
            }
        }
    }

    private fun observeUI() {
        adapter = HomeAdapter(::onClickMovie)
        binding.mainRecyclerview.adapter = adapter
        homeViewModel.movieList.observe(this) {
            when (it) {
                is Resource.Success -> {
                    binding.animationViewLoading.isVisible = false
                    val data = it.data
                    adapter?.submitList(mutableListOf(data))
                }
                is Resource.Error -> {
                    binding.animationViewLoading.isVisible = false
                    it.message?.let { message ->
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                }
                is Resource.Loading -> {
                    binding.animationViewLoading.isVisible = true
                }
            }

        }
    }

    private fun onClickMovie(movie: Movie) {
        val intent = Intent(this@MainActivity,MovieActivity::class.java)
        intent.putExtra("title", movie.Title)
        intent.putExtra("poster", movie.Poster)
        intent.putExtra("country", movie.Country)
        intent.putExtra("imdb", movie.imdbRating)
        intent.putExtra("genre", movie.Genre)
        intent.putExtra("plot", movie.Plot)
        intent.putExtra("runtime", movie.Runtime)
        intent.putExtra("year", movie.Year)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
    }

}