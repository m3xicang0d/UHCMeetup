package dev.ukry.menu.buttons

import dev.ukry.menu.Button
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class StaticItemStackButton(private val item: ItemStack) : Button() {

    override fun getButtonItem(player: Player): ItemStack {
        return item.clone()
    }

}