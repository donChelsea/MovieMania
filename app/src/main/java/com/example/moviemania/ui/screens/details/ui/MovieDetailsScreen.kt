@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)

package com.example.moviemania.ui.screens.details.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.BottomSheetState
import androidx.compose.material.BottomSheetValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.navigation.NavHostController
import com.example.moviemania.R
import com.example.moviemania.domain.models.Movie
import com.example.moviemania.domain.models.Video
import com.example.moviemania.domain.models.VideoItem
import com.example.moviemania.ui.composables.MovieManiaChipGroup
import com.example.moviemania.ui.composables.MovieManiaLargeCard
import com.example.moviemania.ui.composables.VideoCard
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
    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        topBar = {
            TopBar(
                movie = movie,
                onAction = onAction
            )
        },
        sheetShape = RoundedCornerShape(20.dp),
        sheetElevation = 50.dp,
        sheetPeekHeight = 250.dp,
        sheetContent = {
            //BottomSheetContent(videos)
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            movie.let { movie ->
                MovieManiaLargeCard(movie = movie)

                Text(
                    text = movie.title,
                    fontSize = 34.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp, start = 8.dp)
                )

                Text(
                    text = "${movie.releaseDate} • ${movie.runtime.toString()}",
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, bottom = 12.dp)
                )

                Text(
                    text = movie.tagline,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, bottom = 12.dp)
                )

                Text(
                    text = movie.overview,
                    fontSize = 16.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, bottom = 12.dp)
                )

                movie.genres?.map { it.name }?.let { genres ->
                    MovieManiaChipGroup(
                        data = genres,
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                }
            }
        }
    }
}


@Composable
fun BottomSheetContent(
    videos: List<Video>,
    currentlyPlayingIndex: Int
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black.copy(0.3f))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val context = LocalContext.current
        val exoPlayer = remember(context) { ExoPlayer.Builder(context).build() }
        val playingItemIndex by remember { mutableIntStateOf(currentlyPlayingIndex) }

        LaunchedEffect(playingItemIndex) {
            if (playingItemIndex == null) {
                exoPlayer.pause()
            } else {
                val video = videos[playingItemIndex]
                //exoPlayer.setMediaItem(MediaItem.fromUri(video.mediaUrl), video.lastPlayedPosition)
                exoPlayer.prepare()
                exoPlayer.playWhenReady = true
            }
        }

        Spacer(
            modifier = Modifier
                .height(3.dp)
                .width(70.dp)
                .background(Color.White)
        )

        Spacer(modifier = Modifier.height(20.dp))

        LazyColumn {
            itemsIndexed(items = videos, key = { _, video -> video.id }) { index, video ->
//                VideoCard(
//                    videoItem = video,
//                    exoPlayer = exoPlayer,
//                    isPlaying = index == playingItemIndex,
//                    onClick = {
//                        //viewModel.onPlayVideoClick(exoPlayer.currentPosition, index)
//                    }
//                )
            }
        }
    }
}

@Composable
private fun TopBar(
    onAction: (DetailsUiAction) -> Unit,
    movie: Movie,
) {
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
                    imageVector = Icons.Outlined.FavoriteBorder,
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
}

@Preview
@Composable
private fun PreviewMovieDetailsContent() {
    MovieDetailsContent(movie = mockMovie, isSaved = false, videos = emptyList(), onAction = {})
}