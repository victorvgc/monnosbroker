package com.carvalho.monnosbroker.core.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<DataType> : ViewModel() {

    abstract suspend fun observeData(): Flow<BaseState<DataType>>
}