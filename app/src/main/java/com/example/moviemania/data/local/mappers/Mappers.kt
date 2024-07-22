package com.example.moviemania.data.local.mappers

import com.example.moviemania.data.local.MovieEntity
import com.example.moviemania.domain.models.Movie

fun MovieEntity.toDomain() = Movie(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    genres = null,
    releaseDate = "",
    tagline = "",
    backdropPath = null,
    runtime = null,
)

fun Movie.toEntity() = MovieEntity(
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath.orEmpty(),
)
