package io.swipetech.commons.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class ClientPSPInfo(
    @JsonProperty("psp")
    val psp: PSP,

    @JsonProperty("client")
    val client: Client
)

data class PSP(
    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("tel")
    val tel: String? = null
)

data class Client(
    @JsonProperty("first_name")
    val firstName: String? = null,

    @JsonProperty("last_name")
    val lastName: String? = null,

    @JsonProperty("document")
    val Document: String? = null,

    @JsonProperty("email")
    val Email: String? = null
)