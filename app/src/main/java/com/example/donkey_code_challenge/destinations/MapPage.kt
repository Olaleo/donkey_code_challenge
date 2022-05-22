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
import androidx.compose.ui.unit.dp
import com.example.donkey_code_challenge.destinations.destinations.SearchPageDestination
import com.example.donkey_code_challenge.model.Hub
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

    var hub by remember {
        mutableStateOf<Hub?>(null)
    }

    resultRecipient.onNavResult(listener = { result ->
        hub = when (result) {
            NavResult.Canceled -> null
            is NavResult.Value -> result.value
        }
    })

    Box(modifier = Modifier.fillMaxSize()) {

        hub?.let { Text(text = it.name) }

        Button(
            onClick = { navigator.navigate(SearchPageDestination) },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 30.dp)
        ) {
            Text(text = "Search")
        }
    }
}