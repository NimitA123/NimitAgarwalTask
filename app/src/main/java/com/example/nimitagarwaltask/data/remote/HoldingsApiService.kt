package com.example.nimitagarwaltask.data.remote


import com.example.nimitagarwaltask.domain.model.HoldingsResponse
import com.example.nimitagarwaltask.utils.AppConstants
import retrofit2.Response
import retrofit2.http.GET

interface HoldingsApiService {
    @GET(AppConstants.HOLDINGS_ENDPOINT)
    suspend fun getHoldings(): Response<HoldingsResponse>

}

