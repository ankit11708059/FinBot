package com.example.finebot.network.responses

import com.google.gson.annotations.SerializedName

data class Symbol(
    @SerializedName(value ="symbol")
    val symbol: String?=null
)