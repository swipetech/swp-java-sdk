package io.swipetech.commons.dtos

import javax.validation.constraints.Size

data class NewTagsDTO(
    @get:Size(max = 10, message = "{tag_invalid_size}")
    val tags: List<String> = listOf()
)

data class TagsDTO(
    val id: String,
    val tags: List<String> = listOf()
)