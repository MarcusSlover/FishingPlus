package me.marcusslover.fishingplus.storage.type

import me.marcusslover.fishingplus.FishingPlus
import me.marcusslover.fishingplus.fisher.Fisher
import me.marcusslover.fishingplus.storage.AbstractStorage
import me.marcusslover.fishingplus.storage.StorageCenter.Credential
import me.marcusslover.fishingplus.storage.StorageCenter.Type
import redis.clients.jedis.JedisPool
import redis.clients.jedis.JedisPoolConfig
import redis.clients.jedis.Protocol
import java.util.*


class RedisStorage(plugin: FishingPlus, type: Type, credential: Credential) : AbstractStorage(plugin, type) {
    private var pool: JedisPool

    init {
        val host = this.validate(credential.host, "Host")
        val port = this.validate(credential.port, "Port")
        val password = this.validate(credential.password, "Password")
        pool = JedisPool(JedisPoolConfig(), host, port, Protocol.DEFAULT_TIMEOUT, password)
    }

    override fun load(uuid: UUID, name: String): Fisher {
        val fisher: Fisher

        pool.resource.use { jedis ->
            val data = jedis.hget(this.collectionName, uuid.toString())
            if (data == "nil") {
                fisher = Fisher.empty(uuid, name)

                val json = this.toJson(fisher)
                jedis.hset(this.collectionName, uuid.toString(), json.toString())
            } else {
                fisher = this.fromJson(data)
            }
        }
        return fisher
    }

    override fun save(fisher: Fisher) {
        val json = this.toJson(fisher)
        pool.resource.use { jedis ->
            jedis.hset(this.collectionName, fisher.uuid.toString(), json.toString())
        }
    }
}