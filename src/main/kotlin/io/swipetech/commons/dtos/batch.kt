package io.swipetech.commons.dtos

import com.fasterxml.jackson.annotation.JsonProperty

data class NewTransferBatchDTO(
    @JsonProperty("actions")
    val actions: MutableList<NewTransferDTO> = mutableListOf(),

    @JsonProperty("memo")
    val memo: Memo? = null
)

data class TransferBatchDTO(
    @JsonProperty("id")
    val id: String? = null,

    @JsonProperty("actions")
    val actions: MutableList<TransferDTO> = mutableListOf(),

    @JsonProperty("memo")
    val memo: Memo? = null
) {
    companion object
}