package com.aa.harcamalarabt.service

import com.aa.harcamalarabt.model.CurrencyModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {

    // http://api.exchangeratesapi.io/v1/latest?access_key=372e9e1be15ed70c7237bfb645b7b6bd&symbols=USD,GBP,TRY,EUR&format=1
    // http://api.exchangeratesapi.io/v1/latest?access_key=3c11d17928061ca36f82a2d5dd7728f5&symbols=USD,TRY,GBP,JPY&format=1

    @GET("v1/latest")
    fun getData(
        @Query("access_key") key: String = "3947a0d206834012db9e2c3a7a973d41",
        @Query("symbols") symbols: String = "USD,TRY,GBP,AZN",
        @Query("format") format: String = "1"
    ) : Single<CurrencyModel>

}