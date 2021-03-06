/*
 * Copyright (c) 2021.
 * Under GNU General Public License v3.0.
 */

package me.marcusslover.fishingplus.fisher

import me.marcusslover.fishingplus.storage.AbstractStorage
import me.marcusslover.fishingplus.storage.StorageCenter
import me.marcusslover.fishingplus.utils.IHandler
import me.marcusslover.fishingplus.utils.IStaticInstance
import me.marcusslover.fishingplus.utils.TaskUtil
import java.util.*
import java.util.function.Consumer
import java.util.function.Predicate

/**
 * Keeps track of all the loaded Fisher objects.
 * As well as, controls the process of saving and loading them.
 */
class FisherHandler : IHandler<Fisher> {
    private var data: AbstractStorage // Storage
    private val list: MutableList<Fisher> = ArrayList()

    init {
        instance = this // Initialize the instance
        data = StorageCenter.get().data
    }

    fun safeLoad(uniqueId: UUID, name: String, consumer: Consumer<Fisher>) {
        TaskUtil.async {
            val fisher = load(uniqueId, name)
            TaskUtil.sync {
                if (fisher != null) {
                    this.add(fisher)
                    consumer.accept(fisher)
                }
            }
        }
    }

    fun load(uniqueId: UUID, name: String): Fisher? {
        val result = find { fisher -> fisher.uuid == uniqueId }
        if (result.isPresent) {
            return null
        }
        return data.load(uniqueId, name)
    }

    fun safeSave(uuid: UUID, consumer: Consumer<Boolean>) {
        val fisher: Fisher? = find { fisher ->
            fisher.uuid == uuid
        }.orElse(null)

        if (fisher != null) {
            TaskUtil.async {
                this.save(fisher)
                TaskUtil.sync {
                    this.remove(fisher)
                    consumer.accept(true)
                }
            }
        }
    }

    // Instance
    companion object Instance : IStaticInstance<FisherHandler> {
        @JvmStatic
        private lateinit var instance: FisherHandler

        @JvmStatic
        override fun get(): FisherHandler {
            return instance
        }
    }

    fun save(fisher: Fisher) {
        data.save(fisher)
    }

    override fun add(element: Fisher) {
        list.add(element)
    }

    override fun remove(element: Fisher) {
        list.remove(element)
    }

    override fun find(predicate: Predicate<in Fisher>): Optional<Fisher> {
        return list.stream().filter(predicate).findFirst()
    }

    override fun all(): MutableList<Fisher> {
        return list
    }
}