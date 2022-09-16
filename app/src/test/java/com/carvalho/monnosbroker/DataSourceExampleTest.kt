package com.carvalho.monnosbroker

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.carvalho.monnosbroker.core.data.data_sources.CoinRemoteDataSourceImpl
import com.carvalho.monnosbroker.core.data.models.RemoteCoin
import com.carvalho.monnosbroker.core.data.models.RemoteResponse
import com.carvalho.monnosbroker.core.data.services.CoinService
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class DataSourceExampleTest {
    @get:Rule
    val testInstantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val testCoroutineRule = CoroutineRule()

    // SUT
    private lateinit var sut: CoinRemoteDataSourceImpl

    // MOCKS
    private val mockService = mock(CoinService::class.java)

    // VARIABLES
    private val testRemoteCoin = RemoteCoin(name = "test_coin")

    @Before
    fun setup() {
        sut = CoinRemoteDataSourceImpl(mockService)
    }

    @Test
    fun `when load coins details then fetch from service`() = testCoroutineRule.runBlockingTest {
        // arrange
        `when`(mockService.loadCoinsDetails()).thenReturn(
            Response.success(
                RemoteResponse(
                    listOf(
                        testRemoteCoin
                    )
                )
            )
        )

        // act
        val result = sut.loadCoinsDetails()

        // assert
        verify(mockService, times(1)).loadCoinsDetails()
        assertEquals(result, listOf(testRemoteCoin.toAppModel()))
    }
}