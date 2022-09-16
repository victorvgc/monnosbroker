package com.carvalho.monnosbroker.core.data.services

import com.carvalho.monnosbroker.core.data.models.RemoteCoin
import com.carvalho.monnosbroker.core.data.models.RemoteResponse
import retrofit2.Response
import retrofit2.http.GET

interface CoinService {

    @GET("v1/assets-details")
    suspend fun loadCoinsDetails(): Response<RemoteResponse<RemoteCoin>>
}