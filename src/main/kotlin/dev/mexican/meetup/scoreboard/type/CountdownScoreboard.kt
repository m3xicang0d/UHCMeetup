package dev.mexican.meetup.scoreboard.type

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.config.LangFile
import dev.mexican.meetup.config.ScoreboardFile
import dev.mexican.meetup.config.SettingsFile
import dev.mexican.meetup.game.Game
import dev.mexican.meetup.game.state.GameState
import dev.mexican.meetup.scoreboard.Scoreboard
import dev.mexican.meetup.util.CC
import dev.ukry.api.cooldown.Cooldown
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class CountdownScoreboard : Scoreboard() {

    val config = LangFile.getConfig()

    var game : Game? = null
    val cache = mutableSetOf<Long>()
    override fun accept(player : Player, scores : MutableList<String>) {
        //Cooldown in chat
        if(game == null) game = Burrito.getInstance().gameHandler.actualGame!!
        val count = TimeUnit.MILLISECONDS.toSeconds(game!!.cooldown!!.getRemainingLong())
        if (count <= 0) {
            game!!.state = GameState.PLAYING
            Bukkit.broadcastMessage(CC.translate(config.getString("GAME.STARTED")))
            game!!.cooldown!!.setCooldown(SettingsFile.getConfig().getInt("GAME.BORDER.TIME"))
            return
        }
        if(!cache.contains(count)) {
            if(count.toInt() % 10 == 0) {
                cache.add(count)
                Bukkit.broadcastMessage(CC.translate(config.getString("GAME.STARTING").replace("<seconds>", count.toString())))
            }
            if (count <= 5) {
                cache.add(count)
                Bukkit.broadcastMessage(CC.translate(config.getString("GAME.STARTING").replace("<seconds>", count.toString())))
            }
        }
        ScoreboardFile.getConfig().getStringList("IN-COUNTDOWN").stream()
            .map { s -> s.replace("<seconds>", (count).toString())}
            .forEach(scores::add)
    }
}