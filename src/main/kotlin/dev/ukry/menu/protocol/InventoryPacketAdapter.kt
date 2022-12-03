/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu.protocol

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import com.comphenix.protocol.wrappers.EnumWrappers
import dev.ukry.meetup.Burrito
import dev.ukry.menu.protocol.event.PlayerCloseInventoryEvent
import dev.ukry.menu.protocol.event.PlayerOpenInventoryEvent
import org.bukkit.Bukkit
import java.util.*

object InventoryPacketAdapter : PacketAdapter(Burrito.getInstance(), PacketType.Play.Client.CLIENT_COMMAND, PacketType.Play.Client.CLOSE_WINDOW) {

    override fun onPacketReceiving(event: PacketEvent?) {
        val player = event!!.player
        val packet = event.packet

        if (packet.type === PacketType.Play.Client.CLIENT_COMMAND && packet.clientCommands.size() != 0 && packet.clientCommands.read(0) == EnumWrappers.ClientCommand.OPEN_INVENTORY_ACHIEVEMENT) {
            if (!currentlyOpen.contains(player.uniqueId)) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Burrito.getInstance()) { Bukkit.getPluginManager().callEvent(
                    PlayerOpenInventoryEvent(player)
                ) }
            }

            currentlyOpen.add(player.uniqueId)
        } else if (packet.type === PacketType.Play.Client.CLOSE_WINDOW) {
            if (currentlyOpen.contains(player.uniqueId)) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(Burrito.getInstance()) { Bukkit.getPluginManager().callEvent(
                    PlayerCloseInventoryEvent(player)
                ) }
            }

            currentlyOpen.remove(player.uniqueId)
        }
    }

    @JvmStatic
    var currentlyOpen: HashSet<UUID> = HashSet()

}
