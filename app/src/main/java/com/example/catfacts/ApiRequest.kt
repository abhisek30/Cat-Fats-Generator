package com.example.catfacts

import com.example.catfacts.api.CatJson
import retrofit2.Call
import retrofit2.http.GET

interface ApiRequest {
    @GET("/facts/random")
    fun getCatFacts(): Call<CatJson>
}