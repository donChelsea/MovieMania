package com.example.moviemania.ui.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import javax.annotation.concurrent.Immutable

@Parcelize
@Immutable
data class GenreUiModel(
    val id: Int,
    val name: String,
): Parcelable
