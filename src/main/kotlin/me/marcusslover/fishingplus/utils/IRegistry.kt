/*
 * Copyright (c) 2021.
 * Under GNU General Public License v3.0.
 */

package me.marcusslover.fishingplus.utils

interface IRegistry<T> : IHolder<T> {
    fun register(element: T)
}