package dev.ukry.meetup.command.admin.menu.button

import dev.ukry.meetup.util.CC
import dev.ukry.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player

class KitsConfigButton : Button() {

    override fun getAmount(player: Player): Int {
        return 1
    }

    override fun getName(player: Player): String {
        return CC.translate("&e&lMeetup Kits")
    }

    override fun getDescription(player: Player): List<String> {
        return listOf(
            CC.translate("&7Click to edit Meetup Kits")
        )
    }

    override fun getMaterial(player: Player): Material {
        return Material.IRON_CHESTPLATE
    }
}