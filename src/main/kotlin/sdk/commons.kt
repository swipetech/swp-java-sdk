package sdk

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
        const val PRODUCTION = "https://api.swipetech.io"
        const val SANDBOX = "https://api-sandbox.swipetech.io"
    }
}

//enum class Network(val networkName: String) {
//    SWIPE("SWIPE"),
//    STELLAR("STELLAR")
//}

data class PaginationParams(
    @get:Min(value = 0, message = "{pagination_starting_after}")
    @JsonProperty("starting_after")
    val startingAfter: String? = null,

    @get:Min(value = 0, message = "{pagination_ending_before}")
    @JsonProperty("ending_before")
    val endingBefore: String? = null,

    @get:Min(value = 0, message = "{pagination_limit}")
    @JsonProperty("limit")
    val limit: String = "100"
)

fun checkError(resp: Response) {
    if (resp.statusCode !in 200..299) {

        if (resp.statusCode !in 400..500) {
            throw Exception(resp.text)
        }

        val errorDTO = gson.fromJson<ErrorDTO>(resp.jsonObject["error"].toString(), ErrorDTO::class.java)
        errorDTO?.let {
            throw Error(code = ErrorCodes.valueOf(errorDTO.code.toUpperCase()), msg = it.msg, subErrors = errorDTO.subErrors)
        }
    }
}

val gson: Gson = GsonBuilder()
    .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
    .create()


