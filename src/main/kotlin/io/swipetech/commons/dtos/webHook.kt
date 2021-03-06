package io.swipetech.commons.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import javax.validation.constraints.NotBlank

data class NewWebhookDTO(
    @JsonProperty("url")
    @get:NotBlank(message = "{url_name_empty}")
    val url: String
)

enum class WebhookType(type: String) {
    TRANSFER_RECEIVED("transfer_received")
}

data class WebhookDTO(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("hook_type")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val hookType: String? = null,

    @JsonProperty("balances")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val url: String? = null,

    @JsonProperty("secret")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val secret: String? = null
)
