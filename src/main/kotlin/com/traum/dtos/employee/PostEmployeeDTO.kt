package com.traum.dtos.employee

import kotlinx.serialization.Serializable

@Serializable
data class PostEmployeeDTO(
    var name: String,
    var phoneNumber: String
)
