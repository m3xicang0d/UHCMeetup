/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu

import com.google.common.base.Joiner
import org.apache.commons.lang.StringUtils
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.Sound
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.ItemMeta
import java.util.*
import java.util.concurrent.ConcurrentHashMap

abstract class Button {

    var preserveName: Boolean = false
    var lastAnimation: Long = System.currentTimeMillis()

    open fun getName(player: Player): String {
        return " "
    }

    open fun getDescription(player: Player): List<String> {
        return emptyList()
    }

    open fun getMaterial(player: Player): Material {
        return Material.AIR
    }

    open fun getDamageValue(player: Player): Byte {
        return 0
    }

    open fun applyMetadata(player: Player, itemMeta: ItemMeta): ItemMeta? {
        return null
    }

    open fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {}

    open fun shouldCancel(player: Player, slot: Int, clickType: ClickType): Boolean {
        return true
    }

    open fun getAmount(player: Player): Int {
        return 1
    }

    open fun getButtonItem(player: Player): ItemStack {
        val buttonItem = ItemStack(getMaterial(player), getAmount(player), getDamageValue(player).toShort())
        val meta = buttonItem.itemMeta

        if (meta != null) {
            if (!preserveName) {
                val displayName = getName(player)
                if (displayName.isNotEmpty()) {
                    meta.displayName = ChatColor.translateAlternateColorCodes('&', getName(player))
                }
            }

            meta.lore = getDescription(player)

            val appliedMeta = applyMetadata(player, meta) ?: meta
            buttonItem.itemMeta = appliedMeta
        }

        return buttonItem
    }

    open fun isAnimated(): Boolean {
        return false
    }

    open fun getAnimationInterval(): Long {
        return 500L
    }

    open fun preAnimationUpdate() {

    }

    companion object {
        val BAR = "${ChatColor.GRAY}${ChatColor.STRIKETHROUGH}${StringUtils.repeat("-", 32)}"

        internal val clickCooldown: MutableMap<UUID, Long> = ConcurrentHashMap()

        @JvmStatic
        fun cooldown(player: Player, duration: Long) {
            clickCooldown[player.uniqueId] = System.currentTimeMillis() + duration
        }

        @JvmStatic
        fun placeholder(material: Material, data: Byte, vararg title: String): Button {
            return placeholder(material, data, Joiner.on(" ").join(title))
        }

        @JvmStatic
        fun placeholder(material: Material): Button {
            return placeholder(material, " ")
        }

        @JvmStatic
        fun placeholder(material: Material, title: String): Button {
            return placeholder(material, 0.toByte(), title)
        }

        @JvmStatic
        fun placeholder(item: ItemStack): Button {
            return object : Button() {
                override fun getName(player: Player): String {
                    return if (item.hasItemMeta() && item.itemMeta.hasDisplayName()) {
                        item.itemMeta.displayName
                    } else {
                        ""
                    }
                }

                override fun getDescription(player: Player): List<String> {
                    return if (item.hasItemMeta() && item.itemMeta.hasLore()) {
                        item.itemMeta.lore
                    } else {
                        emptyList()
                    }
                }

                override fun getMaterial(player: Player): Material {
                    return item.type
                }

                override fun getDamageValue(player: Player): Byte {
                    return item.durability.toByte()
                }
            }
        }

        @JvmStatic
        fun placeholder(material: Material, data: Byte, title: String): Button {
            return object : Button() {
                override fun getName(player: Player): String {
                    return title
                }

                override fun getDescription(player: Player): List<String> {
                    return listOf()
                }

                override fun getMaterial(player: Player): Material {
                    return material
                }

                override fun getDamageValue(player: Player): Byte {
                    return data
                }
            }
        }

        @JvmStatic
        protected fun fromItem(item: ItemStack?): Button {
            return object : Button() {
                override fun getButtonItem(player: Player): ItemStack {
                    return item ?: ItemStack(Material.AIR)
                }
            }
        }

        @JvmStatic
        fun playFail(player: Player) {
            player.playSound(player.location, Sound.DIG_GRASS, 20.0F, 0.1F)
        }

        @JvmStatic
        fun playSuccess(player: Player) {
            player.playSound(player.location, Sound.NOTE_PIANO, 20.0F, 15.0F)
        }

        @JvmStatic
        fun playNeutral(player: Player) {
            player.playSound(player.location, Sound.CLICK, 20.0F, 1.0F)
        }

        @JvmStatic
        fun styleAction(color: ChatColor, action: String, text: String): String {
            return color.toString() + ChatColor.BOLD + action + ChatColor.RESET + color + " " + text
        }
    }

}