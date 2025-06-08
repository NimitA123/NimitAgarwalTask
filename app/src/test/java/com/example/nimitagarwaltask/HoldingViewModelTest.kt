package com.example.nimitagarwaltask

import com.example.nimitagarwaltask.data.local.HoldingsEntity
import com.example.nimitagarwaltask.domain.usecase.IHoldingsUseCase
import com.example.nimitagarwaltask.presentation.screen.holdings_screen.HoldingsViewModel
import com.example.nimitagarwaltask.utils.UiState
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@ExperimentalCoroutinesApi
class HoldingsViewModelTest {

    private lateinit var viewModel: HoldingsViewModel
    private lateinit var useCase: IHoldingsUseCase

    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        useCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchHoldings emits Loading then Success`() = runTest {
        // Sample fake data
        // Sample fake data
        val fakeHoldings = listOf(
            HoldingsEntity(
                symbol = "HDFC",
                quantity = 10,
                avgPrice = 150.0,
                ltp = 155.0,
                close = 148.0
            ),
            HoldingsEntity(
                symbol = "Airtel",
                quantity = 5,
                avgPrice = 2800.0,
                ltp = 2825.0,
                close = 2790.0
            ),
            HoldingsEntity(
                symbol = "IDEA",
                quantity = 30,
                avgPrice = 2000.0,
                ltp = 2830.0,
                close = 2730.0
            )
        )


        coEvery { useCase.fetchHoldings() } returns flow {
            emit(UiState.Loading)
            emit(UiState.Success(fakeHoldings))
        }

        viewModel = HoldingsViewModel(useCase)

        val states = mutableListOf<UiState<List<HoldingsEntity>>>()
        val job = launch {
            viewModel.uiState.toList(states)
        }

        viewModel.fetchHoldings()

        advanceUntilIdle()

        assert(states[0] is UiState.Loading)
        assert(states[1] is UiState.Success && (states[1] as UiState.Success).data == fakeHoldings)

        job.cancel()
    }

    @Test
    fun `fetchHoldings emits Loading then Error`() = runTest {
        coEvery { useCase.fetchHoldings() } returns flow {
            emit(UiState.Loading)
            emit(UiState.Error("Something went wrong"))
        }

        viewModel = HoldingsViewModel(useCase)

        val states = mutableListOf<UiState<List<HoldingsEntity>>>()
        val job = launch {
            viewModel.uiState.toList(states)
        }

        viewModel.fetchHoldings()
        advanceUntilIdle()
        assert(states[0] is UiState.Loading)
        assert(states[1] is UiState.Error && (states[1] as UiState.Error).message == "Something went wrong")
        job.cancel()
    }
}
