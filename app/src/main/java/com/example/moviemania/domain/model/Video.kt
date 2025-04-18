package com.example.moviemania.domain.model

import javax.annotation.concurrent.Immutable

@Immutable
data class Video(
    val id: String,
    val name: String,
    val type: String,
    val site: String,
    val key: String
) {
    fun toUiModel() = VideoUiModel(
        id = id,
        name = name,
        type = type,
        key = key,
        site = site
    )
}
