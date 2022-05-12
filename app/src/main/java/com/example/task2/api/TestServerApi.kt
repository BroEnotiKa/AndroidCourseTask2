package com.example.task2.api

import com.example.task2.models.HabitData
import com.google.gson.GsonBuilder
import com.google.gson.internal.LinkedTreeMap
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface TestServerApi {
    @GET("api/habit")
    suspend fun getAllHabits(@Header("Authorization") token: String): ArrayList<HabitData>

    @PUT("api/habit")
    suspend fun putHabit(@Header("Authorization") token: String, @Body habit: HabitData): LinkedTreeMap<String, String>

    @HTTP(method = "DELETE", path = "api/habit", hasBody = true)
    suspend fun deleteHabit(@Header("Authorization") token: String, @Body uid: LinkedTreeMap<String, String>)

    companion object Factory {
        private const val MAX_TRIES = 3

        fun create(): TestServerApi {
            val okHttpClient = OkHttpClient().newBuilder()
                .addInterceptor { chain ->
                    val request: Request = chain.request()
                    var response = chain.proceed(request)
                    var tries = 0
                    while (!(response.isSuccessful) && tries < MAX_TRIES) {
                        try {
                            response = chain.proceed(request)
                        } finally {
                            tries++
                        }
                    }
                    response
                }
                .build()

            val gson = GsonBuilder()
                .registerTypeAdapter(HabitData::class.java, HabitDataTypeAdapter())
                .create()

            val retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .baseUrl("https://droid-test-server.doubletapp.ru/")
                .build()

            return retrofit.create(TestServerApi::class.java)
        }
    }
}