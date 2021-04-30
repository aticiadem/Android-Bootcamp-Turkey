package com.aa.harcamalarabt.service

import com.aa.harcamalarabt.model.CurrencyModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {

    // http://api.exchangeratesapi.io/v1/latest?access_key=ee06c56b8fe3ba6df11d71a9e587de5d&symbols=USD,TRY,GBP,AZN&format=1

    @GET("latest")
    fun getData(
        @Query("access_key") key: String = "0d824cced891c9d8d2b815987f0196e6",
        @Query("symbols") symbols: String = "USD,TRY,GBP,EUR",
        @Query("format") format: String = "1"
    ) : Single<CurrencyModel>

}