package com.traum.dtos.table

import kotlinx.serialization.Serializable

@Serializable
class GetTableDTO {
    var id: Long? = null
    var tableNumber: Int? = null
}

