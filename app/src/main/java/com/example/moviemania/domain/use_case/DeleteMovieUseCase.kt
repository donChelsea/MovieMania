package com.example.moviemania.domain.use_case

import com.example.moviemania.data.source.local.model.MovieEntity
import com.example.moviemania.domain.repository.LocalRepository
import javax.inject.Inject

class DeleteMovieUseCase @Inject constructor(
    private val repository: LocalRepository
) {
    suspend operator fun invoke(movie: MovieEntity) =
        repository.deleteMovie(movie = movie)
}