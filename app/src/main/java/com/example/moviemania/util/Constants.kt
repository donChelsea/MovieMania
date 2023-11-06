package com.example.moviemania.util

import com.example.moviemania.domain.models.Genre
import com.example.moviemania.domain.models.Movie
import com.example.moviesrus.domain.models.Production

const val BASE_URL = "https://api.themoviedb.org/3/"
const val API_KEY = "f93059fa6dc40b8e3e733fcac8a251e4"
const val API_LANGUAGE = "en-US"
const val API_PAGE = "1"
const val GENRE_IMAGE_URL = "https://is4-ssl.mzstatic.com/image/thumb/Purple125/v4/d7/3d/ef/d73defb8-0c4a-b4c4-3814-967e624fbe1a/source/256x256bb.jpg"
const val API_SORT_BY_POPULARITY_DESC = "popularity.desc"
const val MOVIE_IMAGE_URL = "https://image.tmdb.org/t/p/w500/"
const val YOUTUBE_URL = "https://www.youtube.com/watch?v="
const val YOUTUBE_API_KEY = "AIzaSyBTbjiKlmQ08PEWFYZAcfUsfASxmCPdDvI"
val mockMovie = Movie(
    false,
    "",
    1,
    "Movie Title",
    "Movie Tagline",
    "Movie Overview",
    "",
    "October 20, 2023",
    listOf(Genre(1, "Action")),
    listOf(Production("Production team")),
    "120"
)
