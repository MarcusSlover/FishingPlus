package me.marcusslover.fishingplus.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerFishEvent

class FishingListener : Listener {

    @EventHandler
    fun onFish(event: PlayerFishEvent) {
        if (event.state == PlayerFishEvent.State.FISHING) {

        }
    }
}