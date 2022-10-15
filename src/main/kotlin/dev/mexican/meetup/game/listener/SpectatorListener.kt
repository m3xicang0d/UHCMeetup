package dev.mexican.meetup.game.listener

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.game.state.GameState
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.metadata.FixedMetadataValue
import java.util.Objects

/**
 * @author UKry
 * Created: 15/10/2022
 * Project UHCMeetup
 **/

class SpectatorListener : Listener {

    @EventHandler
    fun onPlayerJoin(event : PlayerJoinEvent) {
        val game = Burrito.getInstance().gameHandler.actualGame!!
        if(game.state != GameState.COUNTDOWN && game.state != GameState.PLAYING && game.state != GameState.ENDING) return
        game.addSpectator(event.player)
        event.player.setMetadata("SPECTATOR", FixedMetadataValue(Burrito.getInstance(), true))
        game.participants.stream()
            .map(Bukkit::getPlayer)
            .filter(Objects::nonNull)
            .forEach {
                it.hidePlayer(event.player)
            }
    }
}