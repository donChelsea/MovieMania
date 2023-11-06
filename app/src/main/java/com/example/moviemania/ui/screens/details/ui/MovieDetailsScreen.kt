@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moviemania.ui.screens.details.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.moviemania.R
import com.example.moviemania.ui.common.MovieManiaLargeCard
import com.example.moviemania.ui.common.MovieManiaNowPlayingCard
import com.example.moviemania.ui.screens.details.DetailsUiAction
import com.example.moviemania.ui.screens.details.DetailsUiEvent
import com.example.moviemania.ui.screens.details.DetailsUiState
import com.example.moviemania.ui.screens.details.MovieDetailsViewModel
import com.example.moviemania.util.mockMovie

@Composable
fun MovieDetailsScreen(
    viewModel: MovieDetailsViewModel,
    navController: NavHostController,
) {
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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.movie_details))
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(),
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
                state.movie?.let { movie ->
                    MovieManiaLargeCard(movie = movie)
                    Text(
                        text = movie.title,
                        fontSize = dimensionResource(id = R.dimen.details_title_text_size).value.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = movie.tagline,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    state.movie.genres?.map { it.name }?.let { genres ->
                        Text(
                            text = genres.joinToString(", "),
                            fontSize = 12.sp,
                            color = Color.Black,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                    Text(
                        text = state.movie.runtime.toString(),
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = state.movie.releaseDate,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Text(
                        text = state.movie.overview,
                        fontSize = 12.sp,
                        color = Color.Black,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        })
}

@Preview
@Composable
private fun PreviewMovieDetailsLayout() {
    MovieDetailsLayout(state = DetailsUiState(movie = mockMovie), onAction = {})
}