package com.example.finebot.repository


import com.example.finebot.model.Candlestick
import com.example.finebot.network.responses.CandleStickResponse
import com.example.finebot.network.responses.ExchangeInfo
import com.example.finebot.network.responses.Kline
import com.example.finebot.network.responses.Symbol
import retrofit2.http.Body


interface CoinRepository {

    suspend fun getCoins(): ExchangeInfo

    //suspend fun get1MinKlines(symbol: String,candlestickInterval: CandlestickInterval) : List<Candlestick>
}