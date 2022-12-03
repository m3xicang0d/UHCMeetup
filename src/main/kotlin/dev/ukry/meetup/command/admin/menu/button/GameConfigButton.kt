package dev.ukry.meetup.command.admin.menu.button

import dev.ukry.meetup.util.CC
import dev.ukry.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class GameConfigButton : Button() {

    override fun getName(player: Player): String {
        return CC.translate("&e&lGame Config")
    }

    override fun getMaterial(player: Player): Material {
        return Material.FLINT_AND_STEEL
    }

    override fun getDescription(player: Player): List<String> {
        return listOf(
            CC.translate("&7Click to edit Game Config")
        )
    }
}