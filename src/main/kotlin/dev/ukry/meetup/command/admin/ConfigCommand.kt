package dev.ukry.meetup.command.admin

import dev.ukry.meetup.command.admin.menu.ConfigMenu
import me.gleeming.command.Command
import org.bukkit.entity.Player

class ConfigCommand {

    @Command(names = ["burrito"], permission = "burrito.admin")
    fun execute(player: Player) {
        ConfigMenu().openMenu(player)
    }
}