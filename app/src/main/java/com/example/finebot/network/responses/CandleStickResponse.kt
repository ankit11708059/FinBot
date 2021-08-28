package com.example.finebot.network.responses

import com.example.finebot.network.model.CoinDTO
import com.google.gson.annotations.SerializedName


class CandleStickResponse {

    @SerializedName(value ="e")
    val kline : Kline?=null
}