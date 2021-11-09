package com.keelim.data.api

import com.mocklets.pluto.PlutoInterceptor
import javax.inject.Inject
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class ApiRequestFactory @Inject constructor() {
    //  private val baseUrl = "http://i5b102.p.ssafy.io:8181/"
    private val baseUrl = ""

    val retrofit: FreeServices = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .addInterceptor(
                    PlutoInterceptor()
                )
                .build()
        )
        .build()
        .create()
}
