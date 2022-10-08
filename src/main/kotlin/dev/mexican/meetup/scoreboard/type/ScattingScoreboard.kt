package dev.mexican.meetup.scoreboard.type

import dev.mexican.meetup.scoreboard.Scoreboard
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class ScattingScoreboard : Scoreboard() {
    override fun accept(player : Player, scores : MutableList<String>) {
        scores.add("Scatting players...")
    }
}