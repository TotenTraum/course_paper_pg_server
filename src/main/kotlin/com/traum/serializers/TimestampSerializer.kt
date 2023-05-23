package com.traum.serializers

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.sql.Timestamp

object TimestampSerializer : KSerializer<Timestamp> {

    override val descriptor = PrimitiveSerialDescriptor("java.sql.Timestamp", PrimitiveKind.STRING)

    override fun deserialize(decoder: Decoder): Timestamp {
        val dateTime = LocalDateTime.parse(decoder.decodeString())
        return Timestamp.valueOf(dateTime.toJavaLocalDateTime())
    }

    override fun serialize(encoder: Encoder, value: Timestamp) =
        encoder.encodeString(LocalDateTime.parse(value.toLocalDateTime().toString()).toString())
}