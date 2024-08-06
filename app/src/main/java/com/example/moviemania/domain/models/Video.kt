package com.example.moviemania.domain.models

import javax.annotation.concurrent.Immutable

@Immutable
data class Video(
    val id: String,
    val name: String,
    val type: String,
    val site: String,
    val key: String
)
