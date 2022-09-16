package com.carvalho.monnosbroker.features.token_list.di

import com.carvalho.monnosbroker.features.token_list.domain.use_cases.PreLoadCoinDetailsUseCase
import com.carvalho.monnosbroker.features.token_list.domain.use_cases.RefreshTokenListUseCase
import com.carvalho.monnosbroker.features.token_list.domain.use_cases.SetCoinAsFavoriteUseCase
import com.carvalho.monnosbroker.features.token_list.view_models.TokenListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val tokenListModule = module {
    // local

    // remote

    // data sources

    // repositories

    // use cases
    factory { PreLoadCoinDetailsUseCase(get()) }
    factory { RefreshTokenListUseCase(get()) }
    factory { SetCoinAsFavoriteUseCase(get()) }

    // view model
    viewModel { TokenListViewModel(get(), get(), get(), get()) }
}