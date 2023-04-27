package com.proyecto.serializer

import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import org.bson.types.ObjectId

object ObjectIdSerializer: KSerializer<ObjectId> {
    override val descriptor = PrimitiveSerialDescriptor("ObjectId", PrimitiveKind.STRING)

    /**
     * Método para deserializer una LocalDate
     * @param decoder Decoder necesario para deserialize
     * @return devuelve un LocalDate
     */
    override fun deserialize(decoder: Decoder): ObjectId {
        return ObjectId(decoder.decodeString())
    }

    /**
     * Método para serializar una LocalDate
     * @param encoder Encoder necesario para poder serializar
     * @param value LocalDate a serializar
     */
    override fun serialize(encoder: Encoder, value: ObjectId) {
        encoder.encodeString(value.toString())
    }
}
