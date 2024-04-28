package com.example.moviemania.ui.screens.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.moviemania.domain.repository.MovieRepository
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
class HomeViewModel @Inject constructor(
    private val repository: MovieRepository,
) : MovieManiaViewModel<HomeUiState, HomeUiEvent, HomeUiAction>() {
    private val _state = MutableStateFlow(HomeUiState())
    override val state: StateFlow<HomeUiState>
        get() = _state.asStateFlow()

    init {
        initData()
    }

    override fun handleAction(action: HomeUiAction) {
        when (action) {
            HomeUiAction.GoToWatchlist -> viewModelScope.launch { _events.emit(HomeUiEvent.GoToWatchlist) }
            is HomeUiAction.OnMovieClicked -> viewModelScope.launch { _events.emit(HomeUiEvent.OnMovieClicked(action.movieId)) }
        }
    }

    private fun initData() {
        viewModelScope.launch {
            combine(
                repository.getNowPlaying(),
                repository.getTrending(),
                repository.getUpcoming(),
            ) { nowPlaying, trending, upcoming ->
                if (
                    nowPlaying.data.orEmpty().isNotEmpty() &&
                    trending.data.orEmpty().isNotEmpty() &&
                    upcoming.data.orEmpty().isNotEmpty()
                ) {
                    ScreenData.Data(
                        trending = trending.data.orEmpty(),
                        nowPlaying = nowPlaying.data.orEmpty(),
                        upcoming = upcoming.data.orEmpty(),
                    )
                } else {
                    ScreenData.Loading
                }
            }
                .flowOn(Dispatchers.IO)
                .catch {t ->
                    Log.e(TAG, "Error: ${t.message.toString()}")
                    newUiState(ScreenData.Error)
                }
                .collectLatest { screenData ->
                    newUiState(screenData)
                }
        }
    }

    private fun newUiState(screenData: ScreenData) =
        _state.update { state -> state.copy(screenData = screenData) }

    companion object {
        const val TAG = "HomeViewModel"
    }
}
