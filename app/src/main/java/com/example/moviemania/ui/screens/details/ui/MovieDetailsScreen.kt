@file:OptIn(ExperimentalMaterialApi::class)

package com.example.moviemania.ui.screens.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.moviemania.common.mockMovie
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.domain.models.Video
import com.example.moviemania.ui.composables.custom.CustomChipGroup
import com.example.moviemania.ui.composables.cards.DetailsCardLarge
import com.example.moviemania.ui.composables.screens.ShowError
import com.example.moviemania.ui.composables.screens.ShowLoading
import com.example.moviemania.ui.screens.details.DetailsUiAction
import com.example.moviemania.ui.screens.details.DetailsUiState
import com.example.moviemania.ui.screens.details.MovieDetailsViewModel
import com.example.moviemania.ui.screens.details.ScreenData

@Composable
fun MovieDetailsScreen(
    navController: NavHostController,
) {
    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->

        }
    }

    MovieDetailsLayout(
        state = state,
        onAction = viewModel::handleAction
    )
}

@Composable
fun MovieDetailsLayout(
    state: DetailsUiState,
    onAction: (DetailsUiAction) -> Unit,
) {
    when (state.screenData) {
        ScreenData.Empty -> {}
        ScreenData.Error -> ShowError()
        ScreenData.Loading -> ShowLoading()
        is ScreenData.Data -> state.screenData.movie?.let {
            MovieDetailsContent(
                movie = it,
                isSaved = state.screenData.isSaved,
                videos = state.screenData.videos,
                onAction = onAction
            )
        }
    }
}

@Composable
fun MovieDetailsContent(
    movie: Movie,
    videos: List<Video>,
    isSaved: Boolean,
    onAction: (DetailsUiAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        movie.let { movie ->
            DetailsCardLarge(movie = movie)

            Text(
                text = movie.title,
                style = MaterialTheme.typography.displayMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 8.dp)
            )

            Text(
                text = "${movie.releaseDate} • ${movie.runtime.toString()}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, bottom = 12.dp)
            )

            if (!movie.tagline.isNullOrEmpty()) {
                Text(
                    text = movie.tagline,
                    style = MaterialTheme.typography.titleLarge,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, bottom = 12.dp)
                )
            }

            movie.genres?.map { it.name }?.let { genres ->
                CustomChipGroup(
                    data = genres,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = movie.overview,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewMovieDetailsContent() {
    MovieDetailsContent(movie = mockMovie, isSaved = false, videos = emptyList(), onAction = {})
}