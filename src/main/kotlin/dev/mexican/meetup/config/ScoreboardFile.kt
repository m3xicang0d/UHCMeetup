package dev.mexican.meetup.config

import com.google.common.base.Preconditions
import dev.mexican.meetup.Burrito
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

//Shitty config file xd
class ScoreboardFile : YamlConfiguration() {

    private val fileName = "scoreboard.yml"
    private val file: File = File(Burrito.getInstance().dataFolder, fileName)

    init {
        Preconditions.checkArgument(!initialed, "Error, config file already initialed!")
        initialed = true
        if (!file.exists()) Burrito.getInstance().saveResource(fileName, false)
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
        private var config : ScoreboardFile? = null

        @JvmStatic
        fun getConfig() : ScoreboardFile {
            if(config == null) {
                config = ScoreboardFile()
            }
            return config!!
        }

        @JvmStatic
        private var initialed = false
            set(value) {
                Preconditions.checkArgument(value, "Error, dont modify code :)")
                field = value
            }
    }
}