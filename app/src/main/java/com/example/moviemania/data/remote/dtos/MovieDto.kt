package com.example.moviemania.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    val id: Int,
    val title: String,
    val tagline: String?,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String,
    val genres: List<GenreDto>?,
    val runtime: Int?,
)

data class VideoDto(
    val name: String,
    val key: String,
    val site: String,
    val type: String,
    val id: String
)

data class GenreDto(
    val name: String,
    val id: Int,
)
