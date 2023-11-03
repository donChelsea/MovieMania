@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.example.moviemania.ui.home.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.moviemania.R
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.ui.common.Carousel
import com.example.moviemania.ui.common.MovieManiaCarouselCard
import com.example.moviemania.ui.common.MovieManiaNowPlayingCard
import com.example.moviemania.ui.common.NestedVerticalGrid
import com.example.moviemania.ui.common.SectionContainer
import com.example.moviemania.ui.home.HomeUiAction
import com.example.moviemania.ui.home.HomeUiState
import com.example.moviemania.ui.home.HomeViewModel
import com.google.accompanist.pager.HorizontalPagerIndicator

@Composable
fun HomeScreen(viewModel: HomeViewModel) {
    val state by viewModel.state.collectAsState()

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
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.home))
                },
                colors = TopAppBarDefaults.smallTopAppBarColors(),
            )
        }, content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(paddingValues)
            ) {
                SectionContainer(title = stringResource(id = R.string.now_playing)) {
                    MovieManiaPager(data = state.nowPlaying)
                }
                Spacer(modifier = Modifier.height(12.dp))
                SectionContainer(title = stringResource(id = R.string.trending)) {
                    Carousel {
                        items(state.trending) { movie ->
                            MovieManiaCarouselCard(movie = movie, onClick = { })
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                SectionContainer(title = stringResource(id = R.string.upcoming)) {
                    Carousel {
                        items(state.upcoming) { movie ->
                            MovieManiaCarouselCard(movie = movie, onClick = { })
                        }
                    }
                }
                Spacer(modifier = Modifier.height(12.dp))
                SectionContainer(title = stringResource(id = R.string.genres)) {
                    NestedVerticalGrid(data = state.genres) {}
                }
            }
        })
}

@Composable
fun MovieManiaPager(
    data: List<Movie>,
) {
    val pagerState = rememberPagerState(pageCount = { data.size })
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.padding(bottom = 25.dp),
        ) { page ->
            MovieManiaNowPlayingCard(movie = data[page], onClick = { })
        }
        HorizontalPagerIndicator(
            modifier = Modifier
                .align(Alignment.BottomCenter),
            pageCount = data.size,
            pagerState = pagerState,
        )
    }
}