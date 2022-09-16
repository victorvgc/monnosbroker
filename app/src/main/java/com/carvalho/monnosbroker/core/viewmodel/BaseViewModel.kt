package com.carvalho.monnosbroker.core.viewmodel

import android.os.Bundle
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<DataType> : ViewModel() {

    abstract suspend fun observeData(bundle: Bundle? = null): Flow<BaseState<DataType>>
}