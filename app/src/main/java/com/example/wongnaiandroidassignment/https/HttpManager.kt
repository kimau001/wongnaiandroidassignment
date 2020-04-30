package com.example.wongnaiandroidassignment.https

import com.example.wongnaiandroidassignment.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object HttpManager {
    var service: ApiService

    init {
        val gson = GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .create()

        val httpClient = OkHttpClient.Builder()
        httpClient.readTimeout(60, TimeUnit.SECONDS)
        httpClient.connectTimeout(60, TimeUnit.SECONDS)

        httpClient.addInterceptor { chain ->
            val original = chain.request()



            val request = original.newBuilder()
                    .method(original.method(), original.body())
                    .build()


            chain.proceed(request)
        }

        val client = httpClient.build()
        val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .baseUrl(BuildConfig.BASE_URL)
                .build()
        service = retrofit.create(ApiService::class.java)
    }
}