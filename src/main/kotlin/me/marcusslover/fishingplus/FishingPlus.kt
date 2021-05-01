package me.marcusslover.fishingplus

import me.marcusslover.fishingplus.exception.StorageException
import me.marcusslover.fishingplus.listener.FishingListener
import me.marcusslover.fishingplus.listener.ServerListener
import me.marcusslover.fishingplus.storage.StorageCenter
import me.marcusslover.fishingplus.utils.IStaticInstance
import org.bukkit.Bukkit
import org.bukkit.event.Listener
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class FishingPlus : JavaPlugin() {
    companion object Instance : IStaticInstance<FishingPlus> {
        @JvmStatic
        private lateinit var instance: FishingPlus

        @JvmStatic
        override fun get(): FishingPlus {
            return instance
        }
    }

    val pluginManager = Bukkit.getPluginManager()
    val storageCenter = StorageCenter()
    val data = File(dataFolder, "storage")

    override fun onLoad() {
        instance = this
    }

    override fun onEnable() {
        if (!dataFolder.exists()) {
            dataFolder.mkdirs()
            logger.info("Created the data folder!")
        }
        // Config
        saveDefaultConfig()

        // Data directory
        if (!data.exists()) data.mkdirs()

        val storageType: String? = config.getString("storage", "FILE")
        var state = StorageCenter.State.INVALID

        if (storageType != null) {
            val type = StorageCenter.Type.find(storageType)
            if (type != null) {
                state = StorageCenter.State.PENDING
                logger.info("Initializing $storageType storage...")

                try {
                    // If the storage needs a set of valid credentials
                    if (type.credentials) {
                        val host: String? = config.getString("host", "localhost")
                        val user: String? = config.getString("user", "admin")
                        val port: Int = config.getInt("port", 3303)
                        val password: String? = config.getString("password", "admin")
                        val database: String? = config.getString("database", "")
                        val credential = StorageCenter.Credential(host, user, port, password, database)

                        storageCenter.initialize(type, credential)
                    } else {
                        storageCenter.initialize(type)
                    }
                    state = StorageCenter.State.STABLE
                } catch (exception: StorageException) {
                    exception.printStackTrace()
                }
            }
        }

        if (state == StorageCenter.State.INVALID) {
            logger.warning("Could not recognize the \"$storageType\" storage type!")
            this.isEnabled = false
            return
        }

        addListener(ServerListener())
        addListener(FishingListener())
    }

    private fun addListener(listener: Listener) {
        pluginManager.registerEvents(listener, this)
    }
}