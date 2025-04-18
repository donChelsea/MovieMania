package com.example.moviemania.domain.use_case

import com.example.moviemania.data.Resource
import com.example.moviemania.domain.model.Movie
import com.example.moviemania.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetTrendingUseCase @Inject constructor(
    private val repository: RemoteRepository
) {
    suspend operator fun invoke(): Flow<Resource<List<Movie>>> {
        return repository.getTrending()
    }
}