package me.marcusslover.fishingplus.utils

import me.marcusslover.fishingplus.FishingPlus
import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitScheduler

class TaskUtil {
    companion object Util {
        @JvmStatic
        private val scheduler: BukkitScheduler = Bukkit.getScheduler()

        @JvmStatic
        fun async(runnable: Runnable) {
            scheduler.runTaskAsynchronously(FishingPlus.get(), runnable)
        }

        @JvmStatic
        fun sync(runnable: Runnable) {
            scheduler.runTask(FishingPlus.get(), runnable)
        }
    }
}