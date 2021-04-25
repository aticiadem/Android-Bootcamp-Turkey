package com.aa.harcamalarabt.model


import com.google.gson.annotations.SerializedName

data class Rates(
    @SerializedName("EUR")
    val eUR: Int,
    @SerializedName("GBP")
    val gBP: Double,
    @SerializedName("TRY")
    val tRY: Double,
    @SerializedName("USD")
    val uSD: Double
)