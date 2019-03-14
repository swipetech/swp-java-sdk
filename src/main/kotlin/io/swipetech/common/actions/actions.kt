package io.swipetech.common.actions

import java.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

fun sign(secret: String, path: String, body: String, timestamp: String) =
    getHmac384(secret, timestamp + path + body)

fun getHmac384(key: String, message: String): String {

    val hasher = Mac.getInstance("HmacSHA384")
    hasher.init(SecretKeySpec(key.toByteArray(), "HmacSHA384"))
    val hash = hasher.doFinal(message.toByteArray())
    return Base64.getEncoder().encodeToString(hash)
}
