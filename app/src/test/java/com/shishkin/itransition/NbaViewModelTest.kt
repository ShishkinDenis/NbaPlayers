package com.shishkin.itransition

import com.google.common.truth.Truth.assertThat
import com.shishkin.itransition.gui.nba.NbaViewModel
import com.shishkin.itransition.gui.nba.mappers.PlayerWithTeamToNbaPlayerUiMapper
import com.shishkin.itransition.gui.nba.uientities.NbaPlayerUi
import com.shishkin.itransition.network.entities.KResult
import com.shishkin.itransition.network.entities.ResultState
import com.shishkin.itransition.repository.NbaRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class NbaViewModelTest {

    @Mock
    lateinit var nbaRepository: NbaRepository

    private lateinit var viewModel: NbaViewModel

    private lateinit var testContextProvider: TestContextProvider

    @Before
    fun setUp() {
        testContextProvider = TestContextProvider()
        viewModel =
            NbaViewModel(nbaRepository, PlayerWithTeamToNbaPlayerUiMapper(), testContextProvider)
    }

    @Test
    fun getNbaPlayersListIsCalled() = runBlockingTest {
        verify(nbaRepository).getNbaPlayersList()
    }

    @Test
    fun playersStateEmitsSuccess() = runBlockingTest {
        whenever(nbaRepository.getNbaPlayersList()).thenReturn(flowOf(KResult.success(listOf())))
        viewModel.loadPlayers()
        assertThat(viewModel.playersState.value.status).isEqualTo(ResultState.success(Any()).status)
    }

    @Test
    fun playersStateEmitsFailure() = runBlockingTest {
        val error = NullPointerException()
        whenever(nbaRepository.getNbaPlayersList()).thenReturn(flowOf(KResult.failure(error)))
        viewModel.loadPlayers()
        assertThat(viewModel.playersState.value.status).isEqualTo(
            ResultState.error<List<NbaPlayerUi>>(error.message, error).status
        )
    }
}
