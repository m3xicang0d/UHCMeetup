package dev.ukry.meetup.command.admin.menu

import dev.ukry.meetup.command.admin.menu.button.*
import dev.ukry.menu.Button
import dev.ukry.menu.Menu
import dev.ukry.menu.Textures
import dev.ukry.menu.buttons.GlassButton
import dev.ukry.menu.buttons.TexturedHeadButton
import org.bukkit.DyeColor
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class ConfigMenu : Menu("Config Manager") {

    override fun getButtons(player: Player): Map<Int, Button> {
        val buttons = mutableMapOf<Int, Button>()

        buttons[1] = LetterButton(Textures.LETTER_B)
        buttons[2] = LetterButton(Textures.LETTER_U)
        buttons[3] = LetterButton(Textures.LETTER_R)
        buttons[4] = LetterButton(Textures.LETTER_R)
        buttons[5] = LetterButton(Textures.LETTER_I)
        buttons[6] = LetterButton(Textures.LETTER_T)
        buttons[7] = LetterButton(Textures.LETTER_O)

        buttons[20] = KitsConfigButton()
        buttons[21] = LobbyConfigButton()
        buttons[22] = GameConfigButton()
        buttons[23] = MessageConfigButton()
        buttons[24] = ComingSoonButton()

        for (x in 0..36) {
            if (!buttons.containsKey(x)) {
                //buttons[x] = AnimatedGlassButton()
                buttons[x] = GlassButton(15)
            }
        }

        return buttons
    }

    override fun acceptsDraggedItems(player: Player, items: Map<Int, ItemStack>): Boolean {
        return false
    }

    override fun size(buttons: Map<Int, Button>): Int {
        return 36
    }
}