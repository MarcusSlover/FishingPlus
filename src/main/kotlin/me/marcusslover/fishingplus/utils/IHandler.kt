/*
 * Copyright (c) 2021.
 * Under GNU General Public License v3.0.
 */

package me.marcusslover.fishingplus.utils

/**
 * Handles elements of the holder.
 */
interface IHandler<T> : IHolder<T> {
    fun add(element: T)
    fun remove(element: T)
}