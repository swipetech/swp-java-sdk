package io.swipetech.commons.dtos

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.hibernate.validator.constraints.Length
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

const val ASSET_MAX_LIMIT = "920000000000"

@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class NewAssetDTO(

    @JsonProperty("code")
    @get:Length(max = 12, message = "{ast_code_invalid_length}")
    @get:NotBlank(message = "{ast_code_is_empty}")
    @get:Pattern(regexp = "^[a-zA-Z0-9]+\$", message = "{ast_code_invalid}")
    val code: String,

    @JsonProperty("limit")
    @get:Min(value = 1, message = "{ast_invalid_limit}")
    val limit: String? = ASSET_MAX_LIMIT,

    @get:Size(max = 10, message = "{tag_invalid_size}")
    @JsonProperty("tags")
    val tags: List<String>? = listOf(),

    @JsonProperty("is_default")
    val isDefault: Boolean? = false

) : INewActionDTO


@JsonTypeInfo(use = JsonTypeInfo.Id.NONE)
data class AssetDTO(

    @JsonProperty("id")
    override val id: String,

    @JsonProperty("code")
    val code: String,

    @JsonProperty("limit")
    val limit: String = ASSET_MAX_LIMIT,

    @JsonProperty("tags")
    val tags: List<String>? = listOf(),

    @JsonProperty("is_default")
    val isDefault: Boolean

) : IActionDTO {

    override fun hashCode(): Int {
        return super.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        return if (other is AssetDTO) this.id == other.id else false
    }

    companion object
}