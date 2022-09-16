package com.carvalho.monnosbroker.core.ui

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.carvalho.monnosbroker.core.viewmodel.BaseState
import com.carvalho.monnosbroker.core.viewmodel.BaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<DataType> : AppCompatActivity(), CoroutineScope {

    protected inline fun <reified T : ViewDataBinding> binding(@LayoutRes resId: Int): Lazy<T> =
        lazy { DataBindingUtil.setContentView(this, resId) }

    abstract val viewModel: BaseViewModel<DataType>

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        job = Job()
    }

    protected fun observeStates(bundle: Bundle? = null) {
        launch {
            viewModel.observeData(bundle).collectLatest {
                processStates(it)
            }
        }
    }

    protected fun processStates(state: BaseState<DataType>) {
        when (state) {
            is BaseState.Failure<*> -> {
                processFailureState(state as BaseState.Failure<DataType>)
            }
            is BaseState.Loading -> {
                processLoadingState(state)
            }
            is BaseState.Success<*> -> {
                processSuccessState(state as BaseState.Success<DataType>)
            }
        }
    }

    abstract fun processLoadingState(state: BaseState.Loading<DataType>)
    abstract fun processSuccessState(state: BaseState.Success<DataType>)
    abstract fun processFailureState(state: BaseState.Failure<DataType>)

    override fun onDestroy() {
        super.onDestroy()

        job.cancel()
    }
}