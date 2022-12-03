package dev.ukry.menu.pagination

import org.bukkit.entity.Player

interface PaginationFilter<T> {

    fun name(): String

    fun priority(): Int

    fun apply(player: Player, menu: CriteriaPaginatedMenu<T>, list: Collection<T>): Collection<T>

    fun isCompatible(filter: PaginationFilter<T>): Boolean {
        return true
    }

    fun isDynamic(): Boolean {
        return false
    }

    fun startCreation(player: Player, call: (PaginationFilter<T>?) -> Unit) {

    }

}