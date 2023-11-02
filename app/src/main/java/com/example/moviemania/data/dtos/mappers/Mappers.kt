package com.example.moviemania.data.dtos.mappers

import com.example.moviemania.data.dtos.GenreDto
import com.example.moviemania.data.dtos.LanguageDto
import com.example.moviemania.data.dtos.MovieDto
import com.example.moviemania.data.dtos.ProductionDto
import com.example.moviemania.domain.models.Genre
import com.example.moviemania.domain.models.Language
import com.example.moviemania.domain.models.Movie
import com.example.moviesrus.domain.models.Production

fun MovieDto.toDomain() = Movie(
    adult = adult,
    backdropPath = backdropPath,
    id = id,
    title = title,
    overview = overview,
    posterPath = posterPath,
    releaseDate = releaseDate,
    runtime = runtime,
    genres = genres?.map { it.toDomain() },
    production = production?.map { it.toDomain() },
    languages = languages?.map { it.toDomain() }
)

fun GenreDto.toDomain() = Genre(
    id = id,
    name = name
)

fun ProductionDto.toDomain() = Production(name)

fun LanguageDto.toDomain() = Language(name)



//fun VideoDto.toDomain() = Video(
//    key = key,
//    site = site,
//    type = type
//)