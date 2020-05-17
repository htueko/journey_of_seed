package com.htueko.android.journeyofseed.util.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.os.Build
import android.util.Log

object NetworkConnectivityChecker : SingleLiveEvent<Boolean>() {

    private lateinit var networkConnectivityUtil: NetworkConnectivityUtil
    private lateinit var connectivityManager: ConnectivityManager

    override fun onActive() {
        registerCallback()
        Log.d("DG", "Network Connectivity Registered")
        super.onActive()
    }

    override fun onInactive() {
        removeCallback()
        Log.d("DG", "Network Connectivity Unregistered")
        super.onInactive()
    }

    fun checkForConnection() {
        value = networkConnectivityUtil.isConnected()
    }

    fun hasConnection(): Boolean = networkConnectivityUtil.isConnected()

    //Should be injected in Application class : )
    fun init(context: Context) {
        networkConnectivityUtil = NetworkConnectivityUtil(context)
        connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    private fun notifyObservers(connectionStatus: Boolean) {
        postValue(connectionStatus)
    }

    private fun registerCallback() {
        val networkRequest = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            NetworkRequest.Builder()
                .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
                .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
                .build()
        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
        }
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    private fun removeCallback() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(networkCallback)
        } else {
            TODO("VERSION.SDK_INT < LOLLIPOP")
        }
    }

    private val networkCallback = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                notifyObservers(true)
                super.onAvailable(network)
            }

            override fun onLost(network: Network) {
                notifyObservers(false)
                super.onLost(network)
            }
        }
    } else {
        TODO("VERSION.SDK_INT < LOLLIPOP")
    }
}