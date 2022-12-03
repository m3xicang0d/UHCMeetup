package dev.ukry.meetup.command.admin.menu.button

import dev.ukry.meetup.util.CC
import dev.ukry.menu.Button
import dev.ukry.menu.pagination.PaginatedMenu
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView

class MessageConfigButton : Button() {

    override fun getDescription(player: Player): List<String> {
        return listOf(
            CC.translate("&7Click to edit Message Config")
        )
    }

    override fun getName(player: Player): String {
        return CC.translate("&e&lMessage Config")
    }

    override fun getMaterial(player: Player): Material {
        return Material.NAME_TAG
    }

    override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
        MessageConfigMenu().openMenu(player)
    }
}

class MessageConfigMenu : PaginatedMenu() {

    override fun getPrePaginatedTitle(player: Player): String {
        return "Message Config"
    }

    override fun getAllPagesButtons(player: Player): Map<Int, Button> {

        return mapOf()
    }

}