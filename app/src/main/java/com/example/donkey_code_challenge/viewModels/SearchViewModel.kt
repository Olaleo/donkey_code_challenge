package com.example.donkey_code_challenge.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donkey_code_challenge.model.Hub
import com.example.donkey_code_challenge.repositories.SearchRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(searchRepository: SearchRepository) : ViewModel() {

    private val _backNavigationEvent = MutableSharedFlow<Hub>()
    val backNavigationEvent = _backNavigationEvent.asSharedFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    val searchResult = searchQuery.mapLatest {
        when (it) {
            "" -> listOf()
            else -> searchRepository.search(it)
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, listOf())

    fun onSearchQueryChanged(searchQuery: String) {
        viewModelScope.launch { _searchQuery.emit(searchQuery) }
    }

    fun onHubClick(hub: Hub) {
        viewModelScope.launch { _backNavigationEvent.emit(hub) }
    }

}