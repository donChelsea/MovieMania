package com.example.moviemania.data.source.remote.model

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.moviemania.domain.model.Genre
import com.example.moviemania.domain.model.Movie
import com.example.moviemania.domain.model.Video
import com.example.moviemania.domain.model.VideoUiModel
import com.example.moviemania.util.convertReleasedDate
import com.example.moviemania.util.convertRuntime
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
) {
    @RequiresApi(Build.VERSION_CODES.O)
    fun toDomain() = Movie(
        backdropPath = backdropPath,
        id = id,
        title = title,
        tagline = tagline.orEmpty(),
        overview = overview,
        posterPath = posterPath,
        releaseDate = releaseDate.convertReleasedDate(),
        runtime = runtime?.convertRuntime(),
        genres = genres?.map { it.toDomain() },
    )
}

data class VideoDto(
    val name: String,
    val key: String,
    val site: String,
    val type: String,
    val id: String
) {
    fun toDomain() = Video(
        id = id,
        name = name,
        type = type,
        key = key,
        site = site
    )
}

data class GenreDto(
    val name: String,
    val id: Int,
) {
    fun toDomain() = Genre(
        id = id,
        name = name
    )
}
