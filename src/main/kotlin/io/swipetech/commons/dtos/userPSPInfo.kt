package io.swipetech.commons.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class UserPSPInfo(
    @JsonProperty("psp")
    val psp: PSP,

    @JsonProperty("user")
    val user: User
)

data class PSP(
    @JsonProperty("name")
    val name: String? = null,

    @JsonProperty("phoneNumber")
    val phoneNumber: String? = null
)

data class User(
    @JsonProperty("first_name")
    val firstName: String? = null,

    @JsonProperty("last_name")
    val lastName: String? = null,

    @JsonProperty("document")
    val Document: String? = null,

    @JsonProperty("email")
    val Email: String? = null
)