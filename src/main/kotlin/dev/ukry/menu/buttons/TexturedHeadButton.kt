package dev.ukry.menu.buttons/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

import dev.ukry.menu.Button
import dev.ukry.menu.utils.ItemUtils
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

abstract class TexturedHeadButton(private val texture: String? = null) : Button() {

    open fun getTexture(player: Player): String {
        return texture!!
    }

    override fun getMaterial(player: Player): Material {
        return Material.SKULL_ITEM
    }

    override fun getDamageValue(player: Player): Byte {
        return 3.toByte()
    }

    override fun getButtonItem(player: Player): ItemStack {
        return ItemUtils.applySkullTexture(super.getButtonItem(player), getTexture(player))
    }

}