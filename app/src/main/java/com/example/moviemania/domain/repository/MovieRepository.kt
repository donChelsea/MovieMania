package com.example.moviemania.domain.repository

import com.example.moviemania.data.Resource
import com.example.moviemania.domain.model.Movie
import com.example.moviemania.domain.model.Video
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getTrending(): Flow<Resource<List<Movie>>>
    suspend fun getNowPlaying(): Flow<Resource<List<Movie>>>
    suspend fun getUpcoming(): Flow<Resource<List<Movie>>>
    suspend fun getMovieDetails(movieId: String): Flow<Resource<Movie>>
    suspend fun getVideos(movieId: String): Flow<Resource<List<Video>>>
}