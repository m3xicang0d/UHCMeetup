package dev.ukry.meetup.scoreboard

import dev.ukry.meetup.Burrito
import dev.ukry.meetup.config.ScoreboardFile
import dev.ukry.meetup.game.state.GameState
import dev.ukry.meetup.util.CC
import io.github.thatkawaiisam.assemble.AssembleAdapter
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit
import java.util.stream.Collectors

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

class ScoreboardProvider(
    private val generatingScoreboard : Scoreboard,
    private val lobbyScoreboard : Scoreboard,
    private val countDownScoreboard : Scoreboard,
    private val playingScoreboard : Scoreboard,
    private val endingScoreboard : Scoreboard
) : AssembleAdapter {

    val config = ScoreboardFile.getConfig()
    private var lastMillisFooter = System.currentTimeMillis()
    private var lastMillisTitle = System.currentTimeMillis()
    private var iFooter = 0
    private var iTitle = 0

    private fun titles(): String {
        val titles = config.getStringList("TITLE-ANIMATED.ANIMATION")
        val time = System.currentTimeMillis()
        val interval = TimeUnit.SECONDS.toMillis(config.getInt("TITLE-ANIMATED.TIME").toLong())
        if (lastMillisTitle + interval <= time) {
            if (iTitle != titles.size - 1) {
                iTitle++
            } else {
                iTitle = 0
            }
            lastMillisTitle = time
        }
        return titles[iTitle]
    }

    private fun footer(): String {
        val footers = config.getStringList("FOOTER-ANIMATED.ANIMATION")
        val time = System.currentTimeMillis()
        val interval = TimeUnit.SECONDS.toMillis(config.getInt("FOOTER-ANIMATED.TIME").toLong())
        if (lastMillisFooter + interval <= time) {
            if (iFooter != footers.size - 1) {
                iFooter++
            } else {
                iFooter = 0
            }
            lastMillisFooter = time
        }
        return footers[iFooter]
    }

    override fun getTitle(player: Player?): String {
        return if (config.getBoolean("TITLE-ANIMATED.ENABLED")) {
            val title = titles()
            title
        } else {
            CC.translate(ScoreboardFile.getConfig().getString("TITLE-NORMAL"))
        }
    }

    override fun getLines(player: Player): MutableList<String> {
        val state = Burrito.getInstance().gameHandler.actualGame!!.state
        var toReturn = mutableListOf<String>()
        when(state) {
            GameState.WAITING, GameState.SCATTING -> {
                lobbyScoreboard.accept(player, toReturn)
            }
            GameState.PLAYING -> {
                playingScoreboard.accept(player, toReturn)
            } GameState.COUNTDOWN -> {
                countDownScoreboard.accept(player, toReturn)
            } GameState.ENDING -> {
                endingScoreboard.accept(player, toReturn)
            } GameState.GENERATING -> {
                generatingScoreboard.accept(player, toReturn)
            }
        }


        if (config.getBoolean("FOOTER-ANIMATED.ENABLED")) {
            val footer = footer()
            toReturn = toReturn
                .stream()
                .map {
                    CC.translate(
                        it.replace("<footer>", footer)
                            .replace("<ping>", (player as CraftPlayer).handle.ping.toString())
                            .replace("<remaining>", Burrito.getInstance().gameHandler.actualGame!!.participants.size.toString())
                    )
                }
                .collect(Collectors.toList())
        }
        return toReturn
    }



}