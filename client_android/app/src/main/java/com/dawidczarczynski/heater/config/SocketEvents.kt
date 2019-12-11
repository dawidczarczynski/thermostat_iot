package com.dawidczarczynski.heater.config

enum class SocketEvents(val event: String) {
    LISTEN_FOR_SENSOR("listen_for_sensor"),
    TEMPERATURE_CHANGE("temperature_change"),
    HEATER_STATUS_CHANGE("heater_status_change"),
    LISTEN_FOR_HEATER_STATUS("listen_for_heater_status")
}