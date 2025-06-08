package com.example.nimitagarwaltask.domain.usecase


import com.example.nimitagarwaltask.data.local.HoldingsEntity
import com.example.nimitagarwaltask.utils.UiState
import kotlinx.coroutines.flow.Flow

interface IHoldingsUseCase {
    fun fetchHoldings(): Flow<UiState<List<HoldingsEntity>>>
}
