package com.example.donkey_code_challenge.destinations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.donkey_code_challenge.R
import com.example.donkey_code_challenge.destinations.destinations.SearchPageDestination
import com.example.donkey_code_challenge.model.Hub
import com.example.donkey_code_challenge.util.distanceTo
import com.example.donkey_code_challenge.util.observeWithLifecycle
import com.example.donkey_code_challenge.viewModels.MapViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import com.ramcosta.composedestinations.result.NavResult
import com.ramcosta.composedestinations.result.ResultRecipient

@RootNavGraph(start = true)
@Destination
@Composable
fun MapPage(
    navigator: DestinationsNavigator,
    resultRecipient: ResultRecipient<SearchPageDestination, Hub>
) {
    val vm: MapViewModel = hiltViewModel()

    vm.navigationEvent.observeWithLifecycle(action = { navigator.navigate(SearchPageDestination) })

    val hubs by vm.nearbyHubs.collectAsState()

    val copenhagen = LatLng(55.7, 12.5)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(copenhagen, 11f)
    }
    resultRecipient.onNavResult(listener = { result ->
        when (result) {
            NavResult.Canceled -> {}
            is NavResult.Value -> cameraPositionState.move(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        result.value.latitude.toDouble(),
                        result.value.longitude.toDouble()
                    ), 20f
                )
            )
        }
    })

    var latestPosition by remember {
        mutableStateOf(LatLng(0.0, 0.0))
    }

    if (!cameraPositionState.isMoving && cameraPositionState.position.target != latestPosition) {
        LaunchedEffect(key1 = cameraPositionState.position.target, block = {
            vm.onLocationChanges(
                cameraPositionState.position.target,
                cameraPositionState.projection?.visibleRegion?.latLngBounds?.let {
                    LatLng(
                        it.northeast.latitude,
                        latestPosition.longitude
                    ).distanceTo(LatLng(it.southwest.latitude, latestPosition.longitude)) / 2
                } ?: 5000.0
            )
        })
        latestPosition = cameraPositionState.position.target
    }


    Box(modifier = Modifier.fillMaxSize()) {

        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState
        ) {
            hubs.forEach {
                val position = LatLng(
                    it.latitude.toDouble(),
                    it.longitude.toDouble()
                )
                if (cameraPositionState.projection?.visibleRegion?.latLngBounds?.contains(position) == true) {
                    Marker(
                        state = MarkerState(
                            position = position
                        )
                    )
                }
            }
        }

        Button(
            onClick = { vm.searchButtonOnClick() },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(bottom = 60.dp, start = 30.dp, end = 60.dp)
        ) {
            Text(text = stringResource(R.string.button_search))
        }
    }
}