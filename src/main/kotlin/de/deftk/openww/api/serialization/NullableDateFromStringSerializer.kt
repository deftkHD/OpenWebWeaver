package de.deftk.openww.api.serialization

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

/**
 * Same as de.deftk.openww.api.serialization.DateFromStringSerializer, but returns null if time is 0
 */
class NullableDateFromStringSerializer: KSerializer<Date?> {

    override fun deserialize(decoder: Decoder): Date? {
        val int = decoder.decodeString().toInt()
        if (int == 0)
            return null
        return Date(int * 1000L)
    }

    override val descriptor: SerialDescriptor
        get() = PrimitiveSerialDescriptor("DateFromString", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: Date?) {
        if (value != null)
            encoder.encodeString((value.time / 1000).toInt().toString())
        else
            encoder.encodeString("0")
    }
}