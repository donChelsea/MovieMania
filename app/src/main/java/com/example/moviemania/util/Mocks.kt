package com.example.moviemania.util

import com.example.moviemania.ui.model.GenreUiModel
import com.example.moviemania.ui.model.MovieUiModel

val mockMovieUiModel = MovieUiModel(
    "",
    1,
    "Movie Title",
    "Movie Tagline",
    "Movie Overview",
    "",
    "October 20, 2023",
    listOf(GenreUiModel(1, "Action")),
    "120"
)
