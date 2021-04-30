package com.aa.harcamalarabt.service

import com.aa.harcamalarabt.model.CurrencyModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {

    // http://api.exchangeratesapi.io/v1/latest?access_key=ee06c56b8fe3ba6df11d71a9e587de5d&symbols=USD,TRY,GBP,AZN&format=1

    @GET("latest")
    fun getData(
        @Query("access_key") key: String = "d99d6d911a86a0d5814d7917def91d4d",
        @Query("symbols") symbols: String = "USD,TRY,GBP,EUR",
        @Query("format") format: String = "1"
    ) : Single<CurrencyModel>

}