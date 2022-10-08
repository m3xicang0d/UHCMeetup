package dev.ukry.api.cooldown

import dev.mexican.meetup.util.TimeUtils
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author UKry
 * Created: 06/10/2022
 * Project API
 **/

class Cooldown {

    var init = System.currentTimeMillis()
    var to = System.currentTimeMillis()

    fun getRemaining() : String {
        return TimeUtils.formatIntoMMSS(to.toInt() - init.toInt())
    }

    fun setCooldown(duration : Int) {
        init = System.currentTimeMillis()
        to = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(duration.toLong())
    }

    fun isOnCooldown(player : Player): Boolean {
        return to >= init
    }
}