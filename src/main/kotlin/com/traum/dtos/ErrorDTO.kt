package com.traum.dtos

import kotlinx.serialization.Serializable

@Serializable
class ErrorDTO(val reason: String) {
    val description: String = ""
}