package com.example.moviemania.domain.repository

import com.example.moviemania.data.local.model.MovieEntity
import com.example.moviemania.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface WatchLaterRepository {
    suspend fun saveMovie(movie: MovieEntity)
    suspend fun getSavedMovies(): Flow<List<Movie>>
    suspend fun deleteMovie(movie: MovieEntity)
}