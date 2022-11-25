package dev.ukry.meetup.game.world.command

import dev.ukry.meetup.Burrito
import me.gleeming.command.Command
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

class RegenerateCommand {

    @Command(names = ["regenerate"], permission = "command.regenerated")
    fun execute(player : Player) {
        val world = Burrito.getInstance().worldHandler.world
        if(world == null) {
            player.sendMessage("World no is generated")
            return
        }
        world.players.forEach {
            Burrito.getInstance().lobbyHandler.sendToLobby(it)
        }
        val game = Burrito.getInstance().gameHandler.actualGame
        if(game == null) {
            Bukkit.getConsoleSender().sendMessage("That game no have map actually!")
            return
        }
        Burrito.getInstance().worldHandler.generatorHandler.generator.generateWorld(game, true)
    }
}