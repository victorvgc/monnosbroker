package com.carvalho.monnosbroker.core.di

import androidx.room.Room
import com.carvalho.monnosbroker.core.data.data_sources.CoinLocalDataSourceImpl
import com.carvalho.monnosbroker.core.data.data_sources.CoinRemoteDataSourceImpl
import com.carvalho.monnosbroker.core.data.data_sources.TokenLocalDataSourceImpl
import com.carvalho.monnosbroker.core.data.data_sources.TokenRemoteDataSourceImpl
import com.carvalho.monnosbroker.core.data.getApi
import com.carvalho.monnosbroker.core.data.repositories.CoinRepositoryImpl
import com.carvalho.monnosbroker.core.data.repositories.TokenRepositoryImpl
import com.carvalho.monnosbroker.core.data.room.MainDatabase
import com.carvalho.monnosbroker.core.data.services.CoinService
import com.carvalho.monnosbroker.core.data.services.TokenService
import com.carvalho.monnosbroker.core.domain.data_souces.CoinDataSource
import com.carvalho.monnosbroker.core.domain.data_souces.TokenDataSource
import com.carvalho.monnosbroker.core.domain.repositories.CoinRepository
import com.carvalho.monnosbroker.core.domain.repositories.TokenRepository
import com.carvalho.monnosbroker.core.domain.use_cases.GetCoinDetailsUseCase
import com.carvalho.monnosbroker.core.domain.use_cases.GetTokenBySymbolUseCase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val mainModule = module {
    // remote
    single {
        getApi(CoinService::class.java)
    }

    single {
        getApi(TokenService::class.java)
    }

    // local
    single {
        Room.databaseBuilder(
            androidContext(),
            MainDatabase::class.java,
            "mainDb"
        ).build()
    }

    single {
        get<MainDatabase>().coinDao()
    }
    single {
        get<MainDatabase>().tokenDao()
    }

    // data source
    single<CoinDataSource.Local> {
        CoinLocalDataSourceImpl(get())
    }
    single<CoinDataSource.Remote> {
        CoinRemoteDataSourceImpl(get())
    }
    single<TokenDataSource.Remote> { TokenRemoteDataSourceImpl(get()) }
    single<TokenDataSource.Local> { TokenLocalDataSourceImpl(get()) }

    // repository
    single<CoinRepository> { CoinRepositoryImpl(get(), get()) }
    single<TokenRepository> { TokenRepositoryImpl(get(), get()) }

    // use cases
    factory { GetCoinDetailsUseCase(get()) }
    factory { GetTokenBySymbolUseCase(get()) }

}