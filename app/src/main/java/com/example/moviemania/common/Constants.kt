package com.example.moviemania.common

import com.example.moviemania.domain.models.Genre
import com.example.moviemania.domain.models.Movie

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "f93059fa6dc40b8e3e733fcac8a251e4"
const val API_LANGUAGE = "en-US"
const val API_PAGE = "1"
const val MOVIE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
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
