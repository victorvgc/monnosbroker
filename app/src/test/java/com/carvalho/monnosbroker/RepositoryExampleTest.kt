package com.carvalho.monnosbroker

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.carvalho.monnosbroker.core.data.repositories.CoinRepositoryImpl
import com.carvalho.monnosbroker.core.domain.data_souces.CoinDataSource
import com.carvalho.monnosbroker.core.domain.models.Coin
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class RepositoryExampleTest {
    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineRule()

    // SUT
    private lateinit var sut: CoinRepositoryImpl

    // MOCKS
    private val mockLocal = mock(CoinDataSource.Local::class.java)
    private val mockRemote = mock(CoinDataSource.Remote::class.java)

    // VARIABLES
    private val testCoin = Coin(name = "test_coin", isFavorite = false)

    @Before
    fun setup() {
        sut = CoinRepositoryImpl(mockLocal, mockRemote)
    }

    @Test
    fun `when load coins details from remote then save into local`() =
        testCoroutineRule.runBlockingTest {
            // arrange
            `when`(mockRemote.loadCoinsDetails()).thenReturn(listOf(testCoin))

            // act
            sut.loadCoinsDetails()

            // assert
            verify(mockRemote, times(1)).loadCoinsDetails()
            verify(mockLocal, times(1)).saveCoins(listOf(testCoin))
        }
}