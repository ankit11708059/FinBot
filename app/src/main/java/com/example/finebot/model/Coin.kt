package com.example.finebot.domain.model

import com.google.gson.annotations.SerializedName


data class Coin(
    var price : Double?=null,
    val name : String?=null
)