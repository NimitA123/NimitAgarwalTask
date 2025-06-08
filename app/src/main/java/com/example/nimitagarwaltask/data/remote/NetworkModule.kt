package com.example.nimitagarwaltask.data.remote


import android.content.Context
import androidx.room.Room
import com.example.nimitagarwaltask.data.local.HoldingsDao
import com.example.nimitagarwaltask.data.local.HoldingsDatabase
import com.example.nimitagarwaltask.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    // Provide SessionManager (for auth tokens, etc.)
    @Provides
    @Singleton
    fun provideSessionManager(@ApplicationContext context: Context): SessionManager {
        return SessionManager(context)
    }

    // Provide Logging interceptor for network logs
    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    // Provide Auth interceptor to add Authorization header if token exists
    @Provides
    @Singleton
    fun provideAuthInterceptor(sessionManager: SessionManager): Interceptor {
        return Interceptor { chain ->
            val request = chain.request()
            val token = sessionManager.getToken()

            val newRequest = if (!token.isNullOrEmpty()) {
                request.newBuilder()
                    .addHeader("Authorization", "Bearer $token")
                    .build()
            } else {
                request
            }

            val response = chain.proceed(newRequest)

            if (response.code == 401) {
                sessionManager.clearSession()
                // Handle logout or session expiration if needed
            }

            response
        }
    }

    // Provide OkHttpClient with interceptors and timeouts
    @Provides
    @Singleton
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .addInterceptor(loggingInterceptor)
            .connectTimeout(30, TimeUnit.SECONDS)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(30, TimeUnit.SECONDS)
            .build()
    }

    // Provide Retrofit instance
    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)  // Your base URL constant
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
    }

    // Provide API service from Retrofit
    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): HoldingsApiService {
        return retrofit.create(HoldingsApiService::class.java)
    }

    // Provide Room database instance
    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): HoldingsDatabase {
        return Room.databaseBuilder(
            context,
            HoldingsDatabase::class.java,
            "holdings_database"
        ).build()
    }

    // Provide HoldingsDao from the database
    @Provides
    fun provideHoldingsDao(database: HoldingsDatabase): HoldingsDao {
        return database.holdingsDao()
    }
}







