package io.swipetech.commons.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import javax.validation.constraints.Size


enum class ActionCode(code: String) {
    ACTION_OK("action_ok"),
    ACTION_SUCCESS("action_success"),
    ACTION_UNDERFUNDED("action_underfunded"),
    ACTION_NOT_PROCESSED("action_not_processed")
}

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.PROPERTY,
    property = "type"
)
@JsonSubTypes(
    JsonSubTypes.Type(value = NewTransferDTO::class, name = "TRANSFER"),
    JsonSubTypes.Type(value = NewTrailTransferDTO::class, name = "TRAIL_TRANSFER"),
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
    JsonSubTypes.Type(value = TrailTransferDTO::class, name = "TRAIL_TRANSFER"),
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
    val memo: Memo? = null
)

data class NewActionBatchDTO(
    @get:Size(min = 1, max = 100, message = "{act_invalid_actions_length}")
    @JsonProperty("actions")
    val actions: List<INewActionDTO>,

    @JsonProperty("memo")
    val memo: Memo? = null
)