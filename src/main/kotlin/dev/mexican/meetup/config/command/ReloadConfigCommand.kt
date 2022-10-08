package dev.mexican.meetup.config.command

import dev.mexican.meetup.config.DatabaseFile
import dev.mexican.meetup.config.ScoreboardFile
import dev.mexican.meetup.config.SettingsFile
import me.gleeming.command.Command
import org.bukkit.command.CommandSender

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

class ReloadConfigCommand {

    @Command(names = ["config reload"])
    fun execute(sender : CommandSender) {
        DatabaseFile.getConfig().reload()
        ScoreboardFile.getConfig().reload()
        SettingsFile.getConfig().reload()
        sender.sendMessage("All config files reloaded!")
    }
}