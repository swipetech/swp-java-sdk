package io.swipetech.sdk

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import io.swipetech.commons.dtos.ErrorDTO
import khttp.responses.Response

fun checkError(resp: Response) {

    if (resp.statusCode !in 200..299) {

        if (resp.statusCode !in 400..500) {
            throw Exception(resp.text)
        }

        val errorDTO =  jacksonObjectMapper().readValue<ErrorDTO>(resp.jsonObject["error"].toString())

        errorDTO?.let {
            throw it
        }
    }
}

inline fun <reified T : Any> getResp(resp: Response, debug: Boolean = false): T {

    val response = jacksonObjectMapper().readValue<T>(resp.jsonObject.toString())

    if (debug) {
        println("Got response:")
        println("--path:${resp.url}")
        println("--body:$response")
    }

    return response
}

