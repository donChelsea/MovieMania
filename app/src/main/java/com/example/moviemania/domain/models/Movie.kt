package com.example.moviemania.domain.models

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

@Parcelize
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
): Parcelable