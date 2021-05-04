/*
 * Copyright (c) 2021.
 * Under GNU General Public License v3.0.
 */

package me.marcusslover.fishingplus.collection

import me.marcusslover.fishingplus.collection.type.FishCollection
import me.marcusslover.fishingplus.utils.IRegistry
import java.util.*
import java.util.function.Predicate

class CollectionRegistry : IRegistry<FishCollection<*>> {
    private val list = mutableListOf<FishCollection<*>>()

    override fun find(predicate: Predicate<in FishCollection<*>>): Optional<FishCollection<*>> {
        return list.stream().filter(predicate).findFirst()
    }

    override fun all(): MutableList<FishCollection<*>> {
        return list
    }

    override fun register(element: FishCollection<*>) {
        list.add(element)
    }

}