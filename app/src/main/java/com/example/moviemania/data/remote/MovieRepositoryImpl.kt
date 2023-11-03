package com.example.moviemania.data.remote

import com.example.moviemania.data.dtos.mappers.toDomain
import com.example.moviemania.domain.models.Genre
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.domain.repository.MovieRepository
import com.example.moviemania.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi
) : MovieRepository {

    override suspend fun getTrending(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading(isLoading = true))

        val movies = api.getTrending().results
        with(movies) {
            emit(Resource.Success(data = map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun getNowPlaying(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading(isLoading = true))

        val movies = api.getNowPlaying().results
        with(movies) {
            emit(Resource.Success(data = map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun getGenres(): Flow<Resource<List<Genre>>> = flow {
        emit(Resource.Loading(isLoading = true))

        val movies = api.getGenres().genres
        with(movies) {
            emit(Resource.Success(data = map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun getUpcoming(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading(isLoading = true))

        val movies = api.getUpcoming().results
        with(movies) {
            emit(Resource.Success(data = map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)
}