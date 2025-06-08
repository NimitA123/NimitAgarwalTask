package com.example.nimitagarwaltask.domain.repository

import com.example.nimitagarwaltask.data.local.HoldingsEntity
import kotlinx.coroutines.flow.Flow

interface HoldingsRepository {
    suspend fun fetchHoldings(): Flow<List<HoldingsEntity>>
}


