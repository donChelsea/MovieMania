package com.example.moviemania.domain.repository

import com.example.moviemania.domain.models.Movie
import kotlinx.coroutines.flow.Flow

interface WatchListRepository {
    suspend fun saveMovie(movie: Movie)
    suspend fun getSavedMovies(): Flow<List<Movie>>
    suspend fun deletedMovie(movie: Movie)
}