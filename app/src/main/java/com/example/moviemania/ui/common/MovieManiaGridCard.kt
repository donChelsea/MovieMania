package com.example.moviemania.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.moviemania.domain.models.Genre

@Composable
fun MovieManiaGridCard(
    genre: Genre,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = { },
) {
    Card(
        modifier = modifier
            .size(115.dp)
            .aspectRatio(1.5F)
            .clickable(onClick = onClick),
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Blue)
        ) {
            Text(
                text = genre.name,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }

    }
}
