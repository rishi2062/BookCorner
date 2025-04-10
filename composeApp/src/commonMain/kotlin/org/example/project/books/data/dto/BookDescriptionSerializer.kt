package org.example.project.books.data.dto

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.CompositeDecoder
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.decodeStructure
import kotlinx.serialization.encoding.encodeStructure
import kotlinx.serialization.json.JsonDecoder
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive

object BookDescriptionSerializer : KSerializer<BookWorkDTO> {
    override val descriptor: SerialDescriptor = buildClassSerialDescriptor(
        BookWorkDTO::class.simpleName!!
    ) {
        element<String?>("description")
    }

    override fun deserialize(decoder: Decoder): BookWorkDTO = decoder.decodeStructure(descriptor) {
        var description: String? = null

        while (true) {
            when (val index = decodeElementIndex(descriptor)) {
                0 -> {
                    val jsonDecoder = decoder as? JsonDecoder
                        ?: throw SerializationException("Can only deserialize JSON")
                    val element = jsonDecoder.decodeJsonElement()

                    description = if (element is JsonObject) {
                        decoder.json.decodeFromJsonElement<BookDescriptionDTO>(
                            element = element,
                            deserializer = BookDescriptionDTO.serializer()
                        ).value
                    } else if (element is JsonPrimitive && element.isString) {
                        element.content
                    } else {
                        null
                    }
                }


                CompositeDecoder.DECODE_DONE -> break
                CompositeDecoder.UNKNOWN_NAME -> throw SerializationException("Unknown index $index while deserializing BookWorkDTO")
            }
        }

        return@decodeStructure BookWorkDTO(description = description)
    }

    // we don't need serialize in our case, as we are not intend to send any such data back to api
    override fun serialize(encoder: Encoder, value: BookWorkDTO) = encoder.encodeStructure(
        descriptor
    ) {
        encodeStringElement(descriptor, 0, value.description ?: "")

    }
}