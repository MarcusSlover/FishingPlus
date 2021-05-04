/*
 * Copyright (c) 2021.
 * Under GNU General Public License v3.0.
 */

package me.marcusslover.fishingplus.listener

import me.marcusslover.fishingplus.fisher.FisherHandler
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerQuitEvent

class ServerListener : Listener {
    private val handler = FisherHandler.get()

    @EventHandler(priority = EventPriority.HIGHEST)
    fun onLogin(event: AsyncPlayerPreLoginEvent) {
        if (event.loginResult == AsyncPlayerPreLoginEvent.Result.ALLOWED) {
            handler.load(event.uniqueId, event.name)
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    fun onLeave(event: PlayerQuitEvent) {
        handler.safeSave(event.player.uniqueId, consumer = {})
    }
}