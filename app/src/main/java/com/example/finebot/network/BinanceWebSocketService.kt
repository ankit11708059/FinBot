package com.example.finebot.network

import com.example.finebot.network.responses.CandleStickResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface BinanceWebSocketService {

    @POST
    suspend fun getPrices(@Body map:HashMap<Any,String>) : CandleStickResponse
}