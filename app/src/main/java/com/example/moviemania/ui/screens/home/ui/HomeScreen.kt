@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moviemania.ui.screens.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ViewList
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.moviemania.R
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.ui.composables.MovieManiaCarousel
import com.example.moviemania.ui.composables.MovieManiaPager
import com.example.moviemania.ui.composables.MovieManiaSmallCard
import com.example.moviemania.ui.composables.SectionContainer
import com.example.moviemania.ui.navigation.Screen
import com.example.moviemania.ui.screens.empty.ErrorScreen
import com.example.moviemania.ui.screens.empty.LoadingScreen
import com.example.moviemania.ui.screens.home.HomeUiAction
import com.example.moviemania.ui.screens.home.HomeUiEvent
import com.example.moviemania.ui.screens.home.HomeUiState
import com.example.moviemania.ui.screens.home.HomeViewModel
import com.example.moviemania.ui.screens.home.ScreenData

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                HomeUiEvent.GoToWatchlist -> navController.navigate(Screen.WatchList.route)
                is HomeUiEvent.OnMovieClicked -> navController.navigate(Screen.MovieDetails.withArgs(event.movieId))
            }
        }
    }

    HomeLayout(
        state = state,
        onAction = viewModel::handleAction
    )
}

@Composable
fun HomeLayout(
    state: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
) {
    when (state.screenData) {
        ScreenData.Empty -> {}
        ScreenData.Error -> ErrorScreen()
        ScreenData.Loading -> LoadingScreen()
        ScreenData.Offline -> {}
        is ScreenData.Data -> HomeContent(
            trending = state.screenData.trending,
            nowPlaying = state.screenData.nowPlaying,
            upcoming = state.screenData.upcoming,
            onAction = onAction
        )
    }
}

@Composable
fun HomeContent(
    trending: List<Movie>,
    nowPlaying: List<Movie>,
    upcoming: List<Movie>,
    onAction: (HomeUiAction) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.home))
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(),
                actions = {
                    IconButton(onClick = { onAction(HomeUiAction.GoToWatchlist) }) {
                        Icon(Icons.Filled.ViewList, stringResource(id = R.string.content_description_go_to_watch_list))
                    }
                }
            )
        }, content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
            ) {
                SectionContainer(title = stringResource(id = R.string.now_playing)) {
                    MovieManiaPager(data = nowPlaying) { onAction(HomeUiAction.OnMovieClicked(it)) }
                }

                Spacer(modifier = Modifier.height(12.dp))

                SectionContainer(title = stringResource(id = R.string.trending)) {
                    MovieManiaCarousel {
                        items(trending) { movie ->
                            MovieManiaSmallCard(movie = movie) {
                                onAction(HomeUiAction.OnMovieClicked(it))
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                SectionContainer(title = stringResource(id = R.string.upcoming)) {
                    MovieManiaCarousel {
                        items(upcoming) { movie ->
                            MovieManiaSmallCard(movie = movie) {
                                onAction(HomeUiAction.OnMovieClicked(it))
                            }
                        }
                    }
                }
            }
        }
    )
}