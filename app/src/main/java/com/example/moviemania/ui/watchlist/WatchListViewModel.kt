package com.example.moviemania.ui.watchlist

import androidx.lifecycle.viewModelScope
import com.example.moviemania.domain.repository.MovieRepository
import com.example.moviemania.util.MovieManiaViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchListViewModel @Inject constructor(
    private val repository: MovieRepository,
) : MovieManiaViewModel<WatchListUiState, WatchListUiEvent, WatchListUiAction>() {
    private val _state = MutableStateFlow(WatchListUiState())
    override val state: StateFlow<WatchListUiState>
        get() = _state.asStateFlow()

    init {
        initData()
    }

    override fun handleAction(action: WatchListUiAction) {
        when (action) {
            WatchListUiAction.OnNavigateBack -> viewModelScope.launch { _events.emit(WatchListUiEvent.OnNavigateBack) }
        }
    }

    private fun initData() {
        viewModelScope.launch {
//            repository.getNowPlaying().collectLatest { results ->
//                when (results) {
//                    is Resource.Success -> {
//                        _state.update { it.copy(nowPlaying = results.data.orEmpty()) }
//                        Log.e("mm_test", "HomeViewModel: getNowPlaying: ${_state.value.nowPlaying.toString()}")
//                    }
//
//                    is Resource.Error -> {
//                        Log.e("mm_test", "HomeViewModel: getNowPlaying: ${results.message.toString()}")
//                    }
//
//                    is Resource.Loading -> _state.update { it.copy(isLoading = true) }
//                }
//            }
        }
    }
}
