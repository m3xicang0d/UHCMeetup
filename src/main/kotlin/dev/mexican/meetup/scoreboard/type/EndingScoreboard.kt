package dev.mexican.meetup.scoreboard.type

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.config.ScoreboardFile
import dev.mexican.meetup.game.state.GameState
import dev.mexican.meetup.scoreboard.Scoreboard
import dev.mexican.meetup.util.CC
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