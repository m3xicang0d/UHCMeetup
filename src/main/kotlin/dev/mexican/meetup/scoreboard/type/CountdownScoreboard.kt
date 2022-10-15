package dev.mexican.meetup.scoreboard.type

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.config.ScoreboardFile
import dev.mexican.meetup.game.Game
import dev.mexican.meetup.game.state.GameState
import dev.mexican.meetup.scoreboard.Scoreboard
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class CountdownScoreboard : Scoreboard() {

    var game : Game? = null
    val cache = mutableSetOf<Long>()
    override fun accept(player : Player, scores : MutableList<String>) {
        //Cooldown in chat
        if(game == null) game = Burrito.getInstance().gameHandler.actualGame!!
        val count = TimeUnit.MILLISECONDS.toSeconds(game!!.cooldown!!.getRemainingLong())
        if (count <= 0) {
            game!!.state = GameState.PLAYING
            Bukkit.broadcastMessage("Game Started!")
            return
        }
        if(!cache.contains(count)) {
            if (count <= 5) {
                cache.add(count)
                Bukkit.broadcastMessage("Starting ${count}...")
            }
        }
        ScoreboardFile.getConfig().getStringList("IN-COUNTDOWN").stream()
            .map { s -> s.replace("<seconds>", (count).toString())}
            .forEach(scores::add)
    }
}