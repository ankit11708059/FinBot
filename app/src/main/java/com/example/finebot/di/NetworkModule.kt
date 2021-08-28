package com.example.finebot.di

import com.example.finebot.network.BinanceService
import com.example.finebot.network.model.CoinDTOMapper
import com.example.finebot.util.CandlestickDeserializer
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule{

    private inline fun <reified T> GsonBuilder.registerTypeAdapter(deserializer: JsonDeserializer<T>): GsonBuilder = registerTypeAdapter(T::class.java, deserializer)
    @Provides
    @Singleton
    fun provideCoinDtoMapper() : CoinDTOMapper{
        return CoinDTOMapper()
    }
    @Provides
    @Singleton
    fun provideBinanceService() : BinanceService{
        return Retrofit.Builder().baseUrl("https://api.binance.com/")
            .addConverterFactory(
                createGsonConverterFactory())
                .build().create(BinanceService::class.java)
    }


    @JvmStatic
    private fun createGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(
            GsonBuilder()
                .registerTypeAdapter(CandlestickDeserializer())
                .create())
    }


}