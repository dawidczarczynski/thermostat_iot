package com.dawidczarczynski.heater.utils

import com.android.volley.VolleyError

interface Client {

    fun get(
        url: String,
        successCb: (r: String) -> Unit,
        errorCb: ((e: VolleyError) -> Unit)?
    )

}