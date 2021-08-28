package com.example.finebot.network.responses

import com.google.gson.annotations.SerializedName

data class Kline(

    @SerializedName(value ="o")
    val open : String?=null
)