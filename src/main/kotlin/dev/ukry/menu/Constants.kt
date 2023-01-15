/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu

import org.apache.commons.lang3.StringUtils
import org.bukkit.ChatColor
import org.bukkit.Material

object Constants {

    @JvmStatic
    val USERNAME_REGEX = "[a-zA-Z0-9_]{1,16}".toRegex()

    @JvmStatic
    val EMAIL_REGEX = "[^@]+@[^@]+\\.[^@]+".toRegex()

    @JvmStatic
    val CONTAINER_TYPES = listOf(
        Material.CHEST,
        Material.TRAPPED_CHEST,
        Material.ENDER_CHEST,
        Material.FURNACE,
        Material.BURNING_FURNACE,
        Material.DISPENSER,
        Material.HOPPER,
        Material.DROPPER,
        Material.BREWING_STAND
    )

    @JvmStatic
    val INTERACTIVE_TYPES = listOf(
        Material.REDSTONE_COMPARATOR,
        Material.REDSTONE_COMPARATOR_OFF,
        Material.REDSTONE_COMPARATOR_ON,
        Material.DIODE,
        Material.DIODE_BLOCK_OFF,
        Material.DIODE_BLOCK_ON,
        Material.LEVER,
        Material.WOOD_BUTTON,
        Material.STONE_BUTTON,
        Material.WOOD_PLATE,
        Material.STONE_PLATE,
        Material.IRON_PLATE,
        Material.GOLD_PLATE,
        Material.NOTE_BLOCK,
        Material.TRAP_DOOR,
        Material.IRON_DOOR,
        Material.WOODEN_DOOR
    )

    @JvmStatic
    val LOG_TYPES = listOf(
        Material.LOG,
        Material.LOG_2
    )

    @JvmStatic
    val HEART_SYMBOL = "❤"

    @JvmStatic
    val CROSSED_SWORDS_SYMBOL = "${ChatColor.BOLD}⚔"

    @JvmStatic
    val CHECK_SYMBOL = "${ChatColor.BOLD}✔"

    @JvmStatic
    val X_SYMBOL = "${ChatColor.BOLD}✗"

    @JvmStatic
    val PRESTIGE_SYMBOL = "${ChatColor.BOLD}⭑"

    @JvmStatic
    val MONEY_SYMBOL = "${ChatColor.BOLD}＄"

    @JvmStatic
    val TOKENS_SYMBOL = "⛃"

    @JvmStatic
    val ALT_TOKENS_SYMBOL = "۞"

    @JvmStatic
    val ARROW_UP = "${ChatColor.BOLD}⬆"

    @JvmStatic
    val ARROW_DOWN = "${ChatColor.BOLD}⬇"

    @JvmStatic
    val ARROW_LEFT = "${ChatColor.BOLD}⬅"

    @JvmStatic
    val ARROW_RIGHT = "${ChatColor.BOLD}➡"

    @JvmStatic
    val CURVED_ARROW_RIGHT = "➥"

    @JvmStatic
    val TRIANGLE_ARROW_LEFT = "◀"

    @JvmStatic
    val TRIANGLE_ARROW_RIGHT = "▶"

    @JvmStatic
    val ARROW_HEAD_RIGHT = "➤"

    @JvmStatic
    val ARROW_HEAD_RIGHT_3D = "➣"

    @JvmStatic
    val DOUBLE_ARROW_LEFT = "«"

    @JvmStatic
    val DOUBLE_ARROW_RIGHT = "»"

    @JvmStatic
    val THIN_VERTICAL_LINE = "┃"

    @JvmStatic
    val THICK_VERTICAL_LINE = "❙"

    @JvmStatic
    val DOT_SYMBOL = "●"

    @JvmStatic
    val SMALL_DOT_SYMBOL = "•"

    @JvmStatic
    val EXP_SYMBOL = "✪"

    @JvmStatic
    val REFRESH_SYMBOL = "${ChatColor.BOLD}⟲"

    @JvmStatic
    val FLAG_SYMBOL = "${ChatColor.BOLD}⚑"

    @JvmStatic
    val ANCHOR_SYMBOL = "${ChatColor.BOLD}⚓"

    /**
     * Example omitted - Solid line which almost entirely spans the
     * (default) Minecraft chat box. 53 is chosen for no reason other than its width
     * being almost equal to that of the chat box.
     */
    @JvmStatic
    val LONG_LINE = ChatColor.STRIKETHROUGH.toString() + StringUtils.repeat("-", 53)

    @JvmStatic
    val ADMIN_PREFIX = "${ChatColor.GRAY}[${ChatColor.DARK_RED}${ChatColor.BOLD}ADMIN${ChatColor.GRAY}] "

    @JvmStatic
    val WOOD_ARROW_UP_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzA0MGZlODM2YTZjMmZiZDJjN2E5YzhlYzZiZTUxNzRmZGRmMWFjMjBmNTVlMzY2MTU2ZmE1ZjcxMmUxMCJ9fX0="

    @JvmStatic
    val WOOD_ARROW_DOWN_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzQzNzM0NmQ4YmRhNzhkNTI1ZDE5ZjU0MGE5NWU0ZTc5ZGFlZGE3OTVjYmM1YTEzMjU2MjM2MzEyY2YifX19"

    @JvmStatic
    val WOOD_ARROW_LEFT_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ=="

    @JvmStatic
    val WOOD_ARROW_RIGHT_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19"

    @JvmStatic
    val WOOD_ARROW_X_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWE2Nzg3YmEzMjU2NGU3YzJmM2EwY2U2NDQ5OGVjYmIyM2I4OTg0NWU1YTY2YjVjZWM3NzM2ZjcyOWVkMzcifX19"

    @JvmStatic
    val WOOD_ARROW_Y_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzUyZmIzODhlMzMyMTJhMjQ3OGI1ZTE1YTk2ZjI3YWNhNmM2MmFjNzE5ZTFlNWY4N2ExY2YwZGU3YjE1ZTkxOCJ9fX0="

    @JvmStatic
    val WOOD_ARROW_Z_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTA1ODJiOWI1ZDk3OTc0YjExNDYxZDYzZWNlZDg1ZjQzOGEzZWVmNWRjMzI3OWY5YzQ3ZTFlMzhlYTU0YWU4ZCJ9fX0="

    @JvmStatic
    val GOLD_DOLLAR_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjUwOWRlOTgxYTc4NmE5ODBiMGJjODcxYWQ4NTViMjBjZTBiNzAxYjdhMmRmMTRjZmZmNmIzYTZlNDUyOTcyMyJ9fX0="

    @JvmStatic
    val IB_ALARM_ON_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTAzNzgwZGZjMmIxYmJmMWFiZjBmMzFkOWVhMmU1Yzc4NTkzMTE4ZTg1ZmViZTZlYjllOTBhMGEyODFiMDBiZSJ9fX0="

    @JvmStatic
    val IB_ALARM_OFF_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGRkMTJhNmMxM2QxZTk1NjU4Mjc5OTVlMjg2Yzk3ODJiYTQ2ZjJkYmE3MzE3OWYzNTc0YjdkMDY5NWNkYjcwMyJ9fX0="

    @JvmStatic
    val IB_WARNING_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGE5MDAzMzliYjk3MWYwMjhhZmU2NzI0YTg1MjE5YmEyMzM5OTE4YmE0OWMxMTlkMmZiODcxZTQ3YWM5OWIzOSJ9fX0="

    @JvmStatic
    val IB_CHAT_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjAyYWYzY2EyZDVhMTYwY2ExMTE0MDQ4Yjc5NDc1OTQyNjlhZmUyYjFiNWVjMjU1ZWU3MmI2ODNiNjBiOTliOSJ9fX0="

    @JvmStatic
    val IB_CHAT_FORBIDDEN_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJmMDU4ZGRjMjk2OTEzMzI1OTFhYzU1YTBmZDczZjQzMjAxMTc5ODJjZmRiY2U3OTY5OTQxY2ZhOGVkOGM2YiJ9fX0="

    @JvmStatic
    val IB_SPEECH_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjQ4Y2UxY2YxOGFmMDVhNTc2ZDYwODEyMzAwMWI3OTFmZWRiNjIyOTExZWY4ZDM4YTMyMGRhM2JjYmY2ZmQyMCJ9fX0="

    @JvmStatic
    val IB_SOUND_ON_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvN2RkYjFlM2VjMzg2ZjhkMTg0YzI5ZmMwNGI4ZjZiNzZiMTg3OTVjMzI1YzQyOWM0OGIzNDgzNDMzMDA2N2FjZSJ9fX0="

    @JvmStatic
    val IB_SOUND_OFF_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGVhNmU2YzRmMjkyZmNjODJiZWZlOTEyYjM5MjE3ODQ3MzA4MDZiZGM0YjA0OTE2MzhlNDYzODExMDg4MjdlYiJ9fX0="

    @JvmStatic
    val IB_TALKING_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjgxNDIyZThkZGMwZDMxMDlhYTY1N2I4OWIwYjBlYjFkMjVjYjNiYzhkNTRkYzZjOTljM2M5YzA4MTQ0MDI1NCJ9fX0="

    @JvmStatic
    val IB_MUTED_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNGYxMzBmNDg1YzNmNzY5N2YzMjBkZGMxMTI4Y2QzZjE3Y2RiZDM3OTE3NjRmN2E3YmI5NWNmMjUyNzM4NTg4In19fQ=="

    @JvmStatic
    val IB_UNLOCKED_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTRkNjFlYmMyOWM2MDk3MjQwNTNlNDJmNjE1YmM3NDJhMTZlZjY4Njk2MTgyOWE2ZDAxMjcwNDUyOWIxMzA4NSJ9fX0="

    @JvmStatic
    val IB_LOCKED_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmVjYjYyYzYzYjI1NzVlYzhkYjc3MWM1N2M4YjU2MDUxNWJiNTA0MTkwMjM4YTk2MWU2ZTI0M2VmNTYwMmVkNCJ9fX0="

    @JvmStatic
    val IB_BANNER_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzYxODQ2MTBjNTBjMmVmYjcyODViYzJkMjBmMzk0MzY0ZTgzNjdiYjMxNDg0MWMyMzhhNmE1MjFhMWVlMTJiZiJ9fX0="

    @JvmStatic
    val IB_CHEST_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzBhZjkyZDgyMDE0ZTA2NGUzMzhiNDM2NDVmMmNhNTE5MGQ1MmVmN2E4MWExMzc3OTY4MDdkZTA1ODY0OGFmIn19fQ=="

    @JvmStatic
    val IB_CHEST_OPEN_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYTZkMmE3MTJjZGQ0NDllZjBlNzc2MDU3YjU5MDMwZGQ0MzNlYThkY2RjYTE2M2QwZmY0MGFmNmU5OTY1ZWYzMiJ9fX0="

    @JvmStatic
    val IB_CHEST_LOOTED_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvM2FkMDVhYzI4YmE1MjBmZDFlN2NiNDIyMTI4NjFkM2M0ZTk5NjMxMDg1MWE1ZmRiNzljNDlmMTViMjc2MzhiYiJ9fX0="

    @JvmStatic
    val IB_MUSIC_NOTE_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVjYjQ5Y2NjYzEzNmIyZjQ3OTJhYTE5MDY3ZGM2NDVhNGVmYTEyYzM3NzQxM2QxOGNkMjEyNzM4YjE5NjlhYSJ9fX0="

    @JvmStatic
    val IB_MUSIC_NOTE_GRAY_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTE2MjFlODhhY2NiYTY5ZDc4ZDJjYmY4MmZlMDU4Y2NjNjBhY2IxMDFiNGQ3MWU3YWY3MDA3M2I2YTFkYjQ5NiJ9fX0="

    @JvmStatic
    val IB_TEAM_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmU0MTVmOTUxZGM5YzM0MjA0YzIyM2YwZjUwMGNiYzBmMWRmZTc3MmJmNGI5YjNiZDE3MzAyNTFkZTY1ODEwYyJ9fX0="

    @JvmStatic
    val IB_TEAM_GRAY_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjMxNDAwZjM1YmFlMGNiMmJkZDAzMTRhNDI0ZjEzMDdiNjkyMGJkZmE2ODE0MjczNjUzMGY0OTA0NjNhNTEzYSJ9fX0="

    @JvmStatic
    val IB_FORWARD_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOTNiZjJmYzY5M2IxNmNiOTFiOGM4N2E0YjA4OWZkOWUxODI1ZmNhMDFjZWZiMTY1YzYxODdmYzUzOWIxNTJjOSJ9fX0="

    @JvmStatic
    val IB_BACKWARDS_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2JkZjJjMzliYjVjYmEyNDQzMjllMDI4MGMwYjRhNDNlOWMzY2VhMjllMDZhYzIyMjcyMjM4ZmZiM2Q1ZTUzYiJ9fX0="

    @JvmStatic
    val IB_ICON_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmMzNWU4Njg0YzdmNzc2YmVmZWRjNDMxOWQwODE0OGM1NGJlYTM5MzIxZTFiZDVkZWY3YTU1Yjg5ZmRhYTA5OSJ9fX0="

    @JvmStatic
    val IB_GLOBE_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjM0N2EzOTQ5OWRlNDllMjRjODkyYjA5MjU2OTQzMjkyN2RlY2JiNzM5OWUxMTg0N2YzMTA0ZmRiMTY1YjZkYyJ9fX0="

    @JvmStatic
    val IB_INBOX_NEW_MAIL = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNmJmM2ZjZGNjZmZkOTYzZTQzMzQ4MTgxMDhlMWU5YWUzYTgwNTY2ZDBkM2QyZDRhYjMwNTFhMmNkODExMzQ4YyJ9fX0="

    @JvmStatic
    val IB_INBOX = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmNkNWMwYzZiMDBlNDgxNDcyN2YwZDc2NGFkNTI1YTczODBhYzQ2MWMzNzYwZTI0ZWMyYjUwMjgxZDg0ZGE3OSJ9fX0="

    @JvmStatic
    val TOKEN_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjBhN2I5NGM0ZTU4MWI2OTkxNTlkNDg4NDZlYzA5MTM5MjUwNjIzN2M4OWE5N2M5MzI0OGEwZDhhYmM5MTZkNSJ9fX0="

    @JvmStatic
    val WOOD_NUMBER_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzFhOTQ2M2ZkM2M0MzNkNWUxZDlmZWM2ZDVkNGIwOWE4M2E5NzBiMGI3NGRkNTQ2Y2U2N2E3MzM0OGNhYWIifX19"

    @JvmStatic
    val IB_STACKED_PAPERS_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDg4M2Q2NTZlNDljMzhjNmI1Mzc4NTcyZjMxYzYzYzRjN2E1ZGQ0Mzc1YjZlY2JjYTQzZjU5NzFjMmNjNGZmIn19fQ=="

    @JvmStatic
    val SKULL_GREEN_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzNlY2RiODQ3ZTEwMTg0OWYzMzU0NzJhYzAyZGIwZDg4OTk2YTY5MThlZWU5NTc5NmRjZjg2Y2I0N2YyMTdlIn19fQ=="

    @JvmStatic
    val SKULL_BLUE_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzdiOWRmZDI4MWRlYWVmMjYyOGFkNTg0MGQ0NWJjZGE0MzZkNjYyNjg0NzU4N2YzYWM3NjQ5OGE1MWM4NjEifX19"

    @JvmStatic
    val SKULL_RED_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvY2I4NTJiYTE1ODRkYTllNTcxNDg1OTk5NTQ1MWU0Yjk0NzQ4YzRkZDYzYWU0NTQzYzE1ZjlmOGFlYzY1YzgifX19"

    @JvmStatic
    val GREEN_PLUS_TEXTURE = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjA1NmJjMTI0NGZjZmY5OTM0NGYxMmFiYTQyYWMyM2ZlZTZlZjZlMzM1MWQyN2QyNzNjMTU3MjUzMWYifX19"

    @JvmStatic
    val CAMERA_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMTQ0MjJhODJjODk5YTljMTQ1NDM4NGQzMmNjNTRjNGFlN2ExYzRkNzI0MzBlNmU0NDZkNTNiOGIzODVlMzMwIn19fQ=="

    @JvmStatic
    val GLOBE_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjFkZDRmZTRhNDI5YWJkNjY1ZGZkYjNlMjEzMjFkNmVmYTZhNmI1ZTdiOTU2ZGI5YzVkNTljOWVmYWIyNSJ9fX0="

    @JvmStatic
    val DOLLAR_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDdiNjljOWRmYjYxMDY3Yzk0ODRkZjdkMDNlNjNmMTc4OTVjOWNkYTMzMjVjMmM1MzRhNWMyMjM1ODU1NzYzMSJ9fX0="

    @JvmStatic
    val YOUTUBE_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmI3Njg4ZGE0NjU4NmI4NTlhMWNkZTQwY2FlMWNkYmMxNWFiZTM1NjE1YzRiYzUyOTZmYWQwOTM5NDEwNWQwIn19fQ=="

    @JvmStatic
    val FRIEND_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGUzNTA1N2RhMjcxMTZlZGQ5MTY0ZTVjZmJjY2JjZDQ5OTY0ZDBiZjkwYjg3OWEyZTAzY2FmMzAzZTU4M2MyOSJ9fX0="

    @JvmStatic
    val TROPHY_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTM0YTU5MmE3OTM5N2E4ZGYzOTk3YzQzMDkxNjk0ZmMyZmI3NmM4ODNhNzZjY2U4OWYwMjI3ZTVjOWYxZGZlIn19fQ=="

    @JvmStatic
    val GRASS_BLOCK_OUTLINED_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjg3MDNmNjY3MzY1MGQzNjE2MTI5MTAyN2NiMGNmOGZiYjdhMzM5ZDY2ODgzMTc2NzQzZjQwMjQ3MzM1NDg5MyJ9fX0="

    @JvmStatic
    val DIAMOND_BLOCK_SWORD_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjAwZTdiMzNlZTJhNjAwMjc1OGFjZmUwOGM3ZGY2YmQzN2E0OTdkYzlmODAwMGMzY2E5ODI0YTJjZmFiY2FkZCJ9fX0="

    @JvmStatic
    val SUNNY_SKY_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzUzYzg5YTJhZGM0ZWU1YmExZjA1ZTVkNjRlOWI0YmI2YjMyMzJjNzIwMjhlMGNiZTM1ZTFiNzNkMGM1N2RjMSJ9fX0="

    @JvmStatic
    val BELL_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM1NTNhY2UzMmNmYTEyZjkxOTEzMjMyODQ3NDY3YmViNTZkNjQ1ZjFjOTZjNDE0NWU3OTlkMGZiOTM3YTMwIn19fQ=="

    @JvmStatic
    val MESSAGE_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGFlN2JmNDUyMmIwM2RmY2M4NjY1MTMzNjNlYWE5MDQ2ZmRkZmQ0YWE2ZjFmMDg4OWYwM2MxZTYyMTZlMGVhMCJ9fX0="

    @JvmStatic
    val RUBIKS_CUBE_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWIxZWYyYTQ4MjlhMTFmZDkwM2I1ZTMxMDg4NjYyYThjNTZlNDcxYmI0ODY0M2MwZDlmOTUwMDZkMTgyMDIxMCJ9fX0="

    @JvmStatic
    val PING_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjViZTQ5YmJkZDFkYjM1ZGVmMDRhZDExZjA2ZGVhYWY0NWM5NjY2YzA1YmMwMmJjOGJmMTQ0NGU5OWM3ZSJ9fX0="

    @JvmStatic
    val GMAIL_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMzc4YTY2OWFkZWYxY2FkMzQ0YzYwYWM5NDYzMmQyOTVkMTM4OWFjY2VhYjI0YjVkMjA4YTllYmE4YmU0NWI3YyJ9fX0="

    @JvmStatic
    val DISCORD_ICON = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg3M2MxMmJmZmI1MjUxYTBiODhkNWFlNzVjNzI0N2NiMzlhNzVmZjFhODFjYmU0YzhhMzliMzExZGRlZGEifX19"

}