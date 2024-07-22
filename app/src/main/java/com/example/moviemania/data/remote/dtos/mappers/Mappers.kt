package com.example.moviemania.data.remote.dtos.mappers

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.moviemania.data.remote.dtos.GenreDto
import com.example.moviemania.data.remote.dtos.MovieDto
import com.example.moviemania.data.remote.dtos.VideoDto
import com.example.moviemania.domain.models.Genre
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.common.convertReleasedDate
import com.example.moviemania.common.convertRuntime
import com.example.moviemania.domain.models.Video

@RequiresApi(Build.VERSION_CODES.O)
fun MovieDto.toDomain() = Movie(
    backdropPath = backdropPath,
    id = id,
    title = title,
    tagline = tagline.orEmpty(),
    overview = overview,
    posterPath = posterPath,
    releaseDate = convertReleasedDate(releaseDate),
    runtime = runtime?.let { convertRuntime(it) },
    genres = genres?.map { it.toDomain() },
)

fun VideoDto.toDomain() = Video(
    id = id,
    name = name,
    type = type,
    key = key,
    site = site
)

fun GenreDto.toDomain() = Genre(
    id = id,
    name = name
)