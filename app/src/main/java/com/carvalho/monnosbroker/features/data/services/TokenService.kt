package com.carvalho.monnosbroker.features.data.services

import com.carvalho.monnosbroker.features.data.models.RemoteResponse
import com.carvalho.monnosbroker.features.data.models.RemoteToken
import retrofit2.Response
import retrofit2.http.GET

interface TokenService {

    @GET("v2/market/tickers?allBase=BRL&marketType=CRYPTO")
    suspend fun getTokenList(): Response<RemoteResponse<RemoteToken>>
}