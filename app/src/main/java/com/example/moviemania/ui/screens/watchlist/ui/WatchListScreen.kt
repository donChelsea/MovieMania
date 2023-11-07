@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.moviemania.ui.screens.watchlist.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import com.example.moviemania.R
import androidx.compose.ui.Modifier
import com.example.moviemania.ui.common.MovieManiaWatchListCard
import com.example.moviemania.ui.navigation.Screen
import com.example.moviemania.ui.screens.empty.ErrorScreen
import com.example.moviemania.ui.screens.empty.LoadingScreen
import com.example.moviemania.ui.screens.watchlist.WatchListUiAction
import com.example.moviemania.ui.screens.watchlist.WatchListUiEvent
import com.example.moviemania.ui.screens.watchlist.WatchListUiState
import com.example.moviemania.ui.screens.watchlist.WatchListViewModel

@Composable
fun WatchListScreen(
    viewModel: WatchListViewModel,
    navController: NavController,
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(key1 = true) {
        viewModel.events.collect { event ->
            when (event) {
                WatchListUiEvent.OnNavigateBack -> navController.popBackStack()
                is WatchListUiEvent.OnMovieClicked -> navController.navigate(Screen.MovieDetails.withArgs(event.movieId))
            }
        }
    }

    if (state.isLoading) {
        LoadingScreen()
    } else if (state.isError) {
        ErrorScreen()
    } else {
        WatchListLayout(
            state = state,
            onAction = viewModel::handleAction
        )
    }
}

@Composable
fun WatchListLayout(
    state: WatchListUiState,
    onAction: (WatchListUiAction) -> Unit,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.watch_list))
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(),
                actions = {
                    IconButton(onClick = {  }) {
                        Icon(Icons.Filled.FavoriteBorder, stringResource(id = R.string.content_description_go_to_watch_list))
                    }
                },
                navigationIcon = {
                    IconButton(onClick = { onAction(WatchListUiAction.OnNavigateBack) }) {
                        Icon(Icons.Filled.ArrowBack, null)
                    }
                }
            )
        }, content = { paddingValues ->
            LazyColumn(modifier = Modifier.padding(paddingValues)) {
                items(state.movies) { movie ->
                    MovieManiaWatchListCard(
                        movie = movie,
                        onClick = { onAction(WatchListUiAction.OnMovieClicked(it))}
                    )
                }
            }
        })
}