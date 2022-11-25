package dev.ukry.meetup.lobby.listener

import dev.ukry.meetup.Burrito
import dev.ukry.meetup.game.state.GameState
import org.bukkit.Bukkit
import org.bukkit.Difficulty
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.world.WorldLoadEvent

/**
 * @author UKry
 * Created: 04/10/2022
 * Project UHCMeetup
 **/

class LobbyListener : Listener {

    @EventHandler
    fun onLobbyLoad(event : WorldLoadEvent) {
        val world = event.world
        if(!world.name.equals(Burrito.getInstance().lobbyHandler.lobbyName, true)) return
        world.worldBorder.setCenter(0.0, 0.0)
        world.difficulty = Difficulty.PEACEFUL
        world.pvp = false
        val block = world.getBlockAt(0, 100, 0)
        if(block.type == Material.AIR) {
            block.type = Material.GLASS
            world.setSpawnLocation(0, 100, 0)
        }
        Bukkit.getConsoleSender().sendMessage("-".repeat(20))
        Bukkit.getConsoleSender().sendMessage("Lobby world configured!")
        Bukkit.getConsoleSender().sendMessage("-".repeat(20))
    }

    @EventHandler
    fun onPlayerJoin(event : PlayerJoinEvent) {
        val game = Burrito.getInstance().gameHandler.actualGame ?: return
        if(game.state != GameState.WAITING) return
        Burrito.getInstance().lobbyHandler.sendToLobby(event.player)
    }

    @EventHandler
    fun antiVoid(event : EntityDamageEvent) {
        if(event.entity !is Player) return
        val state = Burrito.getInstance().gameHandler.actualGame!!.state
        if(state == GameState.WAITING) {
            event.isCancelled = true
            Burrito.getInstance().lobbyHandler.sendToLobby(event.entity as Player)
        } else event.isCancelled = state == GameState.COUNTDOWN
    }


}