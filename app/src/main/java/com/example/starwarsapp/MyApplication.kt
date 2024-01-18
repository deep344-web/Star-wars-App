package com.example.starwarsapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application(){
    companion object{
        var IS_OFFLINE : Boolean = false
    }
}