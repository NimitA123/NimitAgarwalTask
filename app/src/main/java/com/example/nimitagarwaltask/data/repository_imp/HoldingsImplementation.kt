package com.example.nimitagarwaltask.data.repository_imp


import android.util.Log
import com.example.nimitagarwaltask.data.local.HoldingsDao
import com.example.nimitagarwaltask.data.local.HoldingsEntity
import com.example.nimitagarwaltask.data.local.toEntity
import com.example.nimitagarwaltask.data.remote.HoldingsApiService
import com.example.nimitagarwaltask.domain.repository.HoldingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class HoldingsRepositoryImpl @Inject constructor(
    private val apiService: HoldingsApiService,
    private val dao: HoldingsDao
) : HoldingsRepository {
    override suspend fun fetchHoldings(): Flow<List<HoldingsEntity>> = flow {
        try {
            val response = apiService.getHoldings()
            if (response.isSuccessful && response.body() != null) {
                val userHoldings = response.body()?.data?.userHolding.orEmpty().filterNotNull()
                val entityList = userHoldings.map { it.toEntity() }
                dao.insertHoldings(entityList)
            }
        } catch (e: IOException) {
            Log.e("NetworkError", "No internet connection: ${e.localizedMessage}")
        } catch (e: Exception) {
            Log.e("GeneralError", "Something went wrong: ${e.localizedMessage}")
        }

        emitAll(dao.getAllHoldings())
    }
}

