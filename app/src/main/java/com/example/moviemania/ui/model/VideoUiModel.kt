package com.example.moviemania.domain.model

import javax.annotation.concurrent.Immutable

@Immutable
data class VideoUiModel(
    val id: String,
    val name: String,
    val type: String,
    val site: String,
    val key: String
)
