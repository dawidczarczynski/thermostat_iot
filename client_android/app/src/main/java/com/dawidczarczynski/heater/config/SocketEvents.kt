package com.dawidczarczynski.heater.config

enum class SocketEvents(val event: String) {
    LISTEN_FOR_SENSOR("listen_for_sensor"),
    TEMPERATURE_CHANGE("temperature_change")
}