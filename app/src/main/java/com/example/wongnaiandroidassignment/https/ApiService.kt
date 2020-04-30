package com.example.wongnaiandroidassignment.https

import okhttp3.ResponseBody
import retrofit2.http.*


interface ApiService {


    @GET("v1/public/coins")
    abstract fun getCoins(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("prefix") prefix: String? = null,
        @Query("symbols") symbols: String? = null,
        @Query("slugs") slugs: String? = null,
        @Query("ids") ids: String? = null
    ): retrofit2.Call<ResponseBody>


}