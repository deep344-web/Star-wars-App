package com.example.starwarsapp.common

import com.example.starwarsapp.MyApplication


interface OfflineHelper{
    fun isOffline(): Boolean

    fun updateOffline(isOffline: Boolean)
}

class OfflineHelperImpl: OfflineHelper{
    override fun isOffline(): Boolean{
        return MyApplication.IS_OFFLINE
    }

    override fun updateOffline(isOffline: Boolean){
        MyApplication.IS_OFFLINE = isOffline
    }
}