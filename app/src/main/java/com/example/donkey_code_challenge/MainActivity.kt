package com.example.donkey_code_challenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.donkey_code_challenge.destinations.NavGraphs
import com.example.donkey_code_challenge.destinations.SearchPage
import com.example.donkey_code_challenge.destinations.appDestination
import com.example.donkey_code_challenge.destinations.destinations.Destination
import com.example.donkey_code_challenge.destinations.destinations.MapPageDestination
import com.example.donkey_code_challenge.destinations.destinations.SearchPageDestination
import com.example.donkey_code_challenge.destinations.startAppDestination
import com.example.donkey_code_challenge.ui.theme.Donkey_code_challengeTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Donkey_code_challengeTheme {
                // A surface container using the 'background' color from the theme

                val navController = rememberNavController()
                val currentDestination by navController.currentBackStackEntryAsState()
                val destination = currentDestination?.appDestination()
                    ?: NavGraphs.root.startRoute.startAppDestination
                Scaffold(topBar = {

                    TopAppBar(
                        title = {
                            Text(
                                text = stringResource(
                                    destination.title
                                )
                            )
                        },
                        elevation = 0.dp,
                        modifier = Modifier,
                        navigationIcon = when (destination) {
                            MapPageDestination -> null
                            else -> {
                                {
                                    IconButton(onClick = {
                                        navController.popBackStack()
                                    }) {
                                        Icon(Icons.Default.ArrowBack, null)
                                    }
                                }
                            }
                        }
                    )
                }) {
                    DestinationsNavHost(
                        modifier = Modifier.padding(it),
                        navGraph = NavGraphs.root,
                        navController = navController
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    Donkey_code_challengeTheme() {
        Greeting("Android")
    }
}

@get:StringRes
val Destination.title
    get(): Int {
        return when (this) {
            MapPageDestination -> R.string.label_map_page_title
            SearchPageDestination -> R.string.label_search_page_title
        }
    }