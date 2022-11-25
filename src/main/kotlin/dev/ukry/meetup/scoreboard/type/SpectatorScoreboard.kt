package dev.ukry.meetup.scoreboard.type

import dev.ukry.meetup.Burrito
import dev.ukry.meetup.config.ScoreboardFile
import dev.ukry.meetup.scoreboard.Scoreboard
import dev.ukry.meetup.util.CC
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class SpectatorScoreboard : Scoreboard() {

    override fun accept(player : Player, scores : MutableList<String>) {
        val game = Burrito.getInstance().gameHandler.actualGame!!
        ScoreboardFile.getConfig().getStringList("IN-SPECTATOR").stream()
            .map(CC::translate)
            .map { s -> s.replace("<border-remaining>", if(game.border.min) "Min" else game.cooldown.getRemaining()) }
            .map { s -> s.replace("<border>", game.border.actualBorder.toString()) }
            .map { s -> s.replace("<kills>", Burrito.getInstance().profileHandler.getOrCreateProfile(player).kills.toString()) }
            .forEach(scores::add)
    }
}