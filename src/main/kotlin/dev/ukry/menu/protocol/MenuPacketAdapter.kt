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
import dev.ukry.meetup.Burrito
import dev.ukry.menu.Menu

object MenuPacketAdapter : PacketAdapter(Burrito.getInstance(), PacketType.Play.Client.CLOSE_WINDOW) {

    override fun onPacketReceiving(event: PacketEvent) {
        if (Menu.currentlyOpenedMenus.containsKey(event.player.uniqueId)) {
            Menu.currentlyOpenedMenus[event.player.uniqueId]?.manualClose = true
        }
    }

}