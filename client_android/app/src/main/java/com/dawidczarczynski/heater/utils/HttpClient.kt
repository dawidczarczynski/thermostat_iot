package com.dawidczarczynski.heater.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson

class HttpClient(private val context: Context): Client {

    private val queue: RequestQueue = Volley.newRequestQueue(context)

    override fun <T>get(
        url: String,
        entity: Class<T>,
        successCb: (r: T) -> Unit,
        errorCb: (() -> Unit)?
    ) {
        Log.v("http", "Calling HTTP GET method with URL $url")

        val request = StringRequest(
            Request.Method.GET,
            url,
            Response.Listener<String> {
                Log.v("http", "HTTP request succeed - $it")
                val parsedResponse: T = parseJsonResponse(it, entity)

                successCb(parsedResponse)
            },
            Response.ErrorListener {
                Log.v("http", "HTTP request failed - ${it.message}")
                showErrorMessage("There are some troubles with service connection. Please try again later...")

                if (errorCb !== null) errorCb()
            }
        )

        addRequestToQueue<String>(request)
    }

    private fun <T>addRequestToQueue(request: Request<T>) {
        queue.add(request)
    }

    private fun <T>parseJsonResponse(jsonString: String, elementType: Class<T>): T {
        return Gson().fromJson(
            jsonString,
            elementType
        )
    }

    private fun showErrorMessage(message: String) {
        Toast
            .makeText(context, message, Toast.LENGTH_LONG)
            .show()
    }

}