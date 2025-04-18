package com.example.moviemania.ui.custom.groups

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@ExperimentalMaterialApi
@Composable
fun CustomChipGroup(
    data: List<String>,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Row(modifier = Modifier.horizontalScroll(rememberScrollState())) {
            repeat(data.size) { index ->
                Chip(
                    modifier = Modifier
                        .padding(horizontal = 4.dp),
                    colors = ChipDefaults.chipColors(backgroundColor = MaterialTheme.colorScheme.primary),
                    onClick = {}
                ) {
                    Text(
                        text = data[index],
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}
