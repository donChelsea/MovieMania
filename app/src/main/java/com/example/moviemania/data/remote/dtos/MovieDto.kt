package com.example.moviemania.data.remote.dtos

import com.google.gson.annotations.SerializedName

data class MovieDto(
    val adult: Boolean,
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
    @SerializedName("production_companies")
    val production: List<ProductionDto>?,
    val runtime: Int?,
)

data class ProductionDto(
    val name: String
)

data class GenreDto(
    val name: String,
    val id: Int,
)
