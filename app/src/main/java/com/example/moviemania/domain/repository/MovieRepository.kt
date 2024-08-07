package com.example.moviemania.domain.repository

import com.example.moviemania.domain.models.Movie
import com.example.moviemania.domain.models.Video
import com.example.moviemania.common.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTrending(): Flow<Resource<List<Movie>>>
    suspend fun getNowPlaying(): Flow<Resource<List<Movie>>>
    suspend fun getUpcoming(): Flow<Resource<List<Movie>>>
    suspend fun getMovieDetails(movieId: String): Flow<Resource<Movie>>
    suspend fun getVideos(movieId: String): Flow<Resource<List<Video>>>
}