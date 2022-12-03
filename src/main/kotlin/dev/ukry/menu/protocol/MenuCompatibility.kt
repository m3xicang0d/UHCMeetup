/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu.protocol

import dev.ukry.menu.utils.Reflection
import org.bukkit.Material

object MenuCompatibility {

    private val BARRIER = Reflection.getEnum(Material::class.java, "BARRIER") as Material?

    @JvmStatic
    fun getBarrierOrReplacement(): Material {
        return BARRIER ?: Material.REDSTONE
    }

}