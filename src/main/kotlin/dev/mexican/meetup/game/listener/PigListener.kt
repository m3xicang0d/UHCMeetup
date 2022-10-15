package dev.mexican.meetup.game.listener

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.game.state.GameState
import org.bukkit.entity.Pig
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.vehicle.VehicleExitEvent
import org.spigotmc.event.entity.EntityDismountEvent

/**
 * @author UKry
 * Created: 15/10/2022
 * Project UHCMeetup
 **/

class PigListener : Listener {

    @EventHandler
    fun onPlayerDismount(event : VehicleExitEvent) {
        val game = Burrito.getInstance().gameHandler.actualGame!!
        if(game.state != GameState.PLAYING) {
            event.isCancelled = true
        }
    }

    @EventHandler
    fun onHitPig(event : EntityDamageEvent) {
        if(event.entity !is Pig) return
        event.isCancelled = true
    }
}