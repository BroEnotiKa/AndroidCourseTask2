package com.example.data

import com.google.gson.internal.LinkedTreeMap
import retrofit2.http.*

interface TestServerApi {
    @GET("api/habit")
    suspend fun getAllHabits(): ArrayList<HabitDataDto>

    @PUT("api/habit")
    suspend fun putHabit(@Body habit: HabitDataDto): LinkedTreeMap<String, String>

    @HTTP(method = "DELETE", path = "api/habit", hasBody = true)
    suspend fun deleteHabit(@Body uid: LinkedTreeMap<String, String>)

    @POST("api/habit_done")
    suspend fun doneHabit(@Body uidWithDate: LinkedTreeMap<String, Any>)
}