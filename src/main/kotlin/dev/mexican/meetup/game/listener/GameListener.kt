package dev.mexican.meetup.game.listener

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.game.state.GameState
import dev.mexican.meetup.user.state.PlayerState
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @author UKry
 * Created: 15/10/2022
 * Project UHCMeetup
 **/

class GameListener : Listener {

    @EventHandler
    fun onParticipantJoin(event : PlayerJoinEvent) {
        val game = Burrito.getInstance().gameHandler.actualGame!!
        if(game.state != GameState.WAITING && game.state != GameState.COUNTDOWN) return
        game.addParticipant(event.player)
        val profile = Burrito.getInstance().profileHandler.getOrCreateProfile(event.player)
        profile.state = PlayerState.PLAYING
    }

    @EventHandler
    fun onPlayerKill(event : PlayerDeathEvent) {
        val killed = event.entity
        val killer = killed.killer ?: return
        val killedProfile = Burrito.getInstance().profileHandler.getOrCreateProfile(killed)
        killedProfile.state = PlayerState.SPECTATING
        val killerProfile = Burrito.getInstance().profileHandler.getOrCreateProfile(killer)
        killerProfile.kills += killedProfile.kills
        val game = Burrito.getInstance().gameHandler.actualGame!!
        if(!game.isParticipant(killed)) return
        val participants = game.participants.size
        if(participants == 1) {
            game.end(killer)
        }
    }

    @EventHandler
    fun onPlayerDeath(event : PlayerDeathEvent) {
        val player = event.entity
        val killer = player.killer
        val game = Burrito.getInstance().gameHandler.actualGame!!
        val participants = game.participants.size
        if(!game.isParticipant(player)) return
        if(participants == 1) {
            if(killer == null) {
                game.end(Bukkit.getPlayer(game.participants[0]))
            } else {
                game.end(killer)
            }
        }
    }
    @EventHandler
    fun onPlayerLeave(event : PlayerQuitEvent) {
        val player = event.player
        val game = Burrito.getInstance().gameHandler.actualGame!!
        val participants = game.participants.size
        if(!game.isParticipant(player)) return
        if(participants == 1) {
            game.end(Bukkit.getPlayer(game.participants[0]))
        }
    }


}