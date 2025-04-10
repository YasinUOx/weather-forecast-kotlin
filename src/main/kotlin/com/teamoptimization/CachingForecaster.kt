package com.teamoptimization

class CachingForecaster (val delegate: Forecaster): Forecaster {


    val cache = mutableMapOf<Pair<String, String>, AcmeForecastingClientResult>()

    override fun forecast(day : String, place: String): AcmeForecastingClientResult {

        val key = Pair(day, place)

        // Check if the result is already in the cache
        cache[key]?.let { cachedResult ->
            // If found in cache, return it without calling the delegate
            return cachedResult
        }

        // If not in cache, get the result from the delegate
        val result = delegate.forecast(day, place)

        // Store the result in the cache for future use
        cache[key] = result


        return AcmeForecastingClientResult("4", "5", "Cold and rainy")
    }
}