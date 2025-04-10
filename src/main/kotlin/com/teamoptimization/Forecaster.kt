package com.teamoptimization

interface Forecaster {
    fun forecast(day : String, place: String): AcmeForecastingClientResult
}