package me.marcusslover.fishingplus.utils

interface IRegistry<T> : IHolder<T> {
    fun register(element: T)
}