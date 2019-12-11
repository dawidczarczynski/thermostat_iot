package com.dawidczarczynski.heater.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import org.json.JSONObject

class HttpClient(private val context: Context) {

    private val queue: RequestQueue = Volley.newRequestQueue(context)

    fun <T>get(
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

    fun post(
        url: String,
        payload: JSONObject,
        successCb: (r: JSONObject) -> Unit,
        errorCb: (() -> Unit)?
    ) {
        Log.v("http", "Calling HTTP POST method with URL $url")

        val request = JsonObjectRequest(
            Request.Method.POST,
            url,
            payload,
            Response.Listener<JSONObject> {
                Log.v("http", "HTTP request succeed - $it")

                successCb(it)
            },
            Response.ErrorListener {
                Log.v("http", "HTTP request failed - ${it.message}")
                showErrorMessage("There are some troubles with service connection. Please try again later...")

                if (errorCb !== null) errorCb()
            }
        )

        addRequestToQueue<JSONObject>(request)
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