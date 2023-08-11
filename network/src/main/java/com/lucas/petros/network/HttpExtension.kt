package com.lucas.petros.network;

import com.google.gson.Gson
import com.google.gson.JsonElement
import retrofit2.HttpException

fun HttpException.errorMessage(): String? {
    return try {
        val body = this.response()?.errorBody()?.string()
        val gson = Gson()
        val element: JsonElement = gson.fromJson(body, JsonElement::class.java)
        element.asJsonObject.get("description").asString
    } catch (e: java.lang.Exception) {
        null
    }
}