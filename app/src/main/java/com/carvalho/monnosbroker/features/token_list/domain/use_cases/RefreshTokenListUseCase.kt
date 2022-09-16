package com.carvalho.monnosbroker.features.token_list.domain.use_cases

import com.carvalho.monnosbroker.core.domain.models.Token
import com.carvalho.monnosbroker.core.viewmodel.BaseState
import com.carvalho.monnosbroker.core.domain.repositories.TokenRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RefreshTokenListUseCase(private val tokenRepository: TokenRepository) {
    suspend operator fun invoke(): Flow<BaseState<List<Token>>> = flow {
        emit(BaseState.Loading(null))

        val list = tokenRepository.fetchTokenList()

        if (list.isNotEmpty())
            emit(BaseState.Success(list))
        else
            emit(BaseState.Failure("Empty list"))
    }
}