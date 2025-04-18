package com.example.moviemania.data.local.model

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.moviemania.domain.model.Movie
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class MovieEntity(

    @PrimaryKey(autoGenerate = false)
    @NonNull
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "posterPath")
    val posterPath: String,

    @ColumnInfo(name = "overview")
    val overview: String,

    @ColumnInfo(name = "title")
    val title: String,
) : Parcelable {

    fun toDomain() = Movie(
        id = id,
        title = title,
        overview = overview,
        posterPath = posterPath,
        genres = null,
        releaseDate = "",
        tagline = "",
        backdropPath = null,
        runtime = null,
    )
}