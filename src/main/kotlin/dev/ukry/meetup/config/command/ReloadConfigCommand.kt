package dev.ukry.meetup.config.command

import dev.ukry.meetup.config.DatabaseFile
import dev.ukry.meetup.config.ScoreboardFile
import dev.ukry.meetup.config.SettingsFile
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