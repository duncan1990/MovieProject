package com.ahmety.movieapplicationproject.api

import com.ahmety.movieapplicationproject.model.Movie
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieAppService {
    companion object {
        const val ENDPOINT = "http://www.omdbapi.com/"
    }

    @GET(".")
    suspend fun getMovies(@Query("t") title: String, @Query("apikey") aKey: String): Response<Movie>
}