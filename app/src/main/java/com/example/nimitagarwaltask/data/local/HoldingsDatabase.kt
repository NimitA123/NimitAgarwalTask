package com.example.nimitagarwaltask.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [HoldingsEntity::class], version = 1, exportSchema = false)
abstract class HoldingsDatabase : RoomDatabase() {
    abstract fun holdingsDao(): HoldingsDao
}