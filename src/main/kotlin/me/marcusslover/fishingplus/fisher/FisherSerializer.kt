/*
 * Copyright (c) 2021.
 * Under GNU General Public License v3.0.
 */

package me.marcusslover.fishingplus.fisher

import com.google.gson.*
import java.lang.reflect.Type
import java.util.*

/**
 * Responsible for serialization and deserialization of the Fisher data class.
 */
class FisherSerializer : JsonSerializer<Fisher>, JsonDeserializer<Fisher> {
    override fun serialize(fisher: Fisher, type: Type, context: JsonSerializationContext): JsonElement {
        val json = JsonObject()
        json.addProperty("uuid", fisher.uuid.toString())
        json.addProperty("name", fisher.name)
        json.addProperty("xp", fisher.xp)

        return json
    }

    override fun deserialize(jsonElement: JsonElement, type: Type, context: JsonDeserializationContext): Fisher? {
        if (jsonElement is JsonObject) {
            val json: JsonObject = jsonElement
            val uuid: UUID = UUID.fromString(json.get("uuid").asString)
            val name: String = json.get("name").asString
            val xp: Long = if (json.has("xp")) json.get("xp").asLong else 0L

            return Fisher(uuid, name, xp)
        }
        return null
    }
}
