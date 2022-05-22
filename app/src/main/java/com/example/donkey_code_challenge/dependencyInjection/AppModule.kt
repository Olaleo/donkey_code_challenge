package com.example.donkey_code_challenge.dependencyInjection

import androidx.compose.ui.text.style.TextDirection.Companion.Content
import com.example.donkey_code_challenge.BuildConfig
import com.example.donkey_code_challenge.network.RetrofitInterface
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(/*includes = [ViewModelModule::class]*/)
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun retroFitInstance(): RetrofitInterface {

        val interceptor = HttpLoggingInterceptor()
        interceptor.level = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }

        val client = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .addInterceptor(Interceptor {
                val original = it.request()

                val request = original.newBuilder()
                    .header("Content-Type", "application/json")
                    .header("Authorization", BuildConfig.API_KEY)
                    .method(original.method, original.body)
                    .build()

                it.proceed(request)
            })

            .build()

        return Retrofit.Builder()
            .baseUrl(BuildConfig.API_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResponseAdapterFactory()).build()
            .create(RetrofitInterface::class.java)
    }

}