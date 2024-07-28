package com.example.moviemania.domain.repository

import com.example.moviemania.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface WatchLaterRepository {
    suspend fun saveMovie(movie: Movie)
    suspend fun getSavedMovies(): Flow<List<Movie>>
    suspend fun deleteMovie(movie: Movie)
}