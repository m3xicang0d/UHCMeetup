package dev.mexican.meetup.game.command

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.game.state.GameState
import me.gleeming.command.Command
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class StartCommand {

    @Command(names = ["start"], permission = "command.start")
    fun execute(player : Player) {
        Burrito.getInstance().gameHandler.actualGame?.start()
    }
}