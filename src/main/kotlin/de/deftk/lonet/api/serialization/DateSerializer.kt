package de.deftk.lonet.api.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

class DateSerializer: KSerializer<Date> {

    override fun deserialize(decoder: Decoder): Date {
        return Date(decoder.decodeInt() * 1000L)
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("Date", PrimitiveKind.INT)

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeInt((value.time / 1000).toInt())
    }
}