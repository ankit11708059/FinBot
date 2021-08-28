package com.example.finebot.network

import com.example.finebot.model.Candlestick
import com.example.finebot.network.responses.ExchangeInfo
import retrofit2.http.GET
import retrofit2.http.Query

interface BinanceService {

    @GET("api/v3/exchangeInfo")
    suspend fun getExchangeInfo() : ExchangeInfo

  /*  @GET("api/v3/klines")
    suspend fun get1MinKline(
    @Query("symbol") symbol: String,
    @Query("interval") interval: CandlestickInterval,
    @Query("limit") limit: Int? = null,
    @Query("startTime") startTime: Long? = null,
    @Query("endTime") endTime: Long? = null) : List<Candlestick>*/

}