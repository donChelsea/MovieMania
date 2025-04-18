package com.example.moviemania.domain.use_case

import com.example.moviemania.domain.repository.LocalRepository
import javax.inject.Inject

class GetSavedMoviesUseCase @Inject constructor(
    private val repository: LocalRepository
) {
    suspend operator fun invoke() =
        repository.getSavedMovies()
}