package com.example.moviemania.ui.screens.home.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.moviemania.R
import com.example.moviemania.ui.custom.cards.CarouselCard
import com.example.moviemania.ui.custom.groups.Carousel
import com.example.moviemania.ui.custom.groups.CustomPager
import com.example.moviemania.ui.custom.groups.CustomSectionContainer
import com.example.moviemania.ui.custom.states.ShowError
import com.example.moviemania.ui.custom.states.ShowLoading
import com.example.moviemania.ui.custom.states.ShowOffline
import com.example.moviemania.ui.navigation.Screen
import com.example.moviemania.ui.screens.home.HomeUiAction
import com.example.moviemania.ui.screens.home.HomeUiEvent
import com.example.moviemania.ui.screens.home.HomeViewModel
import com.example.moviemania.ui.screens.home.ScreenData
import com.example.moviemania.ui.screens.home.localHomeUiState

@Composable
fun HomeScreen(
    navController: NavHostController,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(true) {
        viewModel.events.collect { event ->
            when (event) {
                is HomeUiEvent.OnMovieClicked -> navController.navigate(Screen.MovieDetails.withArgs(event.movieId))
            }
        }
    }

    CompositionLocalProvider(localHomeUiState provides state) {
        HomeLayout(
            onAction = viewModel::handleAction
        )
    }
}

@Composable
fun HomeLayout(
    onAction: (HomeUiAction) -> Unit,
) {
    when (val screenData = localHomeUiState.current.screenData) {
        ScreenData.Initial -> {}
        ScreenData.Offline -> ShowOffline()
        ScreenData.Loading -> ShowLoading()
        is ScreenData.Error -> ShowError(message = screenData.message)
        is ScreenData.Data -> HomeContent(onAction = onAction)
    }
}

@Composable
fun HomeContent(
    onAction: (HomeUiAction) -> Unit
) {
    val moviesLists = localHomeUiState.current.screenData as ScreenData.Data

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        CustomSectionContainer(title = stringResource(id = R.string.now_playing)) {
            CustomPager(data = moviesLists.nowPlaying) { onAction(HomeUiAction.OnMovieClicked(it)) }
        }

        Spacer(modifier = Modifier.height(12.dp))

        CustomSectionContainer(title = stringResource(id = R.string.trending)) {
            Carousel {
                items(moviesLists.trending) { movie ->
                    CarouselCard(movieUiModel = movie) {
                        onAction(HomeUiAction.OnMovieClicked(it))
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        CustomSectionContainer(title = stringResource(id = R.string.upcoming)) {
            Carousel {
                items(moviesLists.upcoming) { movie ->
                    CarouselCard(movieUiModel = movie) {
                        onAction(HomeUiAction.OnMovieClicked(it))
                    }
                }
            }
        }
    }
}