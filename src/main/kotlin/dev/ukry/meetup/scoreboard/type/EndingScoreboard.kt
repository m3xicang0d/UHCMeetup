package dev.ukry.meetup.scoreboard.type

import dev.ukry.meetup.Burrito
import dev.ukry.meetup.config.ScoreboardFile
import dev.ukry.meetup.game.state.GameState
import dev.ukry.meetup.scoreboard.Scoreboard
import dev.ukry.meetup.util.CC
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class EndingScoreboard : Scoreboard() {
    override fun accept(player : Player, scores : MutableList<String>) {
        val game = Burrito.getInstance().gameHandler.actualGame!!
        if(game.cooldown.isOnCooldown()) {
            game.state = GameState.GENERATING
        }
        ScoreboardFile.getConfig().getStringList("ENDING").stream()
            .map(CC::translate)
            .forEach(scores::add)
    }
}