package com.dawidczarczynski.heater.utils

import android.util.Log
import com.dawidczarczynski.heater.config.UrlConstants
import io.socket.client.IO
import io.socket.client.Socket
import org.json.JSONObject

private const val TAG = "WebsocketClient"

class WebsocketClient {

    private val socket: Socket = IO.socket(UrlConstants.SOCKET_HOST.url)
    private val handlers = HashSet<String>()

    init {
        socket
            .connect()
            .on(Socket.EVENT_CONNECT) { Log.v(TAG,"socket connected") }
            .on(Socket.EVENT_DISCONNECT) { Log.v(TAG,"socket disconnected") }
    }

    fun registerEventHandler(event: String, cb: (JSONObject) -> Unit) {
        handlers.add(event)
        socket.on(event) {
            val socketMessage = it[0] as JSONObject
            cb(socketMessage)
        }

        Log.v(TAG, "$event event handler registered")
    }

    fun <T> sendEvent(event: String, payload: T) {
        socket.emit(event, payload)

        Log.v(TAG, "$event event emitted with payload: $payload")
    }

    fun unregisterAllEventHandlers() {
        handlers.forEach {
            socket.off(it)
        }
        handlers.clear()

        Log.v(TAG, "All event handlers unregistered")
    }

}
