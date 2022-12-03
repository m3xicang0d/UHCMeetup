package dev.ukry.menu.pagination

import org.bukkit.entity.Player

interface PaginationSort<T> {

    fun name(): String

    fun apply(player: Player, menu: CriteriaPaginatedMenu<T>, src: Collection<T>): Collection<T>

}