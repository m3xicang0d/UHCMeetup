/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu.protocol.event

import org.bukkit.event.HandlerList
import org.bukkit.entity.Player
import org.bukkit.event.player.PlayerEvent

class PlayerCloseInventoryEvent(player: Player) : PlayerEvent(player) {

    private val instanceHandlers: HandlerList = handlerList

    override fun getHandlers(): HandlerList {
        return instanceHandlers
    }

    companion object {
        var handlerList: HandlerList = HandlerList()
    }

}