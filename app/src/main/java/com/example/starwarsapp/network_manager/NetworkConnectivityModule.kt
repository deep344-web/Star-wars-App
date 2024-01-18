package com.example.starwarsapp.network_manager

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkConnectivityModule {

    @Singleton
    @Provides
    fun provideAppDatabase(@ApplicationContext appContext: Context): NetworkConnectivityObserver {
        return NetworkConnectivityObserver(appContext)
    }
}
