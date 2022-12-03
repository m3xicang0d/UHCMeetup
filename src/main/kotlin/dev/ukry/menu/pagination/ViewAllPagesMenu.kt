/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu.pagination

import dev.ukry.menu.Button
import dev.ukry.menu.buttons.BackButton
import dev.ukry.menu.Menu
import org.bukkit.entity.Player

class ViewAllPagesMenu(private val menu: PaginatedMenu) : Menu() {

    init {
        autoUpdate = true
    }

    override fun getTitle(player: Player): String {
        return "Jump to page"
    }

    override fun getButtons(player: Player): Map<Int, Button> {
        val buttons = HashMap<Int, Button>()

        buttons[0] = BackButton {
            menu.openMenu(player)
        }

        var index = 10
        for (i in 1..menu.getPages(player)) {
            buttons[index++] = JumpToPageButton(i, menu)
            if ((index - 8) % 9 == 0) {
                index += 2
            }
        }

        return buttons
    }

}