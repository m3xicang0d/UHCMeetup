/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu

import dev.ukry.menu.utils.ItemBuilder
import dev.ukry.menu.utils.Tasks
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.inventory.*
import org.bukkit.event.player.PlayerQuitEvent

object ButtonListeners : Listener {

    @EventHandler(priority = EventPriority.MONITOR)
    fun onInventoryDragEvent(event: InventoryDragEvent) {
        val openMenu = Menu.currentlyOpenedMenus[event.whoClicked.uniqueId]
        if (openMenu != null) {
            if (event.inventory != event.view.topInventory) {
                event.isCancelled = true
                return
            }

            // check if dragging in both the menu and their own inventory
            // by comparing max used slot to max slots
            if (event.newItems.maxByOrNull { it.key }!!.key >= event.view.topInventory.size) {
                event.isCancelled = true
                return
            }

            if (openMenu.acceptsDraggedItems(event.whoClicked as Player, event.newItems)) {
                if (openMenu.updateAfterClick) {
                    openMenu.openMenu(event.whoClicked as Player)
                }
            } else {
                event.isCancelled = true
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onButtonPress(event: InventoryClickEvent) {
        val player = event.whoClicked as Player

        val openMenu = Menu.currentlyOpenedMenus[player.uniqueId]
        if (openMenu != null) {
            if (event.click == ClickType.DOUBLE_CLICK) {
                event.isCancelled = true
                return
            }

            // handle bottom mechanics (edit own inventory)
            if (!openMenu.keepBottomMechanics) {
                if (event.clickedInventory != event.view.topInventory) {
                    event.isCancelled = true
                    return
                }
            }

            if (Button.clickCooldown.containsKey(player.uniqueId)) {
                if (System.currentTimeMillis() < Button.clickCooldown[player.uniqueId]!!) {
                    event.isCancelled = true
                    return
                }
            }

            // handle items being inserted via cursor
            if (event.cursor != null && event.cursor.type != Material.AIR && (event.click == ClickType.LEFT || event.click == ClickType.RIGHT || event.click == ClickType.MIDDLE)) {
                if (event.clickedInventory == event.view.topInventory) {
                    event.isCancelled = true

                    val itemInserted = when (event.click) {
                        ClickType.LEFT -> {
                            event.cursor
                        }
                        ClickType.RIGHT -> {
                            ItemBuilder.copyOf(event.cursor).amount(1).build()
                        }
                        ClickType.MIDDLE -> {
                            val half = (event.cursor.amount / 2).coerceAtLeast(1)
                            ItemBuilder.copyOf(event.cursor).amount(half).build()
                        }
                        else -> {
                            event.cursor
                        }
                    }

                    if (openMenu.acceptsInsertedItem(player, itemInserted, event.slot)) {
                        when (event.click) {
                            ClickType.LEFT -> {
                                event.cursor = null
                            }
                            ClickType.RIGHT -> {
                                if (event.cursor.amount == 1) {
                                    event.cursor = null
                                } else {
                                    event.cursor = ItemBuilder.copyOf(event.cursor).amount(event.cursor.amount - 1).build()
                                }
                            }
                            ClickType.MIDDLE -> {
                                val half = (event.cursor.amount - (event.cursor.amount / 2).coerceAtLeast(1)).coerceAtLeast(1)
                                ItemBuilder.copyOf(event.cursor).amount(half).build()
                            }
                            else -> {
                                event.cursor = null
                            }
                        }

                        refreshOpenMenuIfSame(player, openMenu)
                    }

                    return
                }
            }

            // handle items being inserted via shift-clicking
            if (event.slot != event.rawSlot) {
                if (event.click == ClickType.SHIFT_LEFT || event.click == ClickType.SHIFT_RIGHT) {
                    event.isCancelled = true

                    if (event.currentItem != null) {
                        if (openMenu.acceptsShiftClickedItem(event.whoClicked as Player, event.currentItem)) {
                            event.currentItem = null
                            refreshOpenMenuIfSame(player, openMenu)
                        }
                    }
                }
                return
            }

            // handle button
            if (openMenu.buttons.containsKey(event.slot)) {
                val button = openMenu.buttons[event.slot]!!
                val cancel = button.shouldCancel(player, event.slot, event.click)

                if (!cancel && (event.click == ClickType.SHIFT_LEFT || event.click == ClickType.SHIFT_RIGHT)) {
                    event.isCancelled = true

                    if (event.currentItem != null) {
                        player.inventory.addItem(event.currentItem)
                    }
                } else {
                    event.isCancelled = cancel
                }

                button.clicked(player, event.slot, event.click, event.view)

                // check if player is still in the same menu and needs to update
                if (Menu.currentlyOpenedMenus.containsKey(player.uniqueId)) {
                    val newMenu = Menu.currentlyOpenedMenus[player.uniqueId]
                    if (newMenu === openMenu && newMenu.updateAfterClick) {
                        newMenu.openMenu(player)
                    }
                }

                if (event.isCancelled) {
                    Tasks.delayed(1L) { player.updateInventory() }
                }

                return // we return here so we don't reach the block of code below
            } else {
                if (event.clickedInventory == event.view.topInventory) {
                    event.isCancelled = true
                }
            }

            // handle non-cancelling menu
            if (event.click == ClickType.SHIFT_LEFT || event.click == ClickType.SHIFT_RIGHT) {
                event.isCancelled = true

                if (openMenu.nonCancelling && event.currentItem != null) {
                    if (event.slot == event.rawSlot && event.clickedInventory == event.view.topInventory) {
                        player.openInventory.topInventory.addItem(event.currentItem)
                        event.currentItem = null
                    }
                }
            }
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    fun onInventoryClose(event: InventoryCloseEvent) {
        val player = event.player as Player

        val openMenu = Menu.currentlyOpenedMenus[player.uniqueId]
        if (openMenu != null) {
            openMenu.closed = true

            if (event.view.cursor != null) {
                event.player.inventory.addItem(event.view.cursor)
                event.view.cursor = null
            }

            openMenu.onClose(player, openMenu.manualClose)
            openMenu.manualClose = false

            Menu.currentlyOpenedMenus.remove(player.uniqueId)
        }
    }

    private fun refreshOpenMenuIfSame(player: Player, menu: Menu) {
        if (Menu.currentlyOpenedMenus.containsKey(player.uniqueId)) {
            val openMenu = Menu.currentlyOpenedMenus[player.uniqueId]
            if (openMenu === menu && openMenu.updateAfterClick) {
                openMenu.openMenu(player)
            }
        }
    }

    @EventHandler
    fun onPlayerQuitEvent(event: PlayerQuitEvent) {
        Button.clickCooldown.remove(event.player.uniqueId)
    }

}