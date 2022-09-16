package com.carvalho.monnosbroker.core.viewmodel

sealed class BaseState<DataType> {
    data class Loading<DataType>(val data: DataType? = null): BaseState<DataType>()
    data class Success<DataType>(val data: DataType): BaseState<DataType>()
    data class Failure<DataType>(val msg: String, val data: DataType? = null): BaseState<DataType>()
}