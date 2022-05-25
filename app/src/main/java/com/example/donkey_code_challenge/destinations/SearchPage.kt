package com.example.donkey_code_challenge.destinations

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.donkey_code_challenge.R
import com.example.donkey_code_challenge.model.Hub
import com.example.donkey_code_challenge.util.observeWithLifecycle
import com.example.donkey_code_challenge.viewModels.SearchViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.result.ResultBackNavigator

@Destination
@Composable
fun SearchPage(resultNavigator: ResultBackNavigator<Hub>) {
    val vm: SearchViewModel = hiltViewModel()

    vm.backNavigationEvent.observeWithLifecycle(action = resultNavigator::navigateBack)

    val searchQuery by vm.searchQuery.collectAsState()

    val searchResult by vm.searchResult.collectAsState()

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        OutlinedTextField(
            value = searchQuery,
            onValueChange = vm::onSearchQueryChanged,
            placeholder = { Text(text = stringResource(R.string.label_search_placeholder)) },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .padding(top = 20.dp)
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp),
            contentPadding = PaddingValues(vertical = 30.dp)
        ) {
            items(searchResult) {
                Row(
                    modifier = Modifier.clickable { vm.onHubClick(it) },
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = it.name, modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = null,
                        modifier = Modifier.padding(vertical = 10.dp)
                    )
                }
                Divider()
            }
        }
    }
}