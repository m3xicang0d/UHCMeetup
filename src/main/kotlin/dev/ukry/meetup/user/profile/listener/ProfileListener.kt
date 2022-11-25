package dev.ukry.meetup.user.profile.listener

import dev.ukry.meetup.Burrito
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.AsyncPlayerPreLoginEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @author UKry
 * Created: 14/11/2022
 * Project UHCMeetup
 **/

class ProfileListener : Listener {

    @EventHandler
    fun onPlayerPreJoin(event : AsyncPlayerPreLoginEvent) {
        val profile = Burrito.getInstance().profileHandler.getOrCreateProfile(event.uniqueId)
        val ser = profile.serialize()
        Burrito.getInstance().storageHandler.storage.save("profile", ser)
        println(ser)
        //This is to try
    }

    @EventHandler
    fun onPlayerLeave(event : PlayerQuitEvent) {
    }
}