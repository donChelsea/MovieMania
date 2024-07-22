package com.example.moviemania.data.remote.repository

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.moviemania.data.remote.MovieApi
import com.example.moviemania.data.remote.dtos.mappers.toDomain
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.domain.models.Video
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

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getTrending(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading(isLoading = true))

        val movies = api.getTrending().results
        with(movies) {
            emit(Resource.Success(data = map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getNowPlaying(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading(isLoading = true))

        val movies = api.getNowPlaying().results
        with(movies) {
            emit(Resource.Success(data = map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getUpcoming(): Flow<Resource<List<Movie>>> = flow {
        emit(Resource.Loading(isLoading = true))

        val movies = api.getUpcoming().results
        with(movies) {
            emit(Resource.Success(data = map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun getMovieDetails(movieId: String): Flow<Resource<Movie>> = flow {
        emit(Resource.Loading(isLoading = true))

        val movie = api.getMovieDetails(movieId = movieId)
        with(movie) {
            emit(Resource.Success(toDomain()))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)

    override suspend fun getVideos(movieId: String): Flow<Resource<List<Video>>> = flow {
        emit(Resource.Loading(isLoading = true))

        val videos = api.getVideos(movieId = movieId).results
        with(videos) {
            emit(Resource.Success(map { it.toDomain() }))
        }
    }.catch { e ->
        emit(Resource.Error(message = e.message.toString()))
    }.flowOn(Dispatchers.IO)
}