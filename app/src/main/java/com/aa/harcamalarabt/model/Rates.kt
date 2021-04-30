package com.aa.harcamalarabt.model

import com.google.gson.annotations.SerializedName

data class Rates(
    @SerializedName("GBP")
    val gBP: Double,
    @SerializedName("TRY")
    val tRY: Double,
    @SerializedName("USD")
    val uSD: Double,
    @SerializedName("EUR")
    val eUR: Double
)