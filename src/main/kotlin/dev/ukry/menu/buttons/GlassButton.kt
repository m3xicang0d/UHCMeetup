/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu.buttons

import dev.ukry.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player

class GlassButton(private val data: Byte) : Button() {

    override fun getName(player: Player): String {
        return " "
    }

    override fun getDescription(player: Player): List<String> {
        return emptyList()
    }

    override fun getMaterial(player: Player): Material {
        return Material.STAINED_GLASS_PANE
    }

    override fun getDamageValue(player: Player): Byte {
        return data
    }

}