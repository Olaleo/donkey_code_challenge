package com.example.donkey_code_challenge.util

import android.location.Location
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@Composable
inline fun <reified T> Flow<T>.observeWithLifecycle(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    noinline action: suspend (T) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        lifecycleOwner.lifecycleScope.launch {
            flowWithLifecycle(
                lifecycleOwner.lifecycle,
                minActiveState
            ).collect { action(it) }
        }
    }
}

fun LatLng.distanceTo(other: LatLng): Double {
    val startPoint = Location("locationA")
    startPoint.setLatitude(latitude)
    startPoint.setLongitude(longitude)

    val endPoint = Location("locationA")
    endPoint.setLatitude(other.latitude)
    endPoint.setLongitude(other.longitude)

   return startPoint.distanceTo(endPoint).toDouble()
}