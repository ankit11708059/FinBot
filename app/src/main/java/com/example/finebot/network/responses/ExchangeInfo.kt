package com.example.finebot.network.responses

import com.google.gson.annotations.SerializedName

class ExchangeInfo {
    @SerializedName("timezone")
    val timeZone : String?=null

    @SerializedName(value = "symbols")
    val symbols : List<Symbol> = listOf()
}