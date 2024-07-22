package com.example.moviemania.ui.composables.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviemania.R
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.common.MOVIE_IMAGE_URL

@Composable
fun CarouselItemCard(
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
            .size(
                width = dimensionResource(id = R.dimen.card_carousel_width).value.dp,
                height = dimensionResource(id = R.dimen.card_carousel_height).value.dp,
            )
            .clickable { onClick(movie.id) }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(MOVIE_IMAGE_URL + movie.posterPath)
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