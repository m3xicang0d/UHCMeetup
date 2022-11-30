package dev.ukry.meetup.game.listener

import dev.ukry.api.cooldown.Cooldown
import dev.ukry.meetup.Burrito
import dev.ukry.meetup.config.SettingsFile
import dev.ukry.meetup.util.CC
import org.bukkit.entity.EnderPearl
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileLaunchEvent
import java.util.UUID

class PearlCooldown : Listener {

    val config = SettingsFile.getConfig()

    private val cooldown = hashMapOf<UUID, Cooldown>()

    @EventHandler
    fun onProjectileLaunch(event: ProjectileLaunchEvent) {
        if (event.entity !is EnderPearl) return
        if (event.entity.shooter !is Player) return

        val game = Burrito.getInstance().gameHandler.actualGame ?: return

        val player = event.entity.shooter as Player

        if (!game.isParticipant(player)) return

        if (!cooldown.containsKey(player.uniqueId)) {
            cooldown[player.uniqueId] = Cooldown().apply {
                setCooldown(config.getInt("GAME.ENDERPEARL.COOLDOWN"))
            }
            player.sendMessage(CC.translate(config.getString("GAME.ENDERPEARL.MESSAGE")))
            return
        }

        if (cooldown[player.uniqueId]!!.isOnCooldown()) {
            event.isCancelled = true
            player.sendMessage(CC.translate(config.getString("GAME.ENDERPEARL.ON_COOLDOWN_MESSAGE").replace("<remaining>", cooldown[player.uniqueId]!!.getRemaining())))
            return
        }
    }
}