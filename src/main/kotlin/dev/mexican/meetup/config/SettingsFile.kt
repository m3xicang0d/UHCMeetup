package dev.mexican.meetup.config

import com.google.common.base.Preconditions
import dev.mexican.meetup.UHCMeetup
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

//Shitty config file xd
class SettingsFile : YamlConfiguration() {

    private val fileName = "settings.yml"
    private val file: File = File(UHCMeetup.getInstance().dataFolder, fileName)

    init {
        Preconditions.checkArgument(!initialed, "Error, config file already initialed!")
        initialed = true
        if (!file.exists()) UHCMeetup.getInstance().saveResource(fileName, false)
        this.reload()
        config = this
    }

    fun reload() {
        super.load(file)
    }

    fun save() {
        super.save(file)
    }

    companion object {
        @JvmStatic
        private var config : SettingsFile? = null

        @JvmStatic
        fun getConfig() : SettingsFile {
            if(config == null) {
                config = SettingsFile()
            }
            return config!!
        }

        @JvmStatic
        var initialed = false
            set(value) {
                Preconditions.checkArgument(value, "Error, dont modify code :)")
                field = value
            }
    }
}