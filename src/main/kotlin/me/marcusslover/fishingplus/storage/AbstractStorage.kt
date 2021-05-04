/*
 * Copyright (c) 2021.
 * Under GNU General Public License v3.0.
 */

package me.marcusslover.fishingplus.storage

import com.google.gson.JsonObject
import me.marcusslover.fishingplus.FishingPlus
import me.marcusslover.fishingplus.exception.InvalidCredentialException
import me.marcusslover.fishingplus.fisher.Fisher
import me.marcusslover.fishingplus.storage.StorageCenter.Type
import me.marcusslover.fishingplus.utils.JsonUtil
import java.util.*
import java.util.logging.Level

abstract class AbstractStorage(protected val plugin: FishingPlus, private val type: Type) {
    private val fisherClass: Class<Fisher> = Fisher::class.java
    protected val collectionName: String = "FishingPlus"

    protected fun <T> validate(obj: T?, name: String): T {
        // Escape from unchecked casting
        if (obj == null) throw InvalidCredentialException("$name cannot be null")
        return obj
    }

    protected fun initializeUser(data: JsonObject, uuid: UUID, name: String): JsonObject {
        // Initial uuid setup.
        if (data.size() <= 0) {
            data.addProperty("uuid", uuid.toString())
        }
        // Dynamic name.
        data.addProperty("name", name)
        return data
    }

    protected fun toJson(fisher: Fisher): JsonObject {
        return JsonUtil.gson.toJsonTree(fisher, fisherClass) as JsonObject
    }

    protected fun fromJson(jsonString: String): Fisher {
        return JsonUtil.gson.fromJson(jsonString, fisherClass)
    }

    protected fun fromJson(json: JsonObject): Fisher {
        return JsonUtil.gson.fromJson(json, fisherClass)
    }

    abstract fun load(uuid: UUID, name: String): Fisher
    abstract fun save(fisher: Fisher)

    fun info(log: String) {
        log(Level.INFO, log)
    }

    fun warning(log: String) {
        log(Level.WARNING, log)
    }

    fun log(level: Level, log: String) {
        plugin.logger.log(level, "[${type.name}] $log")
    }
}