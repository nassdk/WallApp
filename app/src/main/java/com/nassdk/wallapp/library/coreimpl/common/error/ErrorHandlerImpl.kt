package com.nassdk.wallapp.library.coreimpl.common.error

import com.nassdk.wallapp.library.coreapi.common.error.ErrorHandler
import com.nassdk.wallapp.library.coreimpl.network.error.NetworkErrorParser
import retrofit2.HttpException
import java.io.IOException
import java.util.concurrent.TimeoutException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorHandlerImpl @Inject constructor(
    private val errorParser: NetworkErrorParser
) : ErrorHandler {

    override fun getError(throwable: Throwable): ErrorWrapper = when (throwable) {
        is IOException -> ErrorWrapper.Network
        is TimeoutException -> ErrorWrapper.Timeout
        is HttpException -> {
            when (throwable.code()) {
                in 400..499 -> {
                    val response = throwable.response()?.errorBody()?.string()
                    val errorNetModel = errorParser.parseError(response)

                    if (errorNetModel != null)
                        ErrorWrapper.CustomError(message = errorNetModel.message)
                    else ErrorWrapper.Unknown
                }
                in 500..599 -> ErrorWrapper.ServerError
                else -> ErrorWrapper.Unknown
            }
        }
        else -> ErrorWrapper.Unknown
    }
}