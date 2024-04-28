@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moviemania.ui.screens.details.ui

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.moviemania.R
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.ui.composables.MovieManiaLargeCard
import com.example.moviemania.ui.screens.details.DetailsUiAction
import com.example.moviemania.ui.screens.details.DetailsUiEvent
import com.example.moviemania.ui.screens.details.DetailsUiState
import com.example.moviemania.ui.screens.details.MovieDetailsViewModel
import com.example.moviemania.ui.screens.details.ScreenData
import com.example.moviemania.ui.screens.empty.ErrorScreen
import com.example.moviemania.ui.screens.empty.LoadingScreen
import com.example.moviemania.util.mockMovie

@Composable
fun MovieDetailsScreen(
    navController: NavHostController,
) {
    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                DetailsUiEvent.OnNavigateBack -> navController.popBackStack()
            }
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
        ScreenData.Error -> ErrorScreen()
        ScreenData.Loading -> LoadingScreen()
        ScreenData.Offline -> {}
        is ScreenData.Data -> state.screenData.movie?.let {
            MovieDetailsContent(
                movie = it,
                isSaved = state.screenData.isSaved,
                onAction = onAction
            )
        }
    }
}

@Composable
fun MovieDetailsContent(
    movie: Movie,
    isSaved: Boolean,
    onAction: (DetailsUiAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.movie_details))
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(),
                actions = {
                    IconButton(onClick = {
                        onAction(DetailsUiAction.OnSaveToWatchlist(movie))
                    }) {
                        Icon(
                            imageVector = if (isSaved) Icons.Filled.Favorite else Icons.Outlined.Favorite,
                            contentDescription = stringResource(id = R.string.content_description_save_unsave)
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(DetailsUiAction.OnNavigateBack) }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }, content = { paddingValues ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxSize()
            ) {
                movie.let { movie ->
                    MovieManiaLargeCard(movie = movie)
                    Text(
                        text = movie.title,
                        style = TextStyle(
                            fontSize = dimensionResource(id = R.dimen.details_title_text_size).value.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    )
                    Text(
                        text = movie.tagline,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    )
                    Text(
                        text = movie.overview,
                        style = TextStyle(
                            fontSize = 20.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    )
                    movie.genres?.map { it.name }?.let { genres ->
                        Text(
                            text = genres.joinToString(", "),
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onBackground,
                            ),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 12.dp)
                        )
                    }
                    Text(
                        text = "Runtime: ${movie.runtime.toString()}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    )
                    Text(
                        text = "Released: ${movie.releaseDate}",
                        style = TextStyle(
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 12.dp)
                    )
                }
            }
        })
}

@Preview
@Composable
private fun PreviewMovieDetailsContent() {
    MovieDetailsContent(movie = mockMovie, isSaved = false, onAction = {})
}