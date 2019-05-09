package io.swipetech.commons.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class NewTrailTransferDTO(
    @JsonProperty("from")
    val from: String,

    @JsonProperty("to")
    val to: String,

    @JsonProperty("amount")
    val amount: String,

    @JsonProperty("asset")
    val asset: String? = null
) : INewActionDTO

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class TrailTransferDTO(

    @JsonProperty("id")
    override val id: String? = null,

    @JsonProperty("from")
    val from: String? = "",

    @JsonProperty("to")
    val to: String? = "",

    @JsonProperty("amount")
    val amount: String? = "0",

    @JsonProperty("asset")
    val asset: String? = "",

    @JsonProperty("action_code")
    val actionCode: String? = ActionCode.ACTION_NOT_PROCESSED.toString()
) : IActionDTO

data class TrailTransferFilter(
    val userFrom: String = "",
    val domainFrom: String = "",
    val userTo: String = "",
    val domainTo: String = ""
)