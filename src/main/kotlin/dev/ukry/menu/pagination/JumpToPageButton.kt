/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu.pagination

import dev.ukry.menu.Button
import org.bukkit.Material
import org.bukkit.event.inventory.ClickType
import org.bukkit.entity.Player
import org.bukkit.inventory.InventoryView

class JumpToPageButton(private val page: Int, private val menu: PaginatedMenu) : Button() {

    override fun getName(player: Player): String {
        return "§ePage " + this.page
    }

    override fun getDescription(player: Player): List<String> {
        return listOf()
    }

    override fun getMaterial(player: Player): Material {
        return Material.BOOK
    }

    override fun getAmount(player: Player): Int {
        return this.page
    }

    override fun clicked(player: Player, i: Int, clickType: ClickType, view: InventoryView) {
        menu.modPage(player, page - menu.page)
        playNeutral(player)
    }

}