package com.example.donkey_code_challenge.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.donkey_code_challenge.destinations.destinations.SearchPageDestination
import com.example.donkey_code_challenge.model.Hub
import com.example.donkey_code_challenge.repositories.HubRepository
import com.google.android.gms.maps.model.LatLng
import com.ramcosta.composedestinations.spec.Direction
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.roundToInt

@HiltViewModel
class MapViewModel @Inject constructor(private val hubRepository: HubRepository) : ViewModel() {

    private val _navigationEvent = MutableSharedFlow<Direction>()
    val navigationEvent = _navigationEvent.asSharedFlow()

    private val _nearbyHubs = MutableStateFlow(listOf<Hub>())
    val nearbyHubs = _nearbyHubs.asStateFlow()

    fun searchButtonOnClick() {
        viewModelScope.launch { _navigationEvent.emit(SearchPageDestination) }
    }

    fun onLocationChanges(location: LatLng, radius: Double) {
        viewModelScope.launch {
            _nearbyHubs.emit(
                if (radius <= 25000) {
                    hubRepository.getNearby(location, radius.roundToInt())
                } else {
                    listOf()
                }
            )
        }
    }
}