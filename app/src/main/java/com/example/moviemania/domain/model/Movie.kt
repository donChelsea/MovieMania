package com.example.moviemania.domain.model

import com.example.moviemania.data.source.local.model.MovieEntity
import com.example.moviemania.ui.model.MovieUiModel
import javax.annotation.concurrent.Immutable

@Immutable
data class Movie(
    val backdropPath: String?,
    val id: Int,
    val title: String,
    val tagline: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val genres: List<Genre>?,
    val runtime: String?
) {
    fun toUiModel() = MovieUiModel(
        backdropPath = backdropPath,
        id = id,
        title = title,
        tagline = tagline,
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate,
        runtime = runtime,
        genres = genres?.map { it.toUiModel() },
    )

    fun toEntity() = MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath.orEmpty(),
    )
}