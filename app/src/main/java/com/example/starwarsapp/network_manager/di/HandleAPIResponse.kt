package com.example.starwarsapp.network_manager.di


import android.app.Application
import retrofit2.Response
import java.io.IOException
import java.net.SocketTimeoutException

fun <T> Response<T>.handleApiResponse(
    application: Application,
    apiCallbackListener: ApiCallbackListener<T?>
) {
    if (this.isSuccessful) {
        apiCallbackListener.onApiSuccess(this.body())
    } else {
        handleApiFailure(application, apiCallbackListener)
    }
}

fun <T> Response<T>.handleApiFailure(
    application: Application,
    apiCallbackListener: ApiCallbackListener<T?>
) {
    val code = this.code()
    if (code == 404) {
        apiCallbackListener.onApiFailure(this.message())
    }
}

fun <T> Throwable.handleApiException(
    apiCallbackListener: ApiCallbackListener<T?>
) {
    runCatching {
        when (this) {
            is SocketTimeoutException -> {
                apiCallbackListener.onApiFailure("Request time out.", ErrorCodes.TIME_OUT.value)
            }

            is IOException -> {
                apiCallbackListener.onApiFailure("No Internet Connection", ErrorCodes.INTERNET_ERROR.value)
            }

            else -> {
                apiCallbackListener.onApiFailure("Don't know error.", ErrorCodes.DO_NOT_KNOW.value)
            }
        }
    }.getOrElse {
        apiCallbackListener.onApiFailure("Something went wrong.", ErrorCodes.SOMETHING_WENT_WRONG.value)
    }
}


