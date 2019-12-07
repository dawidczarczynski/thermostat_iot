package com.dawidczarczynski.heater.utils

interface Client {

    fun <T>get(
        url: String,
        entity: Class<T>,
        successCb: (r: T) -> Unit,
        errorCb: (() -> Unit)?
    )

}