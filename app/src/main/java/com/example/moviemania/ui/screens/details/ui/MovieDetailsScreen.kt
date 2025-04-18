@file:OptIn(ExperimentalMaterialApi::class)

package com.example.moviemania.ui.screens.details.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.moviemania.R
import com.example.moviemania.util.mockMovieUiModel
import com.example.moviemania.ui.model.MovieUiModel
import com.example.moviemania.domain.model.VideoUiModel
import com.example.moviemania.ui.custom.bookmark.BookmarkButtonView
import com.example.moviemania.ui.custom.bookmark.BookmarkState
import com.example.moviemania.ui.custom.cards.DetailsCard
import com.example.moviemania.ui.custom.cards.YoutubePlayerCard
import com.example.moviemania.ui.custom.groups.CustomChipGroup
import com.example.moviemania.ui.custom.states.ShowError
import com.example.moviemania.ui.custom.states.ShowLoading
import com.example.moviemania.ui.screens.details.DetailsUiAction
import com.example.moviemania.ui.screens.details.DetailsUiState
import com.example.moviemania.ui.screens.details.MovieDetailsViewModel
import com.example.moviemania.ui.screens.details.ScreenData
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.seconds

@Composable
fun MovieDetailsScreen() {
    val viewModel: MovieDetailsViewModel = hiltViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

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
        is ScreenData.Data -> state.screenData.movieUiModel?.let {
            MovieDetailsContent(
                movieUiModel = it,
                trailer = state.screenData.trailer,
                bookmarkState = state.screenData.bookmarked,
                onAction = onAction
            )
        }
    }
}

@Composable
fun MovieDetailsContent(
    movieUiModel: MovieUiModel,
    trailer: VideoUiModel?,
    onAction: (DetailsUiAction) -> Unit,
    bookmarkState: BookmarkState,
) {
    var bookmarked by rememberSaveable { mutableStateOf(bookmarkState == BookmarkState.Bookmarked) }
    var toggling by rememberSaveable { mutableStateOf(false) }
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(rememberScrollState())
    ) {
        movieUiModel.let { movie ->
            Box {
                DetailsCard(movieUiModel = movie)

                BookmarkButtonView(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    state = when {
                        toggling -> BookmarkState.Toggling
                        bookmarked -> BookmarkState.Bookmarked
                        else -> BookmarkState.NotBookmarked
                    },
                    onBookmarkClicked = {
                        scope.launch {
                            toggling = true
                            delay(1.seconds)
                            onAction(DetailsUiAction.OnUpdateWatchLater(movie, bookmarked))
                            toggling = false
                            bookmarked = !bookmarked
                        }
                    }
                )
            }

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

            if (movie.tagline.isNotEmpty()) {
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

            Text(
                text = stringResource(id = R.string.trailers_and_more),
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp, start = 8.dp)
            )

            trailer?.let {
                YoutubePlayerCard(
                    videoUiModel = it,
                    lifecycleOwner = LocalLifecycleOwner.current
                )
            }

            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}

@Preview
@Composable
private fun PreviewMovieDetailsContent() {
    MovieDetailsContent(
        movieUiModel = mockMovieUiModel,
        trailer = VideoUiModel(
            id = "1",
            name = "name",
            type = "Trailer",
            site = "Youtube",
            key = ""
        ),
        onAction = {},
        bookmarkState = BookmarkState.NotBookmarked
    )
}