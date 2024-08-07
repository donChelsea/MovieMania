package com.example.moviemania.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

@Parcelize
@Immutable
data class Genre(
    val id: Int,
    val name: String,
): Parcelable
