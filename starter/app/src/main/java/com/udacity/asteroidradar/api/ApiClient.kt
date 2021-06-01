package com.udacity.asteroidradar.api

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import com.udacity.asteroidradar.ui.AsteroidRadarApp
import com.udacity.asteroidradar.utils.Constants
import com.udacity.asteroidradar.utils.JSONObjectAdapter
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object {
        val mServices: ApiInterface by lazy {
            getClient().create(ApiInterface::class.java)
        }

        /**
         * Build the Moshi object that Retrofit will be using, making sure to add the Kotlin adapter for
         * full Kotlin compatibility.
         */
        private val moshi = Moshi.Builder()
            .add(JSONObjectAdapter)
            .build()


        //Build Retrofit Client
        private fun getClient(): Retrofit {
            return Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addCallAdapterFactory(CoroutineCallAdapterFactory())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(MoshiConverterFactory.create(moshi))
                .client(getOkHttpClient().build())
                .build()
        }

        private fun getOkHttpClient(): OkHttpClient.Builder{
            val builder = OkHttpClient.Builder()
            builder.addNetworkInterceptor(ChuckInterceptor(AsteroidRadarApp.instance))
            builder.connectTimeout(1, TimeUnit.MINUTES)
            builder.readTimeout(30, TimeUnit.SECONDS)
            builder.writeTimeout(15, TimeUnit.SECONDS)
            return builder
        }
    }
}