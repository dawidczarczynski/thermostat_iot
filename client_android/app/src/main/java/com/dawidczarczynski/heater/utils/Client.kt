package com.dawidczarczynski.heater.utils

import com.android.volley.VolleyError
import org.json.JSONObject

interface Client {

    fun get(
        url: String,
        successCb: (r: JSONObject) -> Unit,
        errorCb: ((e: VolleyError) -> Unit)?
    )

}