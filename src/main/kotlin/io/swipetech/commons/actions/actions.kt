package io.swipetech.commons.actions

import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

fun sign(secret: String, path: String, body: String, timestamp: String) =
    getHmac256(secret, timestamp + path + body)

fun getHmac256(key: String, message: String): String {

    val hasher = Mac.getInstance("HmacSHA256")
    hasher.init(SecretKeySpec(key.toByteArray(), "HmacSHA256"))
    val hash = hasher.doFinal(message.toByteArray())
    return Base64.getEncoder().encodeToString(hash)
}
