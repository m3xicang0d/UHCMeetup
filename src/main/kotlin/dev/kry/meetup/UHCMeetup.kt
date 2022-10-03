package dev.kry.meetup

import dev.kry.meetup.command.InfoCommand
import dev.kry.meetup.storage.Storage
import dev.kry.meetup.storage.StorageHandler
import me.gleeming.command.CommandHandler
import org.bukkit.plugin.java.JavaPlugin

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class UHCMeetup : JavaPlugin() {

    //Storage section
    lateinit var storageHandler : StorageHandler
    lateinit var storage : Storage

    override fun onLoad() {
        saveDefaultConfig()
        storageHandler = StorageHandler().also { it.load() }
        CommandHandler.init(this)
    }

    override fun onEnable() {
        CommandHandler.registerCommands(InfoCommand::class.java)
    }

    companion object {
        @JvmStatic
        fun getInstance() : UHCMeetup {
            return JavaPlugin.getPlugin(UHCMeetup::class.java)
        }
    }
}