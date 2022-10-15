package dev.mexican.meetup.game.listener

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.game.state.GameState
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent

/**
 * @author UKry
 * Created: 15/10/2022
 * Project UHCMeetup
 **/

class GameListener : Listener {

    @EventHandler
    fun onPlayerJoin(event : PlayerJoinEvent) {
        val game = Burrito.getInstance().gameHandler.actualGame!!
        if(game.state != GameState.WAITING) return
        game.addParticipant(event.player)
    }
}