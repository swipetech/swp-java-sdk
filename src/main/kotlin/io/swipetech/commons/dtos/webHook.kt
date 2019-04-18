package io.swipetech.commons.dtos

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import org.hibernate.validator.constraints.URL
import javax.validation.constraints.NotBlank

data class NewWebhookDTO(
    @JsonProperty("url")
    @get:NotBlank(message = "{url_name_empty}")
    @get:URL(message = "{invalid_url}")
    val url: String,

    @JsonProperty("actionType")
    @get:NotBlank(message = "{webhook_type_empty}")
    val actionType: String
)

data class WebhookDTO(
    @JsonProperty("id")
    val id: String,

    @JsonProperty("actionType")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val actionType: String? = null,

    @JsonProperty("url")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val url: String? = null
)
