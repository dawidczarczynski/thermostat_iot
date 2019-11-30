package com.dawidczarczynski.heater.utils

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class HttpClient(context: Context): Client {

    private var queue: RequestQueue = Volley.newRequestQueue(context)

    override fun get(
        url: String,
        successCb: (r: String) -> Unit,
        errorCb: ((e: VolleyError) -> Unit)?
    ) {
        Log.v("http", "Calling HTTP GET method with URL $url")

        val request = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> {
                Log.v("http", "HTTP request succeed - $it")
                successCb(it)
            },
            Response.ErrorListener {
                Log.v("http", "HTTP request failed - ${it.message}")
                if (errorCb !== null) errorCb(it)
            }
        )

        addRequestToQueue<String>(request)
    }

    private fun <T>addRequestToQueue(request: Request<T>) {
        queue.add(request)
    }

}