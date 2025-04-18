package com.example.moviemania.data.source.remote

import com.example.moviemania.data.source.remote.model.ApiListResultDto
import com.example.moviemania.data.source.remote.model.MovieDto
import com.example.moviemania.data.source.remote.model.VideoDto
import retrofit2.http.GET
import retrofit2.http.Path

interface MovieApi {
    @GET("trending/movie/day")
    suspend fun getTrending(): ApiListResultDto<MovieDto>

    @GET("movie/now_playing")
    suspend fun getNowPlaying(): ApiListResultDto<MovieDto>

    @GET("movie/upcoming")
    suspend fun getUpcoming(): ApiListResultDto<MovieDto>

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: String,
    ): MovieDto

    @GET("movie/{movieId}/videos")
    suspend fun getVideos(
        @Path("movieId") movieId: String,
    ): ApiListResultDto<VideoDto>
}