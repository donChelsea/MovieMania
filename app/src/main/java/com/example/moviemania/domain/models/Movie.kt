package com.example.moviemania.domain.models

import android.os.Parcelable
import com.example.moviesrus.domain.models.Production
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val adult: Boolean,
    val backdropPath: String?,
    val id: Int,
    val title: String,
    val tagline: String,
    val overview: String,
    val posterPath: String?,
    val releaseDate: String,
    val genres: List<Genre>?,
    val production: List<Production>?,
    val runtime: String?,
): Parcelable
