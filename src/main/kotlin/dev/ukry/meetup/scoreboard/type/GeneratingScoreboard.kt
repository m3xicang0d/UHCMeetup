package dev.ukry.meetup.scoreboard.type

import dev.ukry.meetup.config.ScoreboardFile
import dev.ukry.meetup.scoreboard.Scoreboard
import dev.ukry.meetup.util.CC
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