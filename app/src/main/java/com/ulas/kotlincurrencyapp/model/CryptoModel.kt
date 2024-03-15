package com.ulas.kotlincurrencyapp.model

import com.google.gson.annotations.SerializedName

data class CryptoModel(
    @SerializedName("data")
    val data: Map<String,Double>
)