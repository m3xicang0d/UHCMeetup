/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu.pagination

import dev.ukry.menu.Button
import dev.ukry.menu.Menu
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import kotlin.math.ceil
import kotlin.math.min

abstract class PaginatedMenu : Menu() {

    var page: Int = 1
    var verticalView: Boolean = false

    override fun getTitle(player: Player): String {
        return getPrePaginatedTitle(player) + ChatColor.RESET.toString() + " - " + page + "/" + getPages(player)
    }

    fun modPage(player: Player, mod: Int) {
        page += mod
        buttons.clear()
        openMenu(player)
    }

    internal fun getPages(player: Player): Int {
        val buttonAmount = getAllPagesButtons(player).size

        return if (buttonAmount == 0) {
            1
        } else {
            ceil(buttonAmount / getMaxItemsPerPage(player).toDouble()).toInt()
        }
    }

    override fun getButtons(player: Player): MutableMap<Int, Button> {
        val buttons = HashMap<Int, Button>()

        val pageButtonSlots = getPageButtonSlots()
        if (pageButtonSlots != null) {
            buttons[pageButtonSlots.first] = PageButton(-1, this)
            buttons[pageButtonSlots.second] = PageButton(1, this)
        }

        // insert entry buttons
        val buttonSlots = getAllPagesButtonSlots()
        if (buttonSlots.isEmpty()) {
            val minIndex = ((page - 1) * getMaxItemsPerPage(player).toDouble()).toInt()
            val maxIndex = (page * getMaxItemsPerPage(player).toDouble()).toInt()

            for (entry in getAllPagesButtons(player).entries) {
                var ind = entry.key
                if (ind in minIndex until maxIndex) {
                    ind -= (getMaxItemsPerPage(player) * (page - 1).toDouble()).toInt() - 9
                    buttons[getButtonsStartOffset() + ind] = entry.value
                }
            }
        } else {
            val maxPerPage = buttonSlots.size
            val minIndex = (page - 1) * maxPerPage
            val maxIndex = page * maxPerPage

            for ((index, entry) in getAllPagesButtons(player).entries.withIndex()) {
                if (index in minIndex until min(maxIndex, buttonSlots.size)) {
                    buttons[buttonSlots[index]] = entry.value
                }
            }
        }

        // insert global buttons AFTER inserting entry buttons
        val global = getGlobalButtons(player)
        if (global != null) {
            for ((key, value) in global) {
                buttons[key] = value
            }
        }

        return buttons
    }

    open fun getMaxItemsPerPage(player: Player): Int {
        return 18
    }

    open fun getButtonsStartOffset(): Int {
        return 0
    }

    open fun getPageButtonSlots(): Pair<Int, Int>? {
        return Pair(0, 8)
    }

    open fun getGlobalButtons(player: Player): Map<Int, Button>? {
        return null
    }

    abstract fun getPrePaginatedTitle(player: Player): String

    abstract fun getAllPagesButtons(player: Player): Map<Int, Button>

    open fun getAllPagesButtonSlots(): List<Int> {
        return emptyList()
    }

}