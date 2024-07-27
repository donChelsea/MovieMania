package com.example.moviemania.common

import com.example.moviemania.domain.models.Genre
import com.example.moviemania.domain.models.Movie

val mockMovie = Movie(
    "",
    1,
    "Movie Title",
    "Movie Tagline",
    "Movie Overview",
    "",
    "October 20, 2023",
    listOf(Genre(1, "Action")),
    "120"
)
