package com.teamoptimization

class CachingForecaster (val delegate: Forecaster): Forecaster {
    override fun forecast(day : String, place: String): AcmeForecastingClientResult {
        return AcmeForecastingClientResult("4", "5", "Cold and rainy")
    }
}