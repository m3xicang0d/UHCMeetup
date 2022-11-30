package dev.ukry.meetup.game.listener

import dev.ukry.meetup.Burrito
import dev.ukry.meetup.game.state.GameState
import dev.ukry.meetup.user.state.PlayerState
import dev.ukry.meetup.util.CC
import fr.mrmicky.fastinv.FastInv
import fr.mrmicky.fastinv.ItemBuilder
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent
import org.bukkit.metadata.FixedMetadataValue
import java.util.*

/**
 * @author UKry
 * Created: 15/10/2022
 * Project UHCMeetup
 **/

class SpectatorListener : Listener {

    val menu = ItemBuilder(Material.ITEM_FRAME)
        .name(CC.translate("&eSpectate Menu"))
        .lore(CC.translate(mutableListOf(
            "&eSee a list of players",
            "&ethat you´re able to",
            "&eteleport to and spectate"
        )))
        .build()


    @EventHandler
    fun onPlayerJoin(event : PlayerJoinEvent) {
        val game = Burrito.getInstance().gameHandler.actualGame!!

        if (game.state == GameState.COUNTDOWN || game.state == GameState.ENDING || game.state == GameState.SCATTING || game.state == GameState.WAITING) return

        game.addSpectator(event.player)
        event.player.setMetadata("SPECTATOR", FixedMetadataValue(Burrito.getInstance(), true))

        game.participants.stream()
            .map(Bukkit::getPlayer)
            .filter(Objects::nonNull)
            .forEach {
                it.hidePlayer(event.player)
            }

        Burrito.getInstance().profileHandler.getOrCreateProfile(event.player).state = PlayerState.SPECTATING

        event.player.inventory.setItem(4,menu)
        event.player.updateInventory()
    }

    @EventHandler
    fun onPlayerQuit(event: PlayerQuitEvent) {
        val game = Burrito.getInstance().gameHandler.actualGame ?: return

        if (game.spectators.contains(event.player.uniqueId)) {
            game.spectators.remove(event.player.uniqueId)
        }
    }

    @EventHandler
    fun onPlayerInteract(event : PlayerInteractEvent) {
        if(event.action != Action.RIGHT_CLICK_AIR && event.action != Action.RIGHT_CLICK_BLOCK) return
        val player = event.player

        if (player.itemInHand.isSimilar(menu)) {
            SpectatorMenu().open(event.player)
        }
    }

    @EventHandler
    fun onInvClick(event: InventoryClickEvent) {
        val game = Burrito.getInstance().gameHandler.actualGame ?: return

        if (game.spectators.contains(event.whoClicked.uniqueId)) {
            event.isCancelled = true
        }
    }

    class SpectatorMenu : FastInv(27, "Spectate") {
        init {
//            addItem()
        }
    }
}