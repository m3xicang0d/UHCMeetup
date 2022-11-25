package dev.ukry.meetup.scoreboard.type

import dev.ukry.meetup.scoreboard.Scoreboard
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class WinnerScoreboard : Scoreboard() {
    override fun accept(player : Player, scores : MutableList<String>) {
        scores.add("Scatting players...")
    }
}