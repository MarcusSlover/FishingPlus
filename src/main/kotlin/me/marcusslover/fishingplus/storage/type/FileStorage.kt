/*
 * Copyright (c) 2021.
 * Under GNU General Public License v3.0.
 */

package me.marcusslover.fishingplus.storage.type

import com.google.gson.JsonObject
import me.marcusslover.fishingplus.FishingPlus
import me.marcusslover.fishingplus.fisher.Fisher
import me.marcusslover.fishingplus.storage.AbstractStorage
import me.marcusslover.fishingplus.storage.StorageCenter
import me.marcusslover.fishingplus.utils.FileUtil
import java.io.File
import java.util.*

class FileStorage(plugin: FishingPlus, type: StorageCenter.Type) : AbstractStorage(plugin, type) {

    override fun load(uuid: UUID, name: String): Fisher {
        val file = File(plugin.data, "$uuid.json")
        val data = FileUtil.readJson(file, JsonObject()) as JsonObject
        return this.fromJson(this.initializeUser(data, uuid, name))
    }

    override fun save(fisher: Fisher) {
        val file = File(plugin.data, "${fisher.uuid}.json")
        FileUtil.writeJson(file, this.toJson(fisher))
    }
}