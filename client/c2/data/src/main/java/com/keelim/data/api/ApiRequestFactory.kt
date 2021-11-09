package com.keelim.data.api

import com.mocklets.pluto.PlutoInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import javax.inject.Inject
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create

class ApiRequestFactory @Inject constructor(
    tokenUtil: TokenUtil
) {
    private val baseUrl = "http://k5b201.p.ssafy.io:4000"
    private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    val retrofit: FreeServices = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .client(
            OkHttpClient.Builder()
                .addInterceptor(
                    HttpLoggingInterceptor().apply {
                        this.level = HttpLoggingInterceptor.Level.HEADERS
                    }
                )
                .addInterceptor(
                    PlutoInterceptor()
                )
                .addInterceptor(AuthInterceptor(tokenUtil.provideToken()!!))
                .build()
        )
        .build()
        .create()

    class AuthInterceptor(private val token:String): Interceptor{
        override fun intercept(chain: Interceptor.Chain): Response  = with(chain){
            val newRequest = request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            proceed(newRequest)
        }
    }
}
