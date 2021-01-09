package de.deftk.lonet.api.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

// god, WHY?
class DateFromStringSerializer: KSerializer<Date> {

    override fun deserialize(decoder: Decoder): Date {
        return Date(decoder.decodeString().toInt() * 1000L)
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("DateFromString", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date) {
        encoder.encodeString((value.time / 1000).toInt().toString())
    }
}