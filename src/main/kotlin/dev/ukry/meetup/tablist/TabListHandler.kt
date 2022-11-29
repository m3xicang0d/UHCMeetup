package dev.ukry.meetup.tablist

import dev.ukry.meetup.Burrito
import dev.ukry.meetup.config.SettingsFile
import dev.ukry.meetup.util.CC
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter
import org.bukkit.Bukkit
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable
import java.lang.reflect.Field


class TabListHandler : Listener {

    val config = SettingsFile.getConfig()

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        object : BukkitRunnable() {
            override fun run() {
                if (!event.player.isOnline) {
                    cancel()
                    return
                }

                sendHeaderFooter(event.player)
            }
        }.runTaskTimer(Burrito.getInstance(), 0, 20)
    }

    fun sendHeaderFooter(player: Player) {
        val header = config.getString("TABLIST.HEADER")
        val footer = config.getString("TABLIST.FOOTER")

        val tabTitle = ChatSerializer.a("{\"text\":\"${CC.translate(header)}\"}")
        val tabFooter = ChatSerializer.a("{\"text\":\"${CC.translate(footer)}\"}")

        val packet = PacketPlayOutPlayerListHeaderFooter(tabTitle);

        try {
            val field: Field = packet.javaClass.getDeclaredField("b")
            field.isAccessible = true
            field.set(packet, tabFooter)
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            (player as CraftPlayer).handle.playerConnection.sendPacket(packet)
        }
    }
}