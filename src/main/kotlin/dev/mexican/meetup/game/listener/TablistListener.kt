package dev.mexican.meetup.game.listener

import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scoreboard.DisplaySlot

/**
 * @author UKry
 * Created: 16/10/2022
 * Project UHCMeetup
 **/

class TablistListener : Listener {

    @EventHandler(priority = EventPriority.LOWEST)
    fun onPlayerJoin(event : PlayerJoinEvent) {
        val scoreboard = event.player.scoreboard
        if(scoreboard.getObjective("Hearts") != null) return
        val objective = scoreboard.registerNewObjective("Hearts", "health")
        objective.displaySlot = DisplaySlot.PLAYER_LIST
    }
}