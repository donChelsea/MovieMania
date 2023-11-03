@file:OptIn(ExperimentalLayoutApi::class)

package com.example.moviemania.ui.common

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moviemania.domain.models.Genre

@Composable
fun NestedVerticalGrid(
    modifier: Modifier = Modifier,
    cellCount: Int = 3,
    data: List<Genre>,
    spaceBetween: Dp = 16.dp,
    horizontalPadding: Dp = 32.dp,
    verticalPadding: Dp = 24.dp,
    content: @Composable (Modifier) -> Unit,
) {
    FlowRow(maxItemsInEachRow = 4) {
        data.forEach { genre ->
            MovieManiaGridCard(genre)
        }
    }
}