package dev.ukry.meetup.command.admin.menu.button

import dev.ukry.meetup.util.CC
import dev.ukry.menu.Button
import dev.ukry.menu.Textures
import dev.ukry.menu.buttons.TexturedHeadButton
import org.bukkit.Material
import org.bukkit.entity.Player

class ComingSoonButton : TexturedHeadButton() {

    override fun getName(player: Player): String {
        return CC.translate("&c&lComing Soon")
    }

    override fun getTexture(player: Player): String {
        return Textures.BARRIER.texture
    }
}