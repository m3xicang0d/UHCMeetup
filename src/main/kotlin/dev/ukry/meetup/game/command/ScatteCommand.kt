package dev.ukry.meetup.game.command

import dev.ukry.meetup.Burrito
import me.gleeming.command.Command
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class ScatteCommand {

    @Command(names = ["scatte"], permission = "command.scatte")
    fun execute(player : Player) {
        val game = Burrito.getInstance().gameHandler.actualGame
        if(game == null) {
            player.sendMessage("Game not initiated... Try /start!")
            return
        }
        game.scatte()
    }
}