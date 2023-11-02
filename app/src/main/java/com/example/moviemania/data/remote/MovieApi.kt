package com.example.moviemania.data.remote

import com.example.moviemania.data.dtos.ApiListResultDto
import com.example.moviemania.data.dtos.MovieDto
import com.example.moviemania.util.API_KEY
import com.example.moviemania.util.API_LANGUAGE
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieApi {

    @GET("trending/movie/day")
    suspend fun getMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = API_LANGUAGE,
    ): ApiListResultDto<MovieDto>
}