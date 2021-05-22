package com.nassdk.wallapp.library.coreapi.common.resourcemanager

interface ResourceManager {
    fun getString(resId: Int): String
    fun getString(resId: Int, vararg formatArg: Any): String
}