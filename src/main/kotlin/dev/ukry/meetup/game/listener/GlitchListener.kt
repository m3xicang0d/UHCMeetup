package dev.ukry.meetup.game.listener

import dev.ukry.meetup.Burrito
import dev.ukry.meetup.game.GameHandler
import dev.ukry.meetup.util.CC
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.EnderPearl
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerTeleportEvent
import org.bukkit.inventory.ItemStack

class GlitchListener : Listener {

    @EventHandler
    fun onPearlLand(event: PlayerTeleportEvent) {
        if (event.cause != PlayerTeleportEvent.TeleportCause.ENDER_PEARL) return

         if (isOutsideBorder(event.to)) {
             event.isCancelled = true
             event.player.inventory.addItem(ItemStack(Material.ENDER_PEARL))
             event.player.updateInventory()
             event.player.sendMessage(CC.translate("&cYou cannot teleport outside the border!"))
         }
    }

    private fun isOutsideBorder(location: Location): Boolean {
        val border = Burrito.getInstance().gameHandler.actualGame?.border ?: return false

        return location.x > border.actualBorder || location.x < -border.actualBorder || location.z > border.actualBorder || location.z < -border.actualBorder
    }
}