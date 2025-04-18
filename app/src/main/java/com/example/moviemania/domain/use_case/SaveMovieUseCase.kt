package com.example.moviemania.domain.use_case

import com.example.moviemania.data.source.local.model.MovieEntity
import com.example.moviemania.domain.repository.LocalRepository
import javax.inject.Inject

class SaveMovieUseCase @Inject constructor(
    private val repository: LocalRepository
) {
    suspend operator fun invoke(movie: MovieEntity) =
        repository.saveMovie(movie = movie)
}