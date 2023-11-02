package com.example.moviesrus.domain.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Production(
    val name: String
): Parcelable

