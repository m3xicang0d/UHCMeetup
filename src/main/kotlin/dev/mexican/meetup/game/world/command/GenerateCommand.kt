package dev.mexican.meetup.game.world.command

import dev.mexican.meetup.Burrito
import me.gleeming.command.Command
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

class GenerateCommand {

    @Command(names = ["generate"], permission = "command.generate")
    fun execute(player : Player) {
        val world = Bukkit.getWorld("world")
        if(world == null) {
            player.sendMessage("World no is generated")
            return
        }
        world.players.forEach {
            Burrito.getInstance().lobbyHandler.sendToLobby(it)
        }
        Burrito.getInstance().worldHandler.generatorHandler.generator.generateWorld()
    }
}