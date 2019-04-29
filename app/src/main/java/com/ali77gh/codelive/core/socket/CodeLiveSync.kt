package com.ali77gh.codelive.core.socket

import android.util.Log
import io.socket.client.IO
import io.socket.client.Socket
import io.socket.client.Socket.*

class CodeLiveSync {

    companion object {
        private val logTag = "CLS"
        private var socket: Socket? = null

        private var connectCallback: ConnectCallback? = null
        private var syncCallback: CodeLiveSyncCallback? = null

        private var currentCode = ""

        fun connect(addr: String) {

            val socket = IO.socket("http://$addr")

            socket?.on(EVENT_CONNECT) {
                Log.d("$logTag-EVENT_CONNECT", "connected")
                connectCallback?.success()
                syncCallback?.onConnectionStateChange(CONNECT)
                join()
            }

            socket?.on(EVENT_CONNECT_ERROR) {
                Log.d("$logTag-EVENT_CONNECT_ERROR", "arive here")
                connectCallback?.failed()
                close()
            }

            socket?.on(EVENT_DISCONNECT) {
                Log.d("$logTag-EVENT_DISCONNECT", "arive here")
                syncCallback?.onConnectionStateChange(DISCONNECT)
            }

            socket?.on("data") {
                Log.d("$logTag-data", "arive here")

            }

            socket?.on("contentChange") {
                Log.d("$logTag-contentChange", "arive here")
            }

            socket?.on("cursorChange") {
                Log.d("$logTag-cursorChange", "arive here")

            }

            socket?.on("scrollChange") {
                Log.d("$logTag-scrollChange", "arive here")

            }

            socket?.on("run") {
                Log.d("$logTag-run", "arive here")
            }

            socket?.on("languageChanged") {
                Log.d("$logTag-languageChanged", "arive here")
            }

            socket.connect()
        }

        private fun join() {
            socket?.emit("join", "android")
            Thread.sleep(200)
            socket?.emit("letsSync")
        }

        fun close() {
            socket?.disconnect()
            socket?.close()
            socket = null
        }


        fun setSyncListener(callback: CodeLiveSyncCallback?) {
            syncCallback = callback
        }

        fun setConnectListener(callback: ConnectCallback?) {
            connectCallback = callback
        }

        const val CONNECT = 0
        const val DISCONNECT = 1

        const val HTML = 0
        const val JS = 1
        const val CPP = 2
    }

    interface ConnectCallback {
        fun success()
        fun failed()
    }


    interface CodeLiveSyncCallback {
        fun onLoad(holeText: String, langId: String)
        fun onCursorChange(o: Any)
        fun onScrollChange(o: Any)
        fun onRun(o: Any)  // :(
        fun onConnectionStateChange(state: Int)
    }
}
