package com.oocode

import com.natpryce.hamkrest.assertion.assertThat
import com.natpryce.hamkrest.equalTo
import com.teamoptimization.AcmeForecastingClientResult
import com.teamoptimization.CachingForecaster
import com.teamoptimization.Forecaster
import org.junit.jupiter.api.Test

class CachingForecasterTest {
    class FakeForecaster : Forecaster {
        override fun forecast(day: String, place: String): AcmeForecastingClientResult {
            return AcmeForecastingClientResult("4", "5", "Cold and rainy")
        }
    }
    @Test
    fun test_forecaster() {
        val delegate = FakeForecaster()
        val underTest = CachingForecaster(delegate)
        val forecast = underTest.forecast("Monday", "Oxford")
        assertThat(forecast, equalTo(AcmeForecastingClientResult("4", "5", "Cold and rainy")))
    }
}