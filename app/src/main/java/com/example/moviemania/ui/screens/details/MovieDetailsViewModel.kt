package com.example.moviemania.ui.screens.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.moviemania.domain.models.Video
import com.example.moviemania.domain.models.VideoItem
import com.example.moviemania.domain.repository.MovieRepository
import com.example.moviemania.domain.repository.WatchlistRepository
import com.example.moviemania.ui.navigation.Screen.MovieDetailArgs.MovieId
import com.example.moviemania.ui.MovieManiaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val watchlistRepository: WatchlistRepository,
    savedStateHandle: SavedStateHandle,
) : MovieManiaViewModel<DetailsUiState, DetailsUiEvent, DetailsUiAction>() {

    private val _state = MutableStateFlow(DetailsUiState())
    override val state: StateFlow<DetailsUiState>
        get() = _state.asStateFlow()

    private val movieId = savedStateHandle[MovieId] ?: ""
    private val currentlyPlayingIndex = MutableStateFlow<Int?>(null)
    private val videoItems = MutableStateFlow<List<VideoItem>>(emptyList())

    init {
        initData()
    }

    override fun handleAction(action: DetailsUiAction) {
        when (action) {
            DetailsUiAction.OnNavigateBack -> viewModelScope.launch { _events.emit(DetailsUiEvent.OnNavigateBack) }
            is DetailsUiAction.OnSaveToWatchlist -> {
                viewModelScope.launch {
                    watchlistRepository.getSavedMovies().collectLatest { saved ->
                        if (saved.isNotEmpty()) {
                            saved.forEach {
                                if (it.id == action.movie.id) {
                                    watchlistRepository.deleteMovie(action.movie)
                                    newUiState(ScreenData.Data(movie = action.movie, isSaved = false))
                                } else {
                                    watchlistRepository.saveMovie(action.movie)
                                    newUiState(ScreenData.Data(movie = action.movie, isSaved = true))
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun initData() {
        viewModelScope.launch {
            combine(
                movieRepository.getMovieDetails(movieId = movieId),
                movieRepository.getVideos(movieId = movieId)
            ) { details, videos ->
                if (details.data != null && videos.data?.isNotEmpty() == true) {
                    ScreenData.Data(movie = details.data, videos = videos.data)
                } else {
                    ScreenData.Loading
                }
            }
                .flowOn(Dispatchers.IO)
                .catch {
                    Log.e(TAG, it.message.toString())
                    newUiState(ScreenData.Error)
                }
                .collectLatest { screenData -> newUiState(screenData) }
        }
    }

    fun onPlayVideoClick(playbackPosition: Long, videoIndex: Int) {
        when (currentlyPlayingIndex.value) {
            null -> currentlyPlayingIndex.value = videoIndex
            videoIndex -> {
                currentlyPlayingIndex.value = null
                videoItems.value = videoItems.value.toMutableList().also { list ->
                    list[videoIndex] = list[videoIndex].copy(lastPlayedPosition = playbackPosition)
                }
            }

            else -> {
                videoItems.value = videoItems.value.toMutableList().also { list ->
                    list[currentlyPlayingIndex.value!!] = list[currentlyPlayingIndex.value!!].copy(lastPlayedPosition = playbackPosition)
                }
                currentlyPlayingIndex.value = videoIndex
            }
        }
    }

    private fun newUiState(screenData: ScreenData) =
        _state.update { state -> state.copy(screenData = screenData) }

    companion object {
        const val TAG = "MovieDetailsViewModel"
    }
}
