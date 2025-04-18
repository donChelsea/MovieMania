package com.example.moviemania.domain.repository

import com.example.moviemania.data.source.local.model.MovieEntity
import com.example.moviemania.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface LocalRepository {
    suspend fun saveMovie(movie: MovieEntity)
    suspend fun getSavedMovies(): Flow<List<Movie>>
    suspend fun deleteMovie(movie: MovieEntity)
}