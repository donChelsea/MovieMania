package com.example.moviemania.data.remote

import com.example.moviemania.data.remote.dtos.ApiListResultDto
import com.example.moviemania.data.remote.dtos.MovieDto
import com.example.moviemania.data.remote.dtos.VideoDto
import com.example.moviemania.common.API_KEY
import com.example.moviemania.common.API_LANGUAGE
import com.example.moviemania.common.API_PAGE
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

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String = API_KEY,
        @Query("language") language: String = API_LANGUAGE
    ): MovieDto

    @GET("movie/{movieId}/videos")
    suspend fun getVideos(
        @Path("movieId") movieId: String,
        @Query("api_key") apiKey: String = API_KEY
    ): ApiListResultDto<VideoDto>
}