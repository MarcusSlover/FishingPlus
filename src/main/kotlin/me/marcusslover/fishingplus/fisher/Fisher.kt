package me.marcusslover.fishingplus.fisher

import java.util.*

data class Fisher(val uuid: UUID, val name: String, var xp: Long) {
    companion object Factory {
        @JvmStatic
        fun empty(uuid: UUID, name: String): Fisher {
            return Fisher(uuid, name, 0L)
        }
    }
}
