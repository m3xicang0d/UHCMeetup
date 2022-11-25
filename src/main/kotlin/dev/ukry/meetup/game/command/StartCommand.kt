package dev.ukry.meetup.game.command

import dev.ukry.meetup.Burrito
import dev.ukry.meetup.game.state.GameState
import me.gleeming.command.Command
import me.gleeming.command.paramter.Param
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class StartCommand {

    @Command(names = ["start"], permission = "command.start")
    fun execute(player : Player) {
        Burrito.getInstance().gameHandler.actualGame!!.start()
    }

    @Command(names = ["game setstate"], permission = "command.start")
    fun execute(player : Player, @Param(name = "state") state : GameState) {
        Burrito.getInstance().gameHandler.actualGame!!.state = state
    }
}