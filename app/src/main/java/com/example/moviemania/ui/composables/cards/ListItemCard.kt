package com.example.moviemania.ui.composables.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviemania.R
import com.example.moviemania.common.mockMovie
import com.example.moviemania.data.remote.MovieApi.Companion.MOVIE_IMAGE_URL
import com.example.moviemania.domain.models.Movie

@Composable
fun ListItemCard(
    movie: Movie,
    onClick: (Int) -> Unit,
    onDelete: (Movie) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
                .height(250.dp)
                .clip(RectangleShape)
                .clickable { onClick(movie.id) },
            elevation = CardDefaults.cardElevation(
                defaultElevation = R.dimen.card_elevation.dp
            ),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(MOVIE_IMAGE_URL + movie.posterPath)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.baseline_image_24),
                    contentDescription = movie.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(width = 150.dp)
                        .fillMaxHeight()
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .height(250.dp)
                        .padding(top = 42.dp, start = 12.dp, end = 12.dp),
                ) {
                    Text(
                        text = movie.title,
                        fontSize = 24.sp,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = movie.overview,
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                }
            }
        }
        Icon(
            imageVector = Icons.Default.Close,
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .padding(18.dp)
                .clickable {
                    onDelete(movie)
                },
            contentDescription = stringResource(id = R.string.close)
        )
    }
}

@Composable
@Preview
fun PreviewMovieManiaWatchListCard() {
    ListItemCard(movie = mockMovie, onClick = { }, onDelete = {})
}
