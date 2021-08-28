package com.example.finebot.repository

import com.example.finebot.model.Candlestick
import com.example.finebot.network.BinanceService

import com.example.finebot.network.model.CoinDTOMapper
import com.example.finebot.network.responses.ExchangeInfo
import com.example.finebot.network.responses.Symbol

class CoinRepository_Impl(private val binanceService: BinanceService,
                          private val coinDTOMapper: CoinDTOMapper
) : CoinRepository {

    override suspend fun getCoins(): ExchangeInfo {
      return binanceService.getExchangeInfo()
    }

   /* override suspend fun get1MinKlines(symbol: String,candlestickInterval: CandlestickInterval): List<Candlestick>{
        return binanceService.get1MinKline(symbol,candlestickInterval,limit = 1)
    }*/
}