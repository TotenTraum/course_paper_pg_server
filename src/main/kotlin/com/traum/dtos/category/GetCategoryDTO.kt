package com.traum.dtos.category

import kotlinx.serialization.Serializable

@Serializable
class GetCategoryDTO {
    var id: Long? = null
    var name: String? = null
}