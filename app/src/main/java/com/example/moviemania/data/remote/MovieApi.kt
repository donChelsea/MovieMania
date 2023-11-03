package com.example.moviemania.data.remote

import com.example.moviemania.data.dtos.ApiGenresResultDto
import com.example.moviemania.data.dtos.ApiListResultDto
import com.example.moviemania.data.dtos.GenreDto
import com.example.moviemania.data.dtos.MovieDto
import com.example.moviemania.util.API_KEY
import com.example.moviemania.util.API_LANGUAGE
import com.example.moviemania.util.API_PAGE
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {
    @GET("trending/movie/day")
    suspend fun getTrending(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = API_LANGUAGE,
    ): ApiListResultDto<MovieDto>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = API_LANGUAGE,
        @Query("page") page: String = API_PAGE
    ): ApiListResultDto<MovieDto>

    @GET("movie/upcoming")
    suspend fun getUpcoming(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = API_LANGUAGE,
        @Query("page") page: String = API_PAGE
    ): ApiListResultDto<MovieDto>

    @GET("genre/movie/list")
    suspend fun getGenres(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = API_LANGUAGE,
    ): ApiGenresResultDto<GenreDto>
}