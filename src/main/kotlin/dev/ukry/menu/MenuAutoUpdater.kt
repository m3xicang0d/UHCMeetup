/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu

import dev.ukry.menu.utils.MinecraftReflection
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerQuitEvent
import java.util.*
import java.util.concurrent.ConcurrentHashMap

object MenuAutoUpdater : Runnable, Listener {

    private val updateTimestamps: MutableMap<UUID, Long> = ConcurrentHashMap()

    override fun run() {
        for ((uuid, openMenu) in Menu.currentlyOpenedMenus.entries) {
            try {
                val player = Bukkit.getPlayer(uuid)
                if (player == null || !player.isOnline) {
                    Menu.currentlyOpenedMenus.remove(uuid)
                    continue
                }

                if (openMenu.closed) {
                    continue
                }

                if (openMenu.autoUpdate) {
                    updateTimestamps.putIfAbsent(player.uniqueId, System.currentTimeMillis())

                    if (System.currentTimeMillis() - updateTimestamps[player.uniqueId]!! >= openMenu.getAutoUpdateTicks()) {
                        openMenu.preAutoUpdate()
                        updateTimestamps[player.uniqueId] = System.currentTimeMillis()
                        openMenu.openMenu(player)
                    }
                } else if (openMenu.animated) {
                    val openInventory = player.openInventory?.topInventory ?: continue

                    if (!MinecraftReflection.isCustomInventory(openInventory)) {
                        continue
                    }

                    var updateInventory = false
                    for ((slot, button) in openMenu.buttons) {
                        if (slot >= openInventory.size - 1) {
                            continue
                        }

                        if (button.isAnimated()) {
                            if (System.currentTimeMillis() - button.lastAnimation >= button.getAnimationInterval()) {
                                updateInventory = true
                                button.preAnimationUpdate()
                                button.lastAnimation = System.currentTimeMillis()
                                openInventory.setItem(slot, button.getButtonItem(player))
                            }
                        }
                    }

                    if (updateInventory) {
                        player.updateInventory()
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @EventHandler
    fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        updateTimestamps.remove(event.player.uniqueId)
    }

}