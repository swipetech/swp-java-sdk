package io.swipetech.commons.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import javax.validation.constraints.Size


@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class NewAccDTO(
    @JsonProperty("starting_balances")
    val startingBalances: List<BalanceDTO>? = listOf(),

    @get:Size(max = 10, message = "{tag_invalid_size}")
    @JsonProperty("tags")
    val tags: List<String>? = listOf(),

    @JsonProperty("fields")
    val fields: HashMap<String, String>? = null
) : INewActionDTO

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class AccountDTO(
    override val id: String,
    val balances: List<BalanceDTO> = listOf(),
    val tags: List<String>? = listOf(),
    val fields: HashMap<String, String>? = null
) : IActionDTO {
    companion object
}
