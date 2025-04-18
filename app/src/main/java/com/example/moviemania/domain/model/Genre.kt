package com.example.moviemania.domain.model

import com.example.moviemania.ui.model.GenreUiModel
import javax.annotation.concurrent.Immutable

@Immutable
data class Genre(
    val id: Int,
    val name: String,
) {
    fun toUiModel() = GenreUiModel(
        id = id,
        name = name
    )
}
