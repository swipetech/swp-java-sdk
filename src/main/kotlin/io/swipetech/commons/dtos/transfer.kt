package io.swipetech.commons.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull


@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class NewTransferDTO(

    @JsonProperty("from")
    @get:NotBlank(message = "{transfer_from_is_empty}")
    @get:NotNull(message = "{transfer_from_is_empty}")
    val from: String,

    @get:NotBlank(message = "{transfer_to_is_empty}")
    @get:NotNull(message = "{transfer_to_is_empty}")
    @JsonProperty("to")
    val to: String,

    @get:NotNull(message = "{transfer_invalid_amount}")
    @JsonProperty("amount")
    val amount: String,

    @JsonProperty("asset")
    @get:NotBlank(message = "{transfer_asset_id_is_empty}")
    @get:NotNull(message = "{transfer_asset_id_is_empty}")
    val asset: String

) : INewActionDTO

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class TransferDTO(

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