package com.aa.harcamalarabt.model


import com.google.gson.annotations.SerializedName

data class CurrencyModel(
    val base: String,
    val rates: Rates
)