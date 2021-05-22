package com.nassdk.wallapp.library.coreimpl.common.error

sealed class ErrorWrapper {
    object Unknown : ErrorWrapper()
    object Timeout : ErrorWrapper()
    object ServerError : ErrorWrapper()
    object Network : ErrorWrapper()
    data class CustomError(val message: String) : ErrorWrapper()
}