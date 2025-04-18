package com.example.moviemania.data.source.local.repository

import com.example.moviemania.data.source.local.MovieDao
import com.example.moviemania.data.source.local.model.MovieEntity
import com.example.moviemania.domain.model.Movie
import com.example.moviemania.domain.repository.LocalRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalRepositoryImpl @Inject constructor(
    private val dao: MovieDao
): LocalRepository {
    override suspend fun saveMovie(movie: MovieEntity) {
        return dao.saveMovie(movie)
    }

    override suspend fun getSavedMovies(): Flow<List<Movie>> {
        return dao.getSavedMoviesFlow().map { list -> list.map { movieEntity -> movieEntity.toDomain() } }
    }

    override suspend fun deleteMovie(movie: MovieEntity) {
        dao.deleteMovie(movie)
    }
}