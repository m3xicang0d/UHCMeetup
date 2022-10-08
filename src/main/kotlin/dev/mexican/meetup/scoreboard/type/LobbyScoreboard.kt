package dev.mexican.meetup.scoreboard.type

import dev.mexican.meetup.config.ScoreboardFile
import dev.mexican.meetup.config.SettingsFile
import dev.mexican.meetup.scoreboard.Scoreboard
import me.gleeming.command.bukkit.BukkitCommand
import org.bukkit.Bukkit
import org.bukkit.entity.Player

/**
 * @author UKry
 * Created: 07/10/2022
 * Project UHCMeetup
 **/

class LobbyScoreboard : Scoreboard() {
    override fun accept(player : Player, scores : MutableList<String>) {
        ScoreboardFile.getConfig().getStringList("IN-LOBBY").stream()
            .map {
                    s -> s.replace("<players-requiered>", (SettingsFile.getConfig().getInt("GAME.PLAYERS.MIN") - Bukkit.getServer().onlinePlayers.size).toString())
            }
            .forEach(scores::add)
    }
}