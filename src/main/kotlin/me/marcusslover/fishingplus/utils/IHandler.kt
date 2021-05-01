package me.marcusslover.fishingplus.utils

import java.util.*
import java.util.function.Predicate

interface IHandler<T> {
    fun add(element: T)
    fun remove(element: T)
    fun find(predicate: Predicate<in T>): Optional<T>
    fun all(): MutableList<T>
}