package com.carvalho.monnosbroker.features.coin_details.di

import com.carvalho.monnosbroker.features.coin_details.view_models.CoinDetailsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val coinDetailsModule = module {
    // local

    // remote

    // data sources

    // repositories

    // use cases

    // view models
    viewModel { CoinDetailsViewModel(get(), get(), get()) }
}