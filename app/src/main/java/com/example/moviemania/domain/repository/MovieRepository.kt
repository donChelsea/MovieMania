package com.example.moviemania.domain.repository

import com.example.moviemania.domain.models.Movie
import com.example.moviemania.util.Resource
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    suspend fun getMovies(): Flow<Resource<List<Movie>>>
}