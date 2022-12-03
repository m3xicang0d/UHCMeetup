/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu

import dev.ukry.meetup.Burrito
import dev.ukry.menu.utils.MinecraftReflection
import dev.ukry.menu.utils.Reflection
import dev.ukry.menu.utils.Tasks
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.lang.reflect.Method
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.math.ceil

abstract class Menu {

    var buttons: ConcurrentHashMap<Int, Button> = ConcurrentHashMap()
    var autoUpdate: Boolean = false
    var autoUpdateInterval: Long = 500L
    var animated: Boolean = false
    var updateAfterClick: Boolean = false
    var keepBottomMechanics: Boolean = true
    var placeholder: Boolean = false
    var nonCancelling: Boolean = false
    var async: Boolean = false
    var manualClose: Boolean = true
    var closed: Boolean = false
    private var staticTitle: String

    constructor() {
        staticTitle = " "
    }

    constructor(title: String) {
        staticTitle = title
    }

    abstract fun getButtons(player: Player): Map<Int, Button>

    open fun getTitle(player: Player): String {
        return staticTitle
    }

    open fun onOpen(player: Player) {}

    open fun onClose(player: Player, manualClose: Boolean) {}

    internal fun createInventory(player: Player): Inventory {
        var title = ChatColor.translateAlternateColorCodes('&', getTitle(player))
        if (title.length > 32) {
            title = title.substring(0, 31)
        }

        val invButtons = getButtons(player)
        var size = size(invButtons)

        val minSize = getMinSize()
        if (minSize != -1) {
            if (minSize > size) {
                size = minSize
            }
        }

        val inv = Bukkit.createInventory(null, size, title)

        for (buttonEntry in invButtons.entries) {
            if (buttonEntry.key >= size) {
                continue
            }

            buttons[buttonEntry.key] = buttonEntry.value
            inv.setItem(buttonEntry.key, buttonEntry.value.getButtonItem(player))
        }

        if (placeholder) {
            val placeholder = Button.placeholder(Material.STAINED_GLASS_PANE, 15.toByte(), " ")

            for (index in 0 until size(invButtons)) {
                if (invButtons[index] == null) {
                    buttons[index] = placeholder
                    inv.setItem(index, placeholder.getButtonItem(player))
                }
            }
        }

        return inv
    }

    fun openMenu(player: Player) {
        if (async) {
            Tasks.async {
                try {
                    asyncLoadResources(player) { successfulLoad ->
                        if (successfulLoad) {
                            val inv = createInventory(player)

                            try {
                                openCustomInventory(player, inv)
                            } catch (ex: Exception) {
                                ex.printStackTrace()
                            }
                        } else {
                            player.sendMessage("${ChatColor.RED}Couldn't load menu...")
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    player.sendMessage("${ChatColor.RED}Couldn't load menu...")
                }
            }
        } else {
            val inv = createInventory(player)

            try {
                openCustomInventory(player, inv)
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    private fun openCustomInventory(player: Player, inv: Inventory) {
        val openInventory = player.openInventory
        if (openInventory != null) {
            // check if top inv size is the same as new menu size and if the titles match
            if (openInventory.topInventory != null && openInventory.topInventory.size == inv.size && openInventory.topInventory.title == inv.title) {
                openInventory.topInventory.contents = inv.contents
                return
            }

            manualClose = false
            player.closeInventory()
        }

        val entityPlayer = MinecraftReflection.getHandle(player)

        if (Bukkit.isPrimaryThread()) {
            if (OPEN_CUSTOM_INVENTORY_METHOD_LEGACY != null) {
                OPEN_CUSTOM_INVENTORY_METHOD_LEGACY.invoke(player, inv, entityPlayer, 0)
            } else {
                OPEN_CUSTOM_INVENTORY_METHOD!!.invoke(player, inv, entityPlayer, getWindowType(inv.size))
            }

            update(player)

            if (openInventory != null) {
                player.updateInventory()
            }
        } else {
            Burrito.getInstance().server.scheduler.runTaskLater(Burrito.getInstance(), {
                if (OPEN_CUSTOM_INVENTORY_METHOD_LEGACY != null) {
                    OPEN_CUSTOM_INVENTORY_METHOD_LEGACY.invoke(player, inv, entityPlayer, 0)
                } else {
                    OPEN_CUSTOM_INVENTORY_METHOD!!.invoke(player, inv, entityPlayer, getWindowType(inv.size))
                }

                update(player)

                if (openInventory != null) {
                    player.updateInventory()
                }
            }, 1L)
        }
    }

    private fun getWindowType(size: Int): String {
        return "minecraft:chest"
    }

    fun update(player: Player) {
        // set open menu reference to this menu
        currentlyOpenedMenus[player.uniqueId] = this

        // call abstract onOpen
        closed = false
        onOpen(player)
    }

    open fun size(buttons: Map<Int, Button>): Int {
        var highest = 0
        for (buttonValue in buttons.keys) {
            if (buttonValue > highest) {
                highest = buttonValue
            }
        }
        return (ceil((highest + 1) / 9.0) * 9.0).toInt()
    }

    open fun getMinSize(): Int {
        return -1
    }

    fun getSlot(x: Int, y: Int): Int {
        return 9 * y + x
    }

    open fun asyncLoadResources(player: Player, callback: (Boolean) -> Unit) {}

    open fun acceptsInsertedItem(player: Player, itemStack: ItemStack, slot: Int): Boolean {
        return false
    }

    open fun acceptsShiftClickedItem(player: Player, itemStack: ItemStack): Boolean {
        return false
    }

    open fun acceptsDraggedItems(player: Player, items: Map<Int, ItemStack>): Boolean {
        return false
    }

    open fun getAutoUpdateTicks(): Long {
        return autoUpdateInterval
    }

    open fun preAutoUpdate() {}

    companion object {

        @JvmStatic
        var currentlyOpenedMenus: MutableMap<UUID, Menu> = ConcurrentHashMap()

        private val OPEN_CUSTOM_INVENTORY_METHOD_LEGACY: Method? = Reflection.getDeclaredMethod(
            MinecraftReflection.getCraftBukkitClass("entity.CraftHumanEntity")!!,
            "openCustomInventory",
            Inventory::class.java,
            MinecraftReflection.getNMSClass("EntityPlayer")!!,
            Integer.TYPE
        )

        private val OPEN_CUSTOM_INVENTORY_METHOD: Method? = Reflection.getDeclaredMethod(
            MinecraftReflection.getCraftBukkitClass("entity.CraftHumanEntity")!!,
            "openCustomInventory",
            Inventory::class.java,
            MinecraftReflection.getNMSClass("EntityPlayer")!!,
            String::class.java
        )

        @JvmStatic
        fun styleAction(color: ChatColor, action: String, text: String): String {
            return color.toString() + ChatColor.BOLD + action + ChatColor.RESET + color + " " + text
        }

    }

}