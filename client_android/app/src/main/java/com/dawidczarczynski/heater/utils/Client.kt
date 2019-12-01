package com.dawidczarczynski.heater.utils

interface Client {

    fun <T>get(
        url: String,
        successCb: (r: T) -> Unit,
        errorCb: (() -> Unit)?
    )

}