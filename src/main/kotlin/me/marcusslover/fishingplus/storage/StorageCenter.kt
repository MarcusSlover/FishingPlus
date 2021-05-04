package me.marcusslover.fishingplus.storage

import me.marcusslover.fishingplus.FishingPlus
import me.marcusslover.fishingplus.storage.type.FileStorage
import me.marcusslover.fishingplus.storage.type.MongoStorage
import me.marcusslover.fishingplus.storage.type.RedisStorage
import me.marcusslover.fishingplus.storage.type.SqlStorage
import me.marcusslover.fishingplus.utils.IStaticInstance

class StorageCenter {
    lateinit var data: AbstractStorage
    var state = State.PENDING

    init {
        instance = this // Initialize the instance
    }

    fun initialize(type: Type) {
        initialize(type, null)
    }

    /**
     * Initializes a storage of the given type.
     * Some storages require valid credentials.
     */
    fun initialize(type: Type, credential: Credential?) {
        val constructor = type.clazz.getConstructor()
        data = if (constructor.typeParameters.size > 2 && credential != null) {
            constructor.newInstance(FishingPlus.get(), type, credential)
        } else {
            constructor.newInstance(FishingPlus.get(), type)
        }
    }

    /**
     * Represents the state of the plugin
     * in term of the storage.
     */
    enum class State {
        /**
         * The plugin is still initializing the storage.
         */
        PENDING,

        /**
         * The storage is working fine.
         * E.g. connection to the database is stable.
         */
        STABLE,

        /**
         * The storage stopped working.
         * E.g. the database connection was lost or
         * the redis server was stopped.
         */
        LOST,

        /**
         * The given credentials in the process
         * of initializing the storage were invalid.
         * Therefore, there's no way to save/load data.
         */
        INVALID
    }

    /**
     * Simple data class for credentials.
     */
    data class Credential(
        val host: String?,
        val user: String?,
        val port: Int,
        val database: String?,
        val password: String?
    )

    /**
     * All types of storages.
     *
     * @param clazz Class of the storage.
     * @param credentials True if this type of storage requires
     * a set of credentials, false if credentials are not needed.
     */
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

    // Instance
    companion object Instance : IStaticInstance<StorageCenter> {

        @JvmStatic
        private lateinit var instance: StorageCenter

        @JvmStatic
        override fun get(): StorageCenter {
            return instance
        }

    }
}
