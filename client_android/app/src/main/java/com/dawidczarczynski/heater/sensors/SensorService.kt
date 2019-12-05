package com.dawidczarczynski.heater.sensors

import com.dawidczarczynski.heater.config.UrlConstants
import com.dawidczarczynski.heater.utils.HttpClient

class SensorService(private val httpClient: HttpClient) {

    fun getSensorsList(
        onSuccess: (List<Sensor>) -> Unit,
        onFailure: () -> Unit
    ) {
        httpClient.get<ArrayList<Sensor>>(
            UrlConstants.HOST.url + UrlConstants.SENSORS_LIST.url,
            onSuccess,
            onFailure
        )
    }

}