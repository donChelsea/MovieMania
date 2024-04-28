package com.example.moviemania.data.remote.dtos.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.moviemania.data.remote.dtos.GenreDto
import com.example.moviemania.data.remote.dtos.MovieDto
import com.example.moviemania.data.remote.dtos.ProductionDto
import com.example.moviemania.domain.models.Genre
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.util.convertReleasedDate
import com.example.moviemania.util.convertRuntime
import com.example.moviemania.domain.models.Production

@RequiresApi(Build.VERSION_CODES.O)
fun MovieDto.toDomain() = Movie(
    adult = adult,
    backdropPath = backdropPath,
    id = id,
    title = title,
    tagline = tagline.orEmpty(),
    overview = overview,
    posterPath = posterPath,
    releaseDate = convertReleasedDate(releaseDate),
    runtime = runtime?.let { convertRuntime(it) },
    genres = genres?.map { it.toDomain() },
    production = production?.map { it.toDomain() },
)

fun GenreDto.toDomain() = Genre(
    id = id,
    name = name
)

fun ProductionDto.toDomain() = Production(name)