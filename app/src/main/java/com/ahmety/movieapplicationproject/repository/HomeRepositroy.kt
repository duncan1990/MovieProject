package com.ahmety.movieapplicationproject.repository

import com.ahmety.movieapplicationproject.api.MovieAppService
import com.ahmety.movieapplicationproject.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(
    private val movieAppService: MovieAppService
) {
    suspend fun getMovie(title: String, aKey: String): Response<Movie> = withContext(
        Dispatchers.IO
    ) {
        val movies = movieAppService.getMovies(title = title, aKey = aKey)
        movies

    }
}