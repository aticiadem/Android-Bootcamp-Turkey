package com.aa.harcamalarabt.service

import com.aa.harcamalarabt.model.CurrencyModel
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyAPI {

    // http://api.exchangeratesapi.io/v1/latest?access_key=372e9e1be15ed70c7237bfb645b7b6bd&symbols=USD,GBP,TRY,EUR&format=1
    // http://api.exchangeratesapi.io/v1/latest?access_key=f45a39bbedb3ea8bc1c9d7c4a13185f8&symbols=USD,AUD,CAD,PLN,MXN&format=1

    @GET("v1/latest")
    fun getData(
        @Query("access_key") key: String = "f45a39bbedb3ea8bc1c9d7c4a13185f8",
        @Query("symbols") symbols: String = "USD,TRY,GBP,AZN",
        @Query("format") format: String = "1"
    ) : Single<CurrencyModel>

}