package dev.ukry.menu.pagination

import dev.ukry.menu.Button
import dev.ukry.menu.Constants
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.ClickType
import org.bukkit.inventory.InventoryView

abstract class CriteriaPaginatedMenu<T> : PaginatedMenu() {

    protected var selectedFilter: PaginationFilter<T> = NoFilter()
    protected var currentFilters: MutableSet<PaginationFilter<T>> = hashSetOf()

    protected var selectedSort: PaginationSort<T> = DefaultSort()
    protected var reversed: Boolean = false

    abstract fun getSourceSet(player: Player): Collection<T>

    abstract fun getFilters(player: Player): List<PaginationFilter<T>>

    abstract fun getSorts(player: Player): List<PaginationSort<T>>

    override fun getAllPagesButtons(player: Player): Map<Int, Button> {
        return hashMapOf<Int, Button>().also { buttons ->

            val originalSet = getSourceSet(player)
            var filteredSet = arrayListOf<T>()

            if (currentFilters.isEmpty()) {
                filteredSet = ArrayList(originalSet)
            } else {
                for (filter in currentFilters) {
                    filteredSet.addAll(filter.apply(player, this, originalSet).filter { !filteredSet.contains(it) })
                }
            }

            filteredSet = ArrayList(selectedSort.apply(player, this, filteredSet))
            if (reversed) {
                filteredSet.reverse()
            }

            for (item in filteredSet) {
                buttons[buttons.size] = createItemButton(player, item)
            }
        }
    }

    open fun getResults(player: Player): Collection<T> {
        var results = getSourceSet(player)
        for (filter in currentFilters.sortedBy { it.priority() }) {
            results = filter.apply(player, this, results)
        }

        return results
    }

    abstract inner class FilteredItemButton<T>(val item: T) : Button()

    abstract fun createItemButton(player: Player, item: T): FilteredItemButton<T>

    open class NoFilter<T> : PaginationFilter<T> {
        override fun name(): String {
            return "No Filter"
        }

        override fun priority(): Int {
            return 0
        }

        override fun apply(player: Player, menu: CriteriaPaginatedMenu<T>, list: Collection<T>): Collection<T> {
            return list
        }

        override fun equals(other: Any?): Boolean {
            return other is NoFilter<*>
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    open class DefaultSort<T> : PaginationSort<T> {
        override fun name(): String {
            return "Default"
        }

        override fun apply(player: Player, menu: CriteriaPaginatedMenu<T>, src: Collection<T>): Collection<T> {
            return src
        }

        override fun equals(other: Any?): Boolean {
            return other is DefaultSort<*>
        }

        override fun hashCode(): Int {
            return javaClass.hashCode()
        }
    }

    open inner class FilterButton : Button() {
        override fun getName(player: Player): String {
            return "${ChatColor.GREEN}${ChatColor.BOLD}Filter"
        }

        override fun getDescription(player: Player): List<String> {
            return arrayListOf<String>().also {
                it.add("")

                for (filter in getFilters(player)) {
                    it.add(buildString {
                        if (filter == selectedFilter) {
                            append(" ${ChatColor.BLUE}${ChatColor.BOLD}» ")
                        } else {
                            append("    ")
                        }

                        if (currentFilters.contains(filter)) {
                            append("${ChatColor.GREEN}${ChatColor.BOLD}")
                        } else {
                            append("${ChatColor.GRAY}")
                        }

                        append(filter.name())
                    })
                }

                it.add("")
                it.add("${ChatColor.BLUE}${Constants.ARROW_UP} ${ChatColor.YELLOW}${ChatColor.BOLD}LEFT-CLICK")
                it.add("${ChatColor.BLUE}${ChatColor.BOLD}${Constants.DOT_SYMBOL} ${ChatColor.YELLOW}${ChatColor.BOLD}MIDDLE-CLICK ${ChatColor.YELLOW}to toggle")
                it.add("${ChatColor.BLUE}${Constants.ARROW_DOWN} ${ChatColor.YELLOW}${ChatColor.BOLD}RIGHT-CLICK")
            }
        }

        override fun getMaterial(player: Player): Material {
            return Material.HOPPER
        }

        override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
            val filters = getFilters(player)
            if (clickType.isLeftClick) {
                selectedFilter = if (selectedFilter == filters.last()) {
                    filters.first()
                } else {
                    filters[filters.indexOf(selectedFilter) + 1]
                }
            } else if (clickType.isRightClick) {
                selectedFilter = if (selectedFilter == filters.first()) {
                    filters.last()
                } else {
                    filters[filters.indexOf(selectedFilter) - 1]
                }
            } else if (clickType == ClickType.MIDDLE || clickType == ClickType.CREATIVE) {
                if (currentFilters.contains(selectedFilter)) {
                    currentFilters.remove(selectedFilter)
                } else {
                    when {
                        selectedFilter is NoFilter -> {
                            currentFilters.clear()
                        }
                        selectedFilter.isDynamic() -> {
                            player.closeInventory()

                            selectedFilter.startCreation(player) { dynamicFilter ->
                                if (dynamicFilter != null) {
                                    currentFilters.add(dynamicFilter)
                                }

                                this@CriteriaPaginatedMenu.openMenu(player)
                            }
                        }
                        else -> {
                            currentFilters.add(selectedFilter)

                            val incompatible = currentFilters.filter { !it.isCompatible(selectedFilter) }
                            currentFilters.removeAll(incompatible)
                        }
                    }
                }
            }
        }
    }

    open inner class SortButton : Button() {
        override fun getName(player: Player): String {
            return "${ChatColor.GREEN}${ChatColor.BOLD}Sort"
        }

        override fun getDescription(player: Player): List<String> {
            return arrayListOf<String>().also {
                it.add("")

                for (sort in getSorts(player)) {
                    val builder = StringBuilder()

                    if (sort == selectedSort) {
                        builder.append(" ${ChatColor.BLUE}${ChatColor.BOLD}» ${ChatColor.GREEN}${sort.name()}")
                    } else {
                        builder.append("    ${ChatColor.GRAY}${sort.name()}")
                    }

                    it.add(builder.toString())
                }

                it.add("")
                it.add("${ChatColor.BLUE}${Constants.ARROW_DOWN} ${ChatColor.YELLOW}${ChatColor.BOLD}LEFT-CLICK")
                it.add("${ChatColor.BLUE}${Constants.ARROW_UP} ${ChatColor.YELLOW}${ChatColor.BOLD}RIGHT-CLICK")
                it.add("${ChatColor.BLUE}${Constants.REFRESH_SYMBOL} ${ChatColor.YELLOW}${ChatColor.BOLD}MIDDLE-CLICK ${ChatColor.YELLOW}to reverse sort")
            }
        }

        override fun getMaterial(player: Player): Material {
            return Material.DIODE
        }

        override fun clicked(player: Player, slot: Int, clickType: ClickType, view: InventoryView) {
            val sorts = getSorts(player)
            if (clickType == ClickType.MIDDLE || clickType == ClickType.CREATIVE) {
                reversed = !reversed
                return
            }

            if (clickType.isLeftClick) {
                selectedSort = if (selectedSort == sorts.last()) {
                    sorts.first()
                } else {
                    sorts[sorts.indexOf(selectedSort) + 1]
                }
            } else if (clickType.isRightClick) {
                selectedSort = if (selectedSort == sorts.first()) {
                    sorts.last()
                } else {
                    sorts[sorts.indexOf(selectedSort) - 1]
                }
            }
        }
    }

}