package dev.mexican.meetup.scoreboard.type

import dev.mexican.meetup.config.ScoreboardFile
import dev.mexican.meetup.scoreboard.Scoreboard
import dev.mexican.meetup.util.CC
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class GeneratingScoreboard : Scoreboard() {
    override fun accept(player : Player, scores : MutableList<String>) {
        ScoreboardFile.getConfig().getStringList("GENERATING").stream()
            .map(CC::translate)
            .forEach(scores::add)
    }
}