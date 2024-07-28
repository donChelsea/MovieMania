package com.example.moviemania.data.local.repository

import com.example.moviemania.data.local.MovieDao
import com.example.moviemania.data.local.mappers.toDomain
import com.example.moviemania.data.local.mappers.toEntity
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.domain.repository.WatchLaterRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WatchLaterRepositoryImpl @Inject constructor(
    private val dao: MovieDao
): WatchLaterRepository {
    override suspend fun saveMovie(movie: Movie) {
        return dao.saveMovie(movie.toEntity())
    }

    override suspend fun getSavedMovies(): Flow<List<Movie>> {
        return dao.getSavedMovies().map { list -> list.map { movieEntity -> movieEntity.toDomain() } }
    }

    override suspend fun deleteMovie(movie: Movie) {
        dao.deleteMovie(movie.toEntity())
    }
}