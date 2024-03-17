package com.ulas.kotlincurrencyapp.service

import com.ulas.kotlincurrencyapp.model.CryptoModel
import retrofit2.Response
import retrofit2.http.GET

interface CryptoAPI {
    //fca_live_DFAtnqo7ynbLaUK7UQIwvdfG7UjzFqT9BLPrfFh5
    //https://api.freecurrencyapi.com/v1/
    // latest?apikey=fca_live_DFAtnqo7ynbLaUK7UQIwvdfG7UjzFqT9BLPrfFh5
    @GET("latest?apikey=fca_live_DFAtnqo7ynbLaUK7UQIwvdfG7UjzFqT9BLPrfFh5")
     suspend fun getData(): Response<CryptoModel>
}