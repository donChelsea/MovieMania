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
import androidx.compose.material.icons.filled.FavoriteBorder
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
import androidx.navigation.NavHostController
import com.example.moviemania.R
import com.example.moviemania.ui.common.MovieManiaCarousel
import com.example.moviemania.ui.common.MovieManiaSmallCard
import com.example.moviemania.ui.common.MovieManiaPager
import com.example.moviemania.ui.common.SectionContainer
import com.example.moviemania.ui.navigation.Screen
import com.example.moviemania.ui.screens.empty.ErrorScreen
import com.example.moviemania.ui.screens.empty.LoadingScreen
import com.example.moviemania.ui.screens.home.HomeUiAction
import com.example.moviemania.ui.screens.home.HomeUiEvent
import com.example.moviemania.ui.screens.home.HomeUiState
import com.example.moviemania.ui.screens.home.HomeViewModel

@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    navController: NavHostController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                HomeUiEvent.OnWatchListClicked -> navController.navigate(Screen.WatchList.route)
                is HomeUiEvent.OnMovieClicked -> navController.navigate(Screen.MovieDetails.withArgs(event.movieId))
            }
        }
    }

    if (state.isLoading) {
        LoadingScreen()
    } else if (state.isError) {
        ErrorScreen()
    } else {
        HomeLayout(
            state = state,
            onAction = viewModel::handleAction,
        )
    }
}

@Composable
fun HomeLayout(
    state: HomeUiState,
    onAction: (HomeUiAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.home))
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(),
                actions = {
                    IconButton(onClick = { onAction(HomeUiAction.OnWatchListClicked) }) {
                        Icon(Icons.Filled.FavoriteBorder, stringResource(id = R.string.content_description_go_to_watch_list))
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
                    MovieManiaPager(data = state.nowPlaying) { onAction(HomeUiAction.OnMovieClicked(it)) }
                }
                Spacer(modifier = Modifier.height(12.dp))
                SectionContainer(title = stringResource(id = R.string.trending)) {
                    MovieManiaCarousel {
                        items(state.trending) { movie ->
                            MovieManiaSmallCard(movie = movie) {
                                onAction(HomeUiAction.OnMovieClicked(it))
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                SectionContainer(title = stringResource(id = R.string.upcoming)) {
                    MovieManiaCarousel {
                        items(state.upcoming) { movie ->
                            MovieManiaSmallCard(movie = movie) {
                                onAction(HomeUiAction.OnMovieClicked(it))
                            }
                        }
                    }
                }
            }
        })
}