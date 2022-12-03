/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu.pagination

import dev.ukry.menu.Button
import dev.ukry.menu.Textures
import dev.ukry.menu.utils.ItemUtils
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import java.util.ArrayList
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView
import org.bukkit.inventory.ItemStack

class PageButton(private val mod: Int, private val menu: PaginatedMenu) : Button() {

    override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
        when {
            clickType == ClickType.RIGHT -> {
                ViewAllPagesMenu(menu).openMenu(player)
                playNeutral(player)
            }
            hasNext(player) -> {
                menu.modPage(player, mod)
                playNeutral(player)
            }
            else -> playFail(player)
        }
    }

    private fun hasNext(player: Player): Boolean {
        val pg = menu.page + mod
        return pg > 0 && menu.getPages(player) >= pg
    }

    override fun getName(player: Player): String {
        if (!this.hasNext(player)) {
            return if (this.mod > 0) {
                "§7Last Page"
            } else {
                "§7First Page"
            }
        }

        return if (this.mod > 0) {
            "${ChatColor.YELLOW}(${(menu.page + mod)}/${menu.getPages(player)}) ->"
        } else {
            "${ChatColor.YELLOW}<- (${(menu.page + mod)}/${menu.getPages(player)})"
        }
    }

    override fun getDescription(player: Player): List<String> {
        return ArrayList()
    }

    override fun getDamageValue(player: Player): Byte {
        return 3.toByte()
    }

    override fun getMaterial(player: Player): Material {
        return Material.SKULL_ITEM
    }

    override fun getButtonItem(player: Player): ItemStack {
        val texture = if (menu.verticalView) {
            if (mod >= 1) {
                ""
            } else {
                ""
            }
        } else {
            if (mod >= 1) {
                Textures.ARROW_RIGHT_BLACK.texture
            } else {
                Textures.ARROW_LEFT_BLACK.texture
            }
        }

        return ItemUtils.applySkullTexture(super.getButtonItem(player), texture)
    }

}