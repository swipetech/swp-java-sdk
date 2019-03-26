package io.swipetech.commons.dtos

import com.fasterxml.jackson.annotation.*
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

enum class Network(val networkName: String) {
    SWIPE("SWIPE"),
    STELLAR("STELLAR")
}

data class PaginationResponse(val cursor: Long)

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

    @JsonProperty("supported_asset")
    val supportedAsset: List<AssetDTO>? = listOf()
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

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class AccountDTO(
    override val id: String,
    val balances: List<BalanceDTO> = listOf(),
    val tags: List<String>? = listOf(),
    val fields: String? = null
) : IActionDTO {
    companion object
}

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class NewAccDTO(
    @JsonProperty("starting_balances")
    val startingBalances: List<BalanceDTO>? = listOf(),

    @get:Size(max = 10, message = "{tag_invalid_size}")
    @JsonProperty("tags")
    val tags: List<String>? = listOf(),

    @JsonProperty("fields")
    val fields: String? = null
) : INewActionDTO

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

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class NewAssetDTO(
    @JsonProperty("code")
    val code: String,

    @JsonProperty("limit")
    val limit: String? = null,

    @get:Size(max = 10, message = "{tag_invalid_size}")
    @JsonProperty("tags")
    val tags: List<String>? = listOf()
) : INewActionDTO

val MAX_LIMIT = "920000000000"

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class AssetDTO(

    @JsonProperty("id")
    override val id: String,

    @JsonProperty("code")
    @get:Length(max = 12, message = "{ast_code_invalid_length}")
    @get:NotBlank(message = "{ast_code_is_empty}")
    @get:Pattern(regexp = "^[a-zA-Z0-9]+\$", message = "{ast_code_invalid}")
    val code: String,

    @JsonProperty("limit")
    @get:Min(value = 1, message = "{ast_invalid_limit}")
    val limit: String = MAX_LIMIT,

    @JsonProperty("tags")
    val tags: List<String>? = listOf()
) : IActionDTO {

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is AssetDTO) this.id == other.id else false
    }

    companion object
}

data class NewTagsDTO(
    @get:Size(max = 10, message = "{tag_invalid_size}")
    val tags: List<String> = listOf()
)

data class TagsDTO(
    val id: String,
    val tags: List<String> = listOf()
)

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

enum class ActionCode(code: String) {
    ACTION_OK("action_ok"),
    ACTION_SUCCESS("action_success"),
    ACTION_UNDERFUNDED("action_underfunded"),
    ACTION_NOT_PROCESSED("action_not_processed")
}

data class NewTransferBatchDTO(
    @JsonProperty("actions")
    val actions: MutableList<NewTransferDTO> = mutableListOf(),

    @JsonProperty("memo")
    val memo: String? = null
)

data class TransferBatchDTO(
    @JsonProperty("id")
    val id: String? = null,

    @JsonProperty("actions")
    val actions: MutableList<TransferDTO> = mutableListOf(),

    @JsonProperty("memo")
    val memo: String? = null
) {
    companion object
}

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = NewTransferDTO::class, name = "TRANSFER"),
    JsonSubTypes.Type(value = NewAccDTO::class, name = "CREATE_ACC"),
    JsonSubTypes.Type(value = NewAssetDTO::class, name = "ISSUE_ASSET"),
    JsonSubTypes.Type(value = DestroyAccDTO::class, name = "DESTROY_ACC")
)
interface INewActionDTO

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = TransferDTO::class, name = "TRANSFER"),
    JsonSubTypes.Type(value = AccountDTO::class, name = "CREATE_ACC"),
    JsonSubTypes.Type(value = AssetDTO::class, name = "ISSUE_ASSET")
)
interface IActionDTO {
    val id: String?
}

data class ActionBatchDTO(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("actions")
    val actions: List<IActionDTO>,

    @JsonProperty("memo")
    val memo: String? = null
)

data class NewActionBatchDTO(
    @get:Size(min = 1, max = 100, message = "{act_invalid_actions_length}")
    @JsonProperty("actions")
    val actions: List<INewActionDTO>,

    @JsonProperty("memo")
    val memo: String? = null
)

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class NewTransferDTO(
    @JsonProperty("from")
    val from: String,

    @JsonProperty("to")
    val to: String,

    @JsonProperty("amount")
    val amount: String,

    @JsonProperty("asset")
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

    @get:Min(value = 0, message = "{pagination_ending_before}")
    @JsonProperty("ending_before")
    val endingBefore: String? = null,

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
        const val PRODUCTION = "https://api.swipetech.io"
        const val SANDBOX = "https://api-sandbox.swipetech.io"
    }
}
