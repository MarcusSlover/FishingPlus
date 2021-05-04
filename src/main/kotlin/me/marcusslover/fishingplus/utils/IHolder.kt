/*
 * Copyright (c) 2021.
 * Under GNU General Public License v3.0.
 */

package me.marcusslover.fishingplus.utils

import java.util.*
import java.util.function.Predicate

/**
 * Holds some elements.
 */
interface IHolder<T> {
    fun find(predicate: Predicate<in T>): Optional<T>
    fun all(): MutableList<T>
}