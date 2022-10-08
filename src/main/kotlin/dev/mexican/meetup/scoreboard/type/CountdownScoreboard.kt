package dev.mexican.meetup.scoreboard.type

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.config.ScoreboardFile
import dev.mexican.meetup.scoreboard.Scoreboard
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class CountdownScoreboard : Scoreboard() {
    override fun accept(player : Player, scores : MutableList<String>) {
        ScoreboardFile.getConfig().getStringList("IN-COUNTDOWN").stream()
            .map { s -> s.replace("<seconds>", Burrito.getInstance().gameHandler.actualGame!!.cooldown!!.getRemaining()) }
            .forEach(scores::add)
    }
}