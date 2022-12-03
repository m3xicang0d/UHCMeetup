package dev.ukry.meetup.command.admin.menu.button

import dev.ukry.meetup.util.CC
import dev.ukry.menu.Textures
import dev.ukry.menu.buttons.TexturedHeadButton
import org.bukkit.entity.Player

class LetterButton(private val texture: Textures) : TexturedHeadButton() {

    override fun getTexture(player: Player): String {
        return texture.texture
    }

    override fun getName(player: Player): String {
        return CC.translate("&e")
    }

    override fun getAmount(player: Player): Int {
        return 1
    }
}