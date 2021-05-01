package me.marcusslover.fishingplus.storage

import me.marcusslover.fishingplus.FishingPlus
import me.marcusslover.fishingplus.storage.type.FileStorage
import me.marcusslover.fishingplus.storage.type.MongoStorage
import me.marcusslover.fishingplus.storage.type.RedisStorage
import me.marcusslover.fishingplus.storage.type.SqlStorage
import me.marcusslover.fishingplus.utils.IStaticInstance

class StorageCenter {
    companion object Instance : IStaticInstance<StorageCenter> {

        @JvmStatic
        private lateinit var instance: StorageCenter

        @JvmStatic
        override fun get(): StorageCenter {
            return instance
        }

    }

    lateinit var data: AbstractStorage
    var state = State.PENDING

    init {
        instance = this
    }

    fun initialize(type: Type) {
        initialize(type, null)
    }

    fun initialize(type: Type, credential: Credential?) {
        val constructor = type.clazz.getConstructor()
        data = if (constructor.typeParameters.size > 2 && credential != null) {
            constructor.newInstance(FishingPlus.get(), type, credential)
        } else {
            constructor.newInstance(FishingPlus.get(), type)
        }
    }

    enum class State {
        PENDING, STABLE, LOST, INVALID
    }

    data class Credential(
        val host: String?,
        val user: String?,
        val port: Int,
        val database: String?,
        val password: String?
    )

    enum class Type(val clazz: Class<out AbstractStorage>, val credentials: Boolean) {
        FILE(FileStorage::class.java, false),
        SQL(SqlStorage::class.java, true),
        MONGODB(MongoStorage::class.java, true),
        REDIS(RedisStorage::class.java, true);

        companion object Finder {
            @JvmStatic
            fun find(id: String): Type? {
                for (value in values()) {
                    if (value.toString() == id) return value
                }
                return null
            }
        }
    }
}