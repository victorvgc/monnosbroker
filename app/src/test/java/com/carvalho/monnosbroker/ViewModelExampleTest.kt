package com.carvalho.monnosbroker

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.carvalho.monnosbroker.core.domain.models.Coin
import com.carvalho.monnosbroker.core.domain.models.Symbol
import com.carvalho.monnosbroker.core.domain.models.Token
import com.carvalho.monnosbroker.core.domain.use_cases.GetCoinDetailsUseCase
import com.carvalho.monnosbroker.core.viewmodel.BaseState
import com.carvalho.monnosbroker.features.token_list.domain.models.TokenItem
import com.carvalho.monnosbroker.features.token_list.domain.use_cases.PreLoadCoinDetailsUseCase
import com.carvalho.monnosbroker.features.token_list.domain.use_cases.RefreshTokenListUseCase
import com.carvalho.monnosbroker.features.token_list.domain.use_cases.SetCoinAsFavoriteUseCase
import com.carvalho.monnosbroker.features.token_list.view_models.TokenListViewModel
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.last
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class ViewModelExampleTest {
    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineRule()

    // SUT
    private lateinit var sut: TokenListViewModel

    // MOCKS
    private val mockPreLoad = mock(PreLoadCoinDetailsUseCase::class.java)
    private val mockGetCoin = mock(GetCoinDetailsUseCase::class.java)
    private val mockRefresh = mock(RefreshTokenListUseCase::class.java)
    private val mockCoinFav = mock(SetCoinAsFavoriteUseCase::class.java)

    // VARIABLES
    private val testCoin = Coin(name = "test_coin", isFavorite = false)
    private val testToken = Token(symbol = Symbol("base", testCoin.name))
    private val testTokenList = listOf(testToken)

    @Before
    fun setup() {
        sut = TokenListViewModel(mockPreLoad, mockGetCoin, mockRefresh, mockCoinFav)
    }

    @Test
    fun `when init then preload coin details`() = testCoroutineRule.runBlockingTest {
        // arrange

        // act

        // assert
        verify(mockPreLoad, times(1)).invoke()
    }

    @Test
    fun `when observed then load token list`() = testCoroutineRule.runBlockingTest {
        // arrange
        `when`(mockRefresh.invoke()).thenReturn(flow {
            emit(BaseState.Loading())
            emit(BaseState.Success(testTokenList))
        })

        // act
        val result = sut.observeData().last()

        // assert
        verify(mockRefresh, times(1)).invoke()

        verify(
            mockGetCoin,
            times(testTokenList.size)
        ).invoke(testTokenList[0].symbol.counterCurrency)

        assertTrue("Result must be a Success", result is BaseState.Success)
    }
}