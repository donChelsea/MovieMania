package com.example.moviemania.ui.screens.home

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.example.moviemania.domain.repository.MovieRepository
import com.example.moviemania.ui.common.BaseViewModel
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
) : BaseViewModel<HomeUiState, HomeUiEvent, HomeUiAction>() {
    private val _state = MutableStateFlow(HomeUiState())

    override val state: StateFlow<HomeUiState>
        get() = _state.asStateFlow()

    init {
        initData()
    }

    override fun handleAction(action: HomeUiAction) {
        when (action) {
            is HomeUiAction.OnMovieClicked -> emitUiEvent(HomeUiEvent.OnMovieClicked(action.movieId))
        }
    }

    private fun initData() {
        safeLaunch {
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
                        trending = trending.data?.map { it.toUiModel() }.orEmpty(),
                        nowPlaying = nowPlaying.data?.map { it.toUiModel() }.orEmpty(),
                        upcoming = upcoming.data?.map { it.toUiModel() }.orEmpty(),
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
