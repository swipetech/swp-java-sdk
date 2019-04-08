package io.swipetech.commons.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

data class NewOrgDTO(
    @JsonProperty("name")
    @get:NotBlank(message = "{org_name_empty}")
    val name: String,

    @JsonProperty("network")
    val network: String,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("active")
    val isActive: Boolean? = true,

    @JsonProperty("assets")
    val assets: List<NewAssetDTO>? = listOf(),

    @JsonProperty("supported_assets")
    val supportedAssets: List<AssetDTO>? = listOf(),

    @JsonProperty("accs")
    val accs: List<NewAccDTO>? = listOf()
)

data class OrgDTO(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("name")
    @get:NotBlank(message = "{org_name_empty}")
    val name: String,

    @JsonProperty("balances")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val balances: List<BalanceDTO> = listOf(),

    @JsonProperty("secret")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val secret: String? = null,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("api_key")
    val apiKey: String? = null,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("network")
    val network: Network,

    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("active")
    val isActive: Boolean = true,

    @JsonProperty("assets")
    val assets: List<AssetDTO>? = listOf(),

    @JsonProperty("accs")
    val accs: List<AccountDTO>? = listOf()

) {
    companion object
}
