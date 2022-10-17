package dev.mexican.meetup.game.listener

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.game.state.GameState
import net.md_5.bungee.api.ChatMessageType
import net.minecraft.server.v1_8_R3.IChatBaseComponent
import net.minecraft.server.v1_8_R3.PacketPlayOutChat
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Pig
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityDamageEvent
import org.bukkit.event.vehicle.VehicleEnterEvent
import org.bukkit.event.vehicle.VehicleExitEvent


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

    @EventHandler
    fun onPlayerMount(event : VehicleEnterEvent) {
        if(event.entered !is Player) return
        val player = event.entered as Player
        val packet = PacketPlayOutChat(IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + "" + "\"}"), 2.toByte())
        (player as CraftPlayer).handle.playerConnection.sendPacket(packet)
    }
}