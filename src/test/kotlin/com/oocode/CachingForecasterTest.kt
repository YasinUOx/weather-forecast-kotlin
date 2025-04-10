package com.oocode

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.teamoptimization.AcmeForecastingClientResult
import com.teamoptimization.CachingForecaster
import com.teamoptimization.Forecaster
import org.junit.jupiter.api.Test

class CachingForecasterTest {
    class FakeForecaster : Forecaster {
        var counter: Int = 0
        override fun forecast(day: String, place: String): AcmeForecastingClientResult {
            counter += 1
            return AcmeForecastingClientResult("4", "5", "Cold and rainy")
        }
    }
    @Test
    fun test_casting_forecaster() {
        val delegate = FakeForecaster()
        val underTest = CachingForecaster(delegate)
        val forecast = underTest.forecast("Monday", "Oxford")
        assertThat(forecast, equalTo(AcmeForecastingClientResult("4", "5", "Cold and rainy")))
    }

    @Test
    fun test_casting_forecaster_is_caching() {
        val delegate = FakeForecaster()
        val underTest = CachingForecaster(delegate)
        underTest.forecast("Monday", "Oxford")
        underTest.forecast("Monday", "Oxford")
        assertThat(delegate.counter, equalTo(1))
    }


    @Test
    fun test_casting_forecaster_is_caching_different() {
        val delegate = FakeForecaster()
        val underTest = CachingForecaster(delegate)
        underTest.forecast("Monday", "Oxford")
        underTest.forecast("Tuesday", "London")
        assertThat(delegate.counter, equalTo(2))
    }

    @Test
    fun test_casting_forecaster_returns_cached_result() {
        // Create a mock forecaster that returns different results each time
        val delegate = object : Forecaster {
            var callCount = 0

            override fun forecast(day: String, place: String): AcmeForecastingClientResult {
                callCount += 1
                // Return different results based on call count
                return when (callCount) {
                    1 -> AcmeForecastingClientResult("1", "10", "First call")
                    2 -> AcmeForecastingClientResult("20", "30", "Second call")
                    else -> AcmeForecastingClientResult("0", "0", "Unexpected call")
                }
            }
        }

        val underTest = CachingForecaster(delegate)

        // First call should get the first result
        val firstResult = underTest.forecast("Monday", "Oxford")
        assertThat(firstResult, equalTo(AcmeForecastingClientResult("1", "10", "First call")))
        assertThat(delegate.callCount, equalTo(1))

        // Second call with the same parameters should return the cached result
        // even though the delegate would return a different result
        val secondResult = underTest.forecast("Monday", "Oxford")
        assertThat(secondResult, equalTo(AcmeForecastingClientResult("1", "10", "First call")))
        assertThat(delegate.callCount, equalTo(1)) // Delegate shouldn't be called again

        // Call with different parameters should call the delegate again
        val thirdResult = underTest.forecast("Tuesday", "London")
        assertThat(thirdResult, equalTo(AcmeForecastingClientResult("20", "30", "Second call")))
        assertThat(delegate.callCount, equalTo(2))
    }
}