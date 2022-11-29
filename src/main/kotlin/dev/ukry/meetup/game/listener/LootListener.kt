package dev.ukry.meetup.game.listener

import dev.ukry.meetup.Burrito
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Chest
import org.bukkit.block.DoubleChest
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent


/**
 * @author UKry
 * Created: 15/10/2022
 * Project UHCMeetup
 **/

class LootListener : Listener {
    @EventHandler
    fun onPlayerDeath(event: PlayerDeathEvent) {

        if (Burrito.getInstance().gameHandler.actualGame?.isParticipant(event.entity) == true) return

        val player = event.entity as Player
        val loc: Location = player.location.clone()
        loc.block.type = Material.CHEST
        loc.z = loc.z + 1
        loc.block.type = Material.CHEST
        val chest = loc.block.state as Chest
        val holder = chest.inventory.holder as DoubleChest
        event.drops.forEach(holder.inventory::addItem)
        event.drops.clear()
    }


}