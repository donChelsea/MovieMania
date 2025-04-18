package com.example.moviemania.ui.screens.home

import com.example.moviemania.data.ConnectionManager
import com.example.moviemania.domain.use_case.GetNowPlayingUseCase
import com.example.moviemania.domain.use_case.GetTrendingUseCase
import com.example.moviemania.domain.use_case.GetUpcomingUseCase
import com.example.moviemania.ui.common.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getNowPlayingUseCase: GetNowPlayingUseCase,
    private val getTrendingUseCase: GetTrendingUseCase,
    private val getUpcomingUseCase: GetUpcomingUseCase,
    private val connectionManager: ConnectionManager,
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
                connectionManager.isConnected,
                getNowPlayingUseCase(),
                getTrendingUseCase(),
                getUpcomingUseCase(),
            ) { isConnected, nowPlaying, trending, upcoming ->
                if (!isConnected) {
                    ScreenData.Offline
                } else if (
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
                .catch { t ->
                    newUiState(ScreenData.Error(t.message.orEmpty()))
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
