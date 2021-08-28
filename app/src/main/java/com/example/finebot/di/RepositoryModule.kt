package com.example.finebot.di



import com.example.finebot.network.BinanceService
import com.example.finebot.network.BinanceWebSocketService
import com.example.finebot.network.model.CoinDTOMapper
import com.example.finebot.repository.CoinRepository
import com.example.finebot.repository.CoinRepository_Impl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideCoinRepository(binanceService: BinanceService, coinDTOMapper: CoinDTOMapper): CoinRepository {
        return CoinRepository_Impl(binanceService = binanceService,coinDTOMapper)
    }

}