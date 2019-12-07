package com.dawidczarczynski.heater.sensors

import com.dawidczarczynski.heater.config.UrlConstants
import com.dawidczarczynski.heater.utils.HttpClient

class SensorService(private val httpClient: HttpClient) {

    fun getSensorsList(
        onSuccess: (List<Sensor>) -> Unit,
        onFailure: () -> Unit
    ) {
        httpClient.get(
            UrlConstants.HOST.url + UrlConstants.SENSORS_LIST.url,
            SensorList::class.java,
            {
                val sensors: List<Sensor> = it.sensors
                onSuccess(sensors)
            },
            onFailure
        )
    }
}
