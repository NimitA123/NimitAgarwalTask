package com.example.nimitagarwaltask.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface HoldingsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHoldings(holdings: List<HoldingsEntity>)

    @Query("SELECT * FROM holdings")
    fun getAllHoldings(): Flow<List<HoldingsEntity>>
}
