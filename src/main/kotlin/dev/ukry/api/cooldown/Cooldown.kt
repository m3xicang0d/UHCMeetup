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

    var to = System.currentTimeMillis()

    fun getRemainingLong() : Long {
        return to - System.currentTimeMillis()
    }

    fun getRemaining() : String {
        return (TimeUnit.MILLISECONDS.toSeconds(to - System.currentTimeMillis()) + 1L).toString()
    }

    fun setCooldown(duration : Int) {
        to = System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(duration.toLong())
    }

    fun isOnCooldown(): Boolean {
        return to >= System.currentTimeMillis()
    }
}