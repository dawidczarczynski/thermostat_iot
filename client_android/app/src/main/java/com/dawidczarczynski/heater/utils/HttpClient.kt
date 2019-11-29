package com.dawidczarczynski.heater.utils

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.VolleyError
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONObject

class HttpClient(context: Context): Client {

    private var queue: RequestQueue = Volley.newRequestQueue(context)

    override fun get(
        url: String,
        successCb: (r: JSONObject) -> Unit,
        errorCb: ((e: VolleyError) -> Unit)?
    ) {
        Log.v("http", "Calling HTTP GET method with URL $url")

        val request = JsonObjectRequest(
            Request.Method.GET,
            url,
            null,
            Response.Listener {
                Log.v("http", "HTTP request succeed - $it")
                successCb(it)
            },
            Response.ErrorListener {
                Log.v("http", "HTTP request failed - ${it.message}")
                if (errorCb !== null) errorCb(it)
            }
        )

        addRequestToQueue<JSONObject>(request)
    }

    private fun <T>addRequestToQueue(request: Request<T>) {
        queue.add(request)
    }

}