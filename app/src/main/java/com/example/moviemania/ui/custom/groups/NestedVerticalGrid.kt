@file:OptIn(ExperimentalLayoutApi::class)

package com.example.moviemania.ui.custom.groups

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.moviemania.domain.model.Genre
import com.example.moviemania.ui.custom.cards.GridCard

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
            GridCard(genre)
        }
    }
}