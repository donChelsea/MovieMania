@file:OptIn(ExperimentalLayoutApi::class)

package com.example.moviemania.ui.common

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moviemania.domain.models.Genre

@Composable
fun NestedVerticalGrid(
    modifier: Modifier = Modifier,
    data: List<Genre>,
    content: @Composable (Modifier) -> Unit,
) {
    FlowRow(
        modifier = modifier,
        maxItemsInEachRow = 4
    ) {
        data.forEach { genre ->
            MovieManiaGridCard(genre)
        }
    }
}