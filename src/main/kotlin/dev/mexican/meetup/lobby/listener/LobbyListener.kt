package dev.mexican.meetup.lobby.listener

import dev.mexican.meetup.Burrito
import org.bukkit.Bukkit
import org.bukkit.Difficulty
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
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
        Bukkit.getConsoleSender().sendMessage("-".repeat(20))
        Bukkit.getConsoleSender().sendMessage("Lobby world configured!")
        Bukkit.getConsoleSender().sendMessage("-".repeat(20))
    }

}