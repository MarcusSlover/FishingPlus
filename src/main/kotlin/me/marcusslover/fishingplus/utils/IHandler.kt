package me.marcusslover.fishingplus.utils

/**
 * Handles elements of the holder.
 */
interface IHandler<T> : IHolder<T> {
    fun add(element: T)
    fun remove(element: T)
}