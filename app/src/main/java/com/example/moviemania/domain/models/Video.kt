package com.example.moviemania.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

@Parcelize
@Immutable
data class Video(
    val id: String,
    val name: String,
    val type: String,
    val site: String,
    val key: String
): Parcelable
