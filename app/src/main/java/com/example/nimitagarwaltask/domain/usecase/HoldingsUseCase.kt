package com.example.nimitagarwaltask.domain.usecase

import com.example.nimitagarwaltask.data.local.HoldingsEntity
import com.example.nimitagarwaltask.domain.repository.HoldingsRepository
import com.example.nimitagarwaltask.utils.UiState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class HoldingsUseCase @Inject constructor(
    private val repository: HoldingsRepository
) : IHoldingsUseCase {

    override fun fetchHoldings(): Flow<UiState<List<HoldingsEntity>>> = flow {
        emit(UiState.Loading)
        try {
            repository.fetchHoldings().collect { holdingsList ->
                emit(UiState.Success(holdingsList))
            }
        } catch (e: Exception) {
            emit(UiState.Error(e.localizedMessage ?: "Unknown Error"))
        }
    }
}