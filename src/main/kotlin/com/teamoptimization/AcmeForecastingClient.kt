import com.teamoptimization.AcmeForecastingClientResult
import com.teamoptimization.Forecaster
import com.teamoptimization.acmeForecast
import org.http4k.core.HttpHandler

class AcmeForecastingClient(
    private val httpClient: HttpHandler
) : Forecaster {
    override fun forecast(day: String, place: String): AcmeForecastingClientResult {
        return acmeForecast(httpClient, day, place)
    }
}