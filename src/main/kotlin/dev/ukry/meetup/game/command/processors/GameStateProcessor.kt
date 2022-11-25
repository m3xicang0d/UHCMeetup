package dev.ukry.meetup.game.command.processors

import dev.ukry.meetup.game.state.GameState
import me.gleeming.command.paramter.Processor
import org.bukkit.command.CommandSender

/**
 * @author UKry
 * Created: 12/10/2022
 * Project UHCMeetup
 **/

class GameStateProcessor : Processor<GameState?>() {


    override fun process(sender: CommandSender, supplied: String): GameState? {
        return try {
            val state = GameState.valueOf(supplied)
            state
        } catch (e : Exception) {
            sender.sendMessage("State not found")
            null
        }
    }
}