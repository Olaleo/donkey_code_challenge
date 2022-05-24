package com.example.donkey_code_challenge.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.donkey_code_challenge.MainCoroutineRule
import com.example.donkey_code_challenge.model.Hub
import com.example.donkey_code_challenge.repositories.SearchRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class SearchViewModelTest {


    @get:Rule
    val rule = InstantTaskExecutorRule()

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()


    private lateinit var viewModel: SearchViewModel

    @MockK
    private lateinit var repository: SearchRepository

    @Before
    fun before() {

        MockKAnnotations.init(this, relaxed = true)
        viewModel = SearchViewModel(repository)
    }

    @Test
    fun searchViewModel_searchResultsStartsEmpty() {
        MainScope().launch {
            assertThat(viewModel.searchResult.value).isEmpty()
        }
    }

    @Test
    fun searchViewModel_emptySearchQuery_returnsEmptyList() = runTest {
        viewModel.onSearchQueryChanged("")

        val results = mutableListOf<List<Hub>>()

        val job = launch {
            viewModel.searchResult.toList(results)
        }

        viewModel.onSearchQueryChanged("")

        delay(1000)

        assertThat(results[0]).isEmpty()
        job.cancel()
    }

    @Test
    fun searchViewModel_nonEmptySearch_returnsNonEmptyList() = runTest {

        val list = listOf(
            Hub(
                -1,
                "",
                "",
                "",
                -1,
                -1,
                -1,
                -1,
                "",
                -1,
                -1,
                -1,
                -1,
                -1, -1, -1, -1, -1, -1,
            )
        )
        coEvery { repository.search("gade") }.returns(
            list
        )

        val results = mutableListOf<List<Hub>>()

        val job = launch {
            viewModel.searchResult.toList(results)
        }

        viewModel.onSearchQueryChanged("gade")

        delay(1000)

        assertThat(results[0]).isEmpty()
        assertThat(results[1]).isEqualTo(list)
        coVerify { repository.search("gade") }
        job.cancel()
    }

    @Test
    fun searchViewModel_hubOnClick_sendsBackNavigationEvent()  {

        val hub = Hub(
            -1,
            "",
            "",
            "",
            -1,
            -1,
            -1,
            -1,
            "",
            -1,
            -1,
            -1,
            -1,
            -1, -1, -1, -1, -1, -1,
        )

        val emittedBackNavigationEvents = mutableListOf<Hub>()

        runTest(UnconfinedTestDispatcher()) {
            val collectJob = launch {
                viewModel.backNavigationEvent.collect { emittedBackNavigationEvents.add(it) }
            }
            viewModel.onHubClick(hub)

            collectJob.cancel()
        }

        assertThat(emittedBackNavigationEvents).isEqualTo(listOf(hub))
    }
}
