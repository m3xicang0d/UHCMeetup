package dev.ukry.meetup.scoreboard.type

import dev.ukry.meetup.Burrito
import dev.ukry.meetup.config.ScoreboardFile
import dev.ukry.meetup.config.SettingsFile
import dev.ukry.meetup.game.state.GameState
import dev.ukry.meetup.scoreboard.Scoreboard
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class LobbyScoreboard : Scoreboard() {

    val config = ScoreboardFile.getConfig()
    private var lastMillisFooter = System.currentTimeMillis()
    private var lastMillisTitle = System.currentTimeMillis()
    private var iFooter = 0
    private var iTitle = 0


    override fun accept(player : Player, scores : MutableList<String>) {
        val game = Burrito.getInstance().gameHandler.actualGame!!
        val required = SettingsFile.getConfig().getInt("GAME.PLAYERS.MIN")
        if(required <= game.participants.size) {
            game.state = GameState.SCATTING
        }
        ScoreboardFile.getConfig().getStringList("IN-LOBBY").stream()
            .map { s -> s.replace("<players-required>", (required - game.participants.size).toString()) }
            .forEach(scores::add)
    }
}