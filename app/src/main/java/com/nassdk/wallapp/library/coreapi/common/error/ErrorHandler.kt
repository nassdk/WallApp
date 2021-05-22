package com.nassdk.wallapp.library.coreapi.common.error

import com.nassdk.wallapp.library.coreimpl.common.error.ErrorWrapper

interface ErrorHandler {
    fun getError(throwable: Throwable): ErrorWrapper
}