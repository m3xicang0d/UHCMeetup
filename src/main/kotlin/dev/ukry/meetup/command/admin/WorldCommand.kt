package dev.ukry.meetup.command.admin

import fr.mrmicky.fastinv.FastInv
import fr.mrmicky.fastinv.ItemBuilder
import me.gleeming.command.Command
import me.gleeming.command.paramter.Param
import org.bukkit.*
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

class WorldCommand {

    @Command(names = ["world"], permission = "command.world")
    fun execute(player : Player, @Param(name = "world") world : World) {
        player.teleport(world.spawnLocation)
    }

    @Command(names = ["world"], permission = "command.world")
    fun execute(player : Player) {
        WorldsMenu().open(player)
    }

    class WorldsMenu : FastInv(27, "${ChatColor.GREEN}Worlds") {

        init {
            Bukkit.getWorlds().forEach {
                @Suppress("WHEN_ENUM_CAN_BE_NULL_IN_JAVA")
                val type = when(it.environment) {
                    World.Environment.NORMAL -> Material.GRASS
                    World.Environment.NETHER -> Material.NETHERRACK
                    World.Environment.THE_END -> Material.ENDER_STONE
                }
                addItem(ItemBuilder(type).name(it.name).build()) { event ->
                    val player = event.whoClicked as Player
                    player.gameMode = GameMode.CREATIVE
                    player.teleport(it.spawnLocation)
                }
            }
        }
    }
}