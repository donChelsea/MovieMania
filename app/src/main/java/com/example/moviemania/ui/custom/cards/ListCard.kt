package com.example.moviemania.ui.custom.cards

import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.moviemania.BuildConfig
import com.example.moviemania.R
import com.example.moviemania.ui.model.MovieUiModel
import com.example.moviemania.util.mockMovieUiModel

@Composable
fun ListCard(
    movieUiModel: MovieUiModel,
    onClick: (Int) -> Unit,
    onDelete: (MovieUiModel) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        Card(
            modifier = modifier
                .fillMaxWidth()
                .padding(12.dp)
                .height(250.dp)
                .clip(RectangleShape)
                .clickable { onClick(movieUiModel.id) },
            colors = CardDefaults.cardColors(
                containerColor = Color.Gray
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = R.dimen.card_elevation.dp
            ),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(BuildConfig.IMAGES_BASE_URL + movieUiModel.posterPath)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.baseline_image_24),
                    contentDescription = movieUiModel.title,
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
                        text = movieUiModel.title,
                        fontSize = 24.sp,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = movieUiModel.overview,
                        fontSize = 12.sp,
                        overflow = TextOverflow.Ellipsis,
                        color = MaterialTheme.colors.onPrimary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 12.dp)
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
                    onDelete(movieUiModel)
                },
            contentDescription = stringResource(id = R.string.close)
        )
    }
}

@Composable
@Preview
fun PreviewListCard() {
    ListCard(movieUiModel = mockMovieUiModel, onClick = { }, onDelete = {})
}
