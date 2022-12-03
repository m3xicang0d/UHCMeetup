package dev.ukry.meetup.command.admin.menu.button

import dev.ukry.meetup.util.CC
import dev.ukry.menu.Button
import org.bukkit.Material
import org.bukkit.entity.Player

class LobbyConfigButton : Button() {

    override fun getDescription(player: Player): List<String> {
        return listOf(
            CC.translate("&7Click to edit Lobby Config")
        )
    }

    override fun getName(player: Player): String {
        return CC.translate("&e&lLobby Config")
    }

    override fun getMaterial(player: Player): Material {
        return Material.REDSTONE_TORCH_ON
    }
}