package com.example.moviemania.ui.model

import android.os.Parcelable
import com.example.moviemania.data.source.local.model.MovieEntity
import com.example.moviemania.domain.model.Genre
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

@Parcelize
@Immutable
data class MovieUiModel(
    val backdropPath: String?,
    val id: Int,
    val title: String,
    val tagline: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val genres: List<GenreUiModel>?,
    val runtime: String?
): Parcelable {

    fun toEntity() = MovieEntity(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath.orEmpty(),
    )
}