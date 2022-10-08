package dev.mexican.meetup.scoreboard

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.config.ScoreboardFile
import dev.mexican.meetup.game.state.GameState
import dev.mexican.meetup.util.CC
import io.github.thatkawaiisam.assemble.AssembleAdapter
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

class ScoreboardProvider(
    val generatingScoreboard : Scoreboard,
    val lobbyScoreboard : Scoreboard,
    val scattingScoreboard : Scoreboard,
    val countDownScoreboard : Scoreboard,
    val playingScoreboard : Scoreboard,
    val endingScoreboard : Scoreboard
) : AssembleAdapter {

    override fun getTitle(player: Player?): String {
        return CC.translate(ScoreboardFile.getConfig().getString("TITLE"))
    }

    override fun getLines(player: Player): MutableList<String> {
        val state = Burrito.getInstance().gameHandler.actualGame!!.state
        val toReturn = mutableListOf<String>()
        when(state) {
            GameState.SCATTING -> {
                countDownScoreboard.accept(player, toReturn)
            }
            GameState.WAITING -> {
                lobbyScoreboard.accept(player, toReturn)
            }
            GameState.PLAYING -> {
                playingScoreboard.accept(player, toReturn)
            } else -> {

            }
        }
        return toReturn
    }

}