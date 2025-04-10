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
}