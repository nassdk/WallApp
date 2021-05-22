package com.nassdk.wallapp.library.coreimpl.network.error

import com.google.gson.Gson
import javax.inject.Inject

class NetworkErrorParser @Inject constructor(
    private val gson: Gson
) {
    fun parseError(response: String?): BaseErrorNetModel.ErrorNetModel? = try {
        val model = gson.fromJson(response, BaseErrorNetModel::class.java)
        model.error
    } catch (e: Exception) {
        null
    }
}