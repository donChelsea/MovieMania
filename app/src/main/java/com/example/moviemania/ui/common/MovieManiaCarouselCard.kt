package com.example.moviemania.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviemania.R
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.util.MOVIE_IMAGE_URL

@Composable
fun MovieManiaCarouselCard(
    movie: Movie,
    modifier: Modifier = Modifier,
    onClick: (Int) -> Unit,
) {
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = R.dimen.card_elevation.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .size(150.dp)
            .clickable { onClick(movie.id) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(MOVIE_IMAGE_URL + movie.backdropPath)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.baseline_image_24),
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth()
        )
        Text(text = movie.title)
    }
}