package com.example.moviemania.domain.use_case

import com.example.moviemania.data.Resource
import com.example.moviemania.domain.model.Video
import com.example.moviemania.domain.repository.RemoteRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVideosUseCase @Inject constructor(
    private val repository: RemoteRepository
) {
    suspend operator fun invoke(movieId: String): Flow<Resource<List<Video>>> {
        return repository.getVideos(movieId = movieId)
    }
}