package com.example.nimitagarwaltask

import app.cash.turbine.test
import com.example.nimitagarwaltask.data.local.HoldingsEntity
import com.example.nimitagarwaltask.domain.repository.HoldingsRepository
import com.example.nimitagarwaltask.domain.usecase.HoldingsUseCase
import com.example.nimitagarwaltask.utils.UiState
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsUseCaseTest {

    private lateinit var useCase: HoldingsUseCase
    private val repository: HoldingsRepository = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        useCase = HoldingsUseCase(repository)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchHoldings emits Loading then Success when repository returns data`() = runTest {

        val fakeHoldings = listOf(
            HoldingsEntity(symbol = "HDFC", quantity = 10, avgPrice = 100.0, ltp = 150.0, close = 148.0),
            HoldingsEntity(symbol = "Reliance", quantity = 5, avgPrice = 1200.0, ltp = 1300.0, close = 1295.0)
        )
        coEvery { repository.fetchHoldings() } returns flow { emit(fakeHoldings) }


        useCase.fetchHoldings().test {
            assertThat(awaitItem()).isInstanceOf(UiState.Loading::class.java)
            val successState = awaitItem()
            assertThat(successState).isInstanceOf(UiState.Success::class.java)
            val data = (successState as UiState.Success).data
            assertThat(data).isEqualTo(fakeHoldings)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchHoldings emits Loading then Error when repository throws exception`() = runTest {

        coEvery { repository.fetchHoldings() } returns flow {
            throw IOException("No internet")
        }


        useCase.fetchHoldings().test {
            assertThat(awaitItem()).isInstanceOf(UiState.Loading::class.java)
            val errorState = awaitItem()
            assertThat(errorState).isInstanceOf(UiState.Error::class.java)
            val message = (errorState as UiState.Error).message
            assertThat(message).contains("No internet")
            cancelAndIgnoreRemainingEvents()
        }
    }
}
