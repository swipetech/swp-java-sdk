package io.swipetech.commons.dtos

import com.fasterxml.jackson.annotation.*
import io.swipetech.commons.actions.sha256
import javax.validation.constraints.Min

data class SignatureInfo(
    val method: String,
    val timestamp: String,
    val path: String,
    val bodyString: String
)

open class Network(val networkName: String) {
    object SWIPE : Network("SWIPE")
    object STELLAR : Network("STELLAR")
}

data class PaginationResponse(val cursor: Long)

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class DestroyAccDTO(@JsonProperty("acc_id") val accId: String) : INewActionDTO

data class BalanceDTO(
    @JsonProperty("asset_id")
    val assetId: String,
    @JsonProperty("asset_code")
    val assetCode: String? = "",
    @JsonProperty("balance")
    val balance: String
) {
    companion object
}

data class ReceiptDTO(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("created_at")
    val createdAt: String
) {
    companion object
}

data class FilterDTO(
    val tag: String? = null
)

@JsonIgnoreProperties("message", "cause", "stackTrace", "suppressed", "localizedMessage")
data class ErrorDTO(
    val code: String,
    val msg: String,
    @JsonProperty("sub_errors")
    val subErrors: MutableList<SubError>
) :

    Throwable(message = "Error(code=$code, subErrors=$subErrors, msg='$msg')") {

    override fun toString(): String {
        return "ErrorDTO(code='$code', msg='$msg', subErrors=$subErrors)"
    }

    companion object
}

data class SubError(
    val code: String,
    var msg: String? = null,
    val field: String? = null,
    val index: Long = -1
)

data class DataDTOReceipt<T>(
    val receipt: ReceiptDTO,
    val value: T
)

data class DataDTO<T>(
    val value: T
)

data class ResponseToken(
    @JsonProperty("token")
    val token: String? = null
)

data class ResponseDTO<T>(
    val data: T? = null,
    val error: ErrorDTO? = null
) {
    companion object
}

data class ResponseListDTO<T>(
    val data: List<DataDTOReceipt<T>>,
    val pagination: PaginationResponse?,
    val error: ErrorDTO? = null
) {
    companion object
}

data class PaginationParams(
    @get:Min(value = 0, message = "{pagination_starting_after}")
    @JsonProperty("starting_after")
    val startingAfter: String? = null,

    @get:Min(value = 0, message = "{pagination_limit}")
    @JsonProperty("limit")
    val limit: String = "100"
)

typealias Method = String

class Methods {
    companion object {
        const val POST: Method = "POST"
        const val GET: Method = "GET"
        const val DELETE: Method = "DELETE"
        const val PUT: Method = "PUT"
    }
}

class AcceptLanguage {
    companion object {
        const val PT_BR = "pt-BR"
        const val EN_US = "en-US"
    }
}

class Host {
    companion object {
        const val PRODUCTION = "https://0-9.api.swipetech.io"
        const val SANDBOX = "https://api-sandbox.swipetech.io"
    }
}

enum class MemoType {
    HASH, TEXT
}

data class Memo(
    @JsonProperty("type")
    val type: String = MemoType.TEXT.name,

    @JsonProperty("value")
    val value: String
) {
    companion object {
        fun Text(v: String): Memo {
            return Memo(MemoType.TEXT.name, v)
        }

        fun Hash(v: String): Memo {
            return Memo(MemoType.HASH.name, sha256(v))
        }
    }
}
