package dev.ukry.api.cooldown

import dev.ukry.meetup.util.TimeUtils
import org.bukkit.entity.Player
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * @author UKry
 * Created: 06/10/2022
 * Project API
 **/

class PlayerCooldown {

    val map = mutableMapOf<UUID, Long>()

    fun getRemaining(player : Player) : String {
        return if(isOnCooldown(player)) {
            TimeUtils.formatIntoMMSS(map[player.uniqueId]!!.toInt())
        } else {
            ""
        }
    }

    fun setCooldown(player : Player, duration : Int) {
        map.putIfAbsent(player.uniqueId, System.currentTimeMillis() + TimeUnit.SECONDS.toMillis(duration.toLong()))
    }

    fun isOnCooldown(player : Player): Boolean {
        return map.containsKey(player.uniqueId)
    }
}