package com.example.nimitagarwaltask

import app.cash.turbine.test
import com.example.nimitagarwaltask.data.local.HoldingsDao
import com.example.nimitagarwaltask.data.local.HoldingsEntity
import com.example.nimitagarwaltask.data.remote.HoldingsApiService
import com.example.nimitagarwaltask.data.repository_imp.HoldingsRepositoryImpl
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsRepositoryImplTest {

    private lateinit var repository: HoldingsRepositoryImpl
    private val dao: HoldingsDao = mockk()
    private val apiService : HoldingsApiService = mockk()

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        repository = HoldingsRepositoryImpl(apiService, dao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchHoldings returns data from DAO`() = runTest {
        val fakeHoldings = listOf(
            HoldingsEntity(symbol = "PNB BANK", quantity = 10, avgPrice = 100.0, ltp = 150.0, close = 148.0),
            HoldingsEntity(symbol = "UCK BANK", quantity = 5, avgPrice = 1200.0, ltp = 1300.0, close = 1295.0)
        )

        every { dao.getAllHoldings() } returns flowOf(fakeHoldings)

        repository.fetchHoldings().test {
            val items = awaitItem()
            assertThat(items).isEqualTo(fakeHoldings)
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchHoldings returns empty list when DAO has no data`() = runTest {
        every { dao.getAllHoldings() } returns flowOf(emptyList())

        repository.fetchHoldings().test {
            val items = awaitItem()
            assertThat(items).isEmpty()
            cancelAndIgnoreRemainingEvents()
        }
    }
}
