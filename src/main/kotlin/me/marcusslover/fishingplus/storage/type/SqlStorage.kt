/*
 * Copyright (c) 2021.
 * Under GNU General Public License v3.0.
 */

package me.marcusslover.fishingplus.storage.type

import me.marcusslover.fishingplus.FishingPlus
import me.marcusslover.fishingplus.fisher.Fisher
import me.marcusslover.fishingplus.storage.AbstractStorage
import me.marcusslover.fishingplus.storage.StorageCenter.Credential
import me.marcusslover.fishingplus.storage.StorageCenter.Type
import java.util.*

class SqlStorage(plugin: FishingPlus, type: Type, credential: Credential) : AbstractStorage(plugin, type) {
    init {

    }

    override fun load(uuid: UUID, name: String): Fisher {
        TODO("Not yet implemented")
        return Fisher.empty(uuid, name)
    }

    override fun save(fisher: Fisher) {
        TODO("Not yet implemented")
    }
}