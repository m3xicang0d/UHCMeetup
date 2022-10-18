package dev.mexican.meetup.scoreboard.type

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.config.ScoreboardFile
import dev.mexican.meetup.config.SettingsFile
import dev.mexican.meetup.scoreboard.Scoreboard
import dev.mexican.meetup.user.state.PlayerState
import dev.mexican.meetup.util.CC
import org.bukkit.entity.Player
import java.util.concurrent.TimeUnit

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class PlayingScoreboard(val spectatorScoreboard : Scoreboard) : Scoreboard() {
    val cache = mutableSetOf<Long>()
    override fun accept(player : Player, scores : MutableList<String>) {
        val game = Burrito.getInstance().gameHandler.actualGame!!
        val cooldown = game.cooldown
        if(!game.border.min) {
            if (!cooldown.isOnCooldown()) {
                game.cooldown.setCooldown(SettingsFile.getConfig().getInt("GAME.BORDER.TIME"))
                game.border.resize()
                cache.clear()
            } else {
                val time = TimeUnit.MILLISECONDS.toSeconds(cooldown.getRemainingLong())
                if (time < 10 && !cache.contains(time)) {
                    player.sendMessage("Reducting border ${time + 1} to ${game.border.actualBorder - game.border.reduction}")
                    cache.add(time)
                }
            }
        }
        val profile = Burrito.getInstance().profileHandler.getOrCreateProfile(player)
        if(profile.state == PlayerState.PLAYING) {
            ScoreboardFile.getConfig().getStringList("IN-MATCH").stream()
                .map(CC::translate)
                .map { s -> s.replace("<border-remaining>", if(game.border.min) "Min" else game.cooldown.getRemaining()) }
                .map { s -> s.replace("<border>", game.border.actualBorder.toString()) }
                .map { s -> s.replace("<kills>", Burrito.getInstance().profileHandler.getOrCreateProfile(player).kills.toString()) }
                .forEach(scores::add)
        } else {
            spectatorScoreboard.accept(player, scores)
        }
    }
}