package io.swipetech.sdk

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.swipetech.commons.dtos.ErrorDTO
import khttp.responses.Response

fun checkError(resp: Response) {

    if (resp.statusCode !in 200..299) {

        if (resp.statusCode !in 400..500) {
            throw Exception(resp.text)
        }

        val errorDTO = gson.fromJson(resp.jsonObject["error"].toString(), ErrorDTO::class.java)
        errorDTO?.let {
            throw it
        }
    }
}

//FIXME change gson for jackson
val gson: Gson = GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .create()


