package com.example.moviemania.data.remote

import com.example.moviemania.data.remote.dtos.ApiListResultDto
import com.example.moviemania.data.remote.dtos.MovieDto
import com.example.moviemania.data.remote.dtos.VideoDto
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

    companion object {
        const val BASE_URL = "https://api.themoviedb.org/3/"
        const val API_KEY = "f93059fa6dc40b8e3e733fcac8a251e4"
        const val API_LANGUAGE = "en-US"
        const val API_PAGE = "1"
        const val MOVIE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
    }
}