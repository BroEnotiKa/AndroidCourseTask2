package com.example.presentation.app

import android.content.Context
import androidx.room.Room
import com.example.data.*
import com.example.domain.repository.HabitRepository
import com.example.domain.useCases.*
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class HabitsModule {
    companion object {
        private const val userToken = "<secret>"
    }

    @Provides
    fun provideAddOrUpdateHabitUseCase(habitRepository: HabitRepository): AddOrUpdateUseCase {
        return AddOrUpdateUseCase(habitRepository, Dispatchers.IO)
    }

    @Provides
    fun provideDeleteHabitUseCase(habitRepository: HabitRepository): DeleteHabitUseCase {
        return DeleteHabitUseCase(habitRepository, Dispatchers.IO)
    }

    @Provides
    fun provideDoneHabitUseCase(habitRepository: HabitRepository): DoneHabitUseCase {
        return DoneHabitUseCase(habitRepository, Dispatchers.IO)
    }

    @Provides
    fun provideGetHabitsUseCase(habitRepository: HabitRepository): GetHabitsUseCase {
        return GetHabitsUseCase(habitRepository)
    }

    @Provides
    fun provideHabitRepository(
        storage: HabitStorage,
        apiRepository: TestServerApiRepository
    ): HabitRepository {
        return HabitRepositoryImpl(storage, apiRepository)
    }

    @Singleton
    @Provides
    fun provideStorage(context: Context): HabitStorage {
        return Room.databaseBuilder(
            context,
            HabitStorage::class.java,
            "db"
        )
            .build()
    }

    @Provides
    fun provideSearchRepository(api: TestServerApi): TestServerApiRepository {
        return TestServerApiRepository(api)
    }

    @Provides
    fun provideHabitService(retrofit: Retrofit): TestServerApi {
        return retrofit.create(TestServerApi::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient().newBuilder()
            .addInterceptor { chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization", userToken)
                    .build()

                var response = chain.proceed(request)
                var tries = 0
                while (!response.isSuccessful && tries < 3) {
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
            .registerTypeAdapter(HabitDataDto::class.java, HabitDataTypeAdapter())
            .create()

        return Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl("https://droid-test-server.doubletapp.ru/")
            .build()
    }
}