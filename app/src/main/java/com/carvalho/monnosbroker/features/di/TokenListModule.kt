package com.carvalho.monnosbroker.features.di

import com.carvalho.monnosbroker.core.data.MainDatabase
import com.carvalho.monnosbroker.core.data.getApi
import com.carvalho.monnosbroker.features.data.data_sources.CoinLocalDataSourceImpl
import com.carvalho.monnosbroker.features.data.data_sources.CoinRemoteDataSourceImpl
import com.carvalho.monnosbroker.features.data.data_sources.TokenLocalDataSourceImpl
import com.carvalho.monnosbroker.features.data.data_sources.TokenRemoteDataSourceImpl
import com.carvalho.monnosbroker.features.data.repositories.CoinRepositoryImpl
import com.carvalho.monnosbroker.features.data.repositories.TokenRepositoryImpl
import com.carvalho.monnosbroker.features.data.services.CoinService
import com.carvalho.monnosbroker.features.data.services.TokenService
import com.carvalho.monnosbroker.features.domain.data_sources.CoinDataSource
import com.carvalho.monnosbroker.features.domain.data_sources.TokenDataSource
import com.carvalho.monnosbroker.features.domain.repositories.CoinRepository
import com.carvalho.monnosbroker.features.domain.repositories.TokenRepository
import com.carvalho.monnosbroker.features.domain.use_cases.GetCoinDetailsUseCase
import com.carvalho.monnosbroker.features.domain.use_cases.PreLoadCoinDetailsUseCase
import com.carvalho.monnosbroker.features.domain.use_cases.RefreshTokenListUseCase
import com.carvalho.monnosbroker.features.domain.use_cases.SetCoinAsFavoriteUseCase
import com.carvalho.monnosbroker.features.view_models.TokenListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tokenListModule = module {
    // remote
    single {
        getApi(CoinService::class.java)
    }

    single {
        getApi(TokenService::class.java)
    }

    // local
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
    factory { PreLoadCoinDetailsUseCase(get()) }
    factory { GetCoinDetailsUseCase(get()) }
    factory { RefreshTokenListUseCase(get()) }
    factory { SetCoinAsFavoriteUseCase(get()) }

    // view model
    viewModel { TokenListViewModel(get(), get(), get(), get()) }
}