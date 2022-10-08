package dev.mexican.meetup.util

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import java.util.stream.Collectors

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

object CC {

    fun translate(input : String) : String {
        return ChatColor.translateAlternateColorCodes('&', input)
    }

    fun translate(input : MutableList<String>) : MutableList<String> {
        return input.stream().map(CC::translate).collect(Collectors.toList())
    }

    fun log(input : String) {
        Bukkit.getConsoleSender().sendMessage(translate(input))
    }

    fun announce(input : String) {
        Bukkit.getServer().onlinePlayers.forEach { it.sendMessage(translate(input)) }
    }

    fun announceAndLog(input : String) {
        log(input)
        announce(input)
    }

    fun announce(vararg input : String) {
        Bukkit.getServer().onlinePlayers.forEach {p ->
            input.forEach {  s ->
                p.sendMessage(translate(s))
            }
        }
    }
}