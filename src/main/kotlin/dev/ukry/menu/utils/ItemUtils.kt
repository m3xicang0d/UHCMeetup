/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu.utils

import com.mojang.authlib.GameProfile
import com.mojang.authlib.properties.Property
import org.apache.commons.lang.WordUtils
import org.bukkit.ChatColor
import org.bukkit.Color
import org.bukkit.Material
import org.bukkit.entity.EntityType
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.LeatherArmorMeta
import org.bukkit.inventory.meta.SkullMeta
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.*


object ItemUtils {

    private val ITEM_STACK_CLASS: Class<*> = MinecraftReflection.getNMSClass("ItemStack")!!
    private val CRAFT_ITEM_STACK_CLASS: Class<*> = MinecraftReflection.getCraftBukkitClass("inventory.CraftItemStack")!!
    private val AS_NMS_COPY_METHOD: Method = Reflection.getMethodSuppressed(CRAFT_ITEM_STACK_CLASS, "asNMSCopy", ItemStack::class.java)!!
    private val AS_BUKKIT_COPY_METHOD: Method = Reflection.getMethodSuppressed(CRAFT_ITEM_STACK_CLASS, "asBukkitCopy", ITEM_STACK_CLASS)!!

    private val EQUIPMENT_ITEMS = hashMapOf<Material, EquipmentSlot>().also {
        it[Material.LEATHER_HELMET] = EquipmentSlot.HEAD
        it[Material.LEATHER_CHESTPLATE] = EquipmentSlot.CHEST
        it[Material.LEATHER_LEGGINGS] = EquipmentSlot.LEGS
        it[Material.LEATHER_BOOTS] = EquipmentSlot.FEET

        it[Material.IRON_HELMET] = EquipmentSlot.HEAD
        it[Material.IRON_CHESTPLATE] = EquipmentSlot.CHEST
        it[Material.IRON_LEGGINGS] = EquipmentSlot.LEGS
        it[Material.IRON_BOOTS] = EquipmentSlot.FEET

        it[Material.GOLD_HELMET] = EquipmentSlot.HEAD
        it[Material.GOLD_CHESTPLATE] = EquipmentSlot.CHEST
        it[Material.GOLD_LEGGINGS] = EquipmentSlot.LEGS
        it[Material.GOLD_BOOTS] = EquipmentSlot.FEET

        it[Material.DIAMOND_HELMET] = EquipmentSlot.HEAD
        it[Material.DIAMOND_CHESTPLATE] = EquipmentSlot.CHEST
        it[Material.DIAMOND_LEGGINGS] = EquipmentSlot.LEGS
        it[Material.DIAMOND_BOOTS] = EquipmentSlot.FEET

        it[Material.PUMPKIN] = EquipmentSlot.HEAD
    }

    private val ARMOR_EQUIPMENT_SLOTS = listOf(EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET)

    private val SWORDS = listOf(
        Material.WOOD_SWORD,
        Material.GOLD_SWORD,
        Material.STONE_SWORD,
        Material.IRON_SWORD,
        Material.DIAMOND_SWORD
    )

    @JvmStatic
    fun getNmsCopy(item: ItemStack): Any {
        return AS_NMS_COPY_METHOD.invoke(null, item)
    }

    @JvmStatic
    fun getBukkitCopy(item: Any): ItemStack {
        return AS_BUKKIT_COPY_METHOD.invoke(null, item) as ItemStack
    }

    @JvmStatic
    fun getName(item: ItemStack): String {
        var name = Reflection.callMethod(getNmsCopy(item), "getName") as String

        if (name.contains(".")) {
            name = WordUtils.capitalize(item.type.toString().toLowerCase().replace("_", " "))
        }

        return name
    }

    @JvmStatic
    fun getDisplayName(itemStack: ItemStack): String {
        return if (itemStack.hasItemMeta() && itemStack.itemMeta.hasDisplayName()) {
            itemStack.itemMeta.displayName
        } else {
            getName(itemStack)
        }
    }

    @JvmStatic
    fun getChatName(itemStack: ItemStack): String {
        return if (itemStack.hasItemMeta() && itemStack.itemMeta.hasDisplayName()) {
            ChatColor.stripColor(itemStack.itemMeta.displayName)
        } else {
            getName(itemStack)
        }
    }

    @JvmStatic
    fun setMonsterEggType(item: ItemStack, type: EntityType): ItemStack {
        val idTag = NBTUtil.newTag()
        NBTUtil.setString(idTag, "id", type.getName())

        val nmsCopy = getNmsCopy(item)
        val tag = NBTUtil.getOrCreateTag(nmsCopy)
        NBTUtil.set(tag, "EntityTag", idTag)
        NBTUtil.setTag(nmsCopy, tag)

        return getBukkitCopy(nmsCopy)
    }

    @JvmStatic
    fun makeTexturedSkull(texture: String): ItemStack {
        return applySkullTexture(ItemStack(Material.SKULL_ITEM, 1, 3), texture)
    }

    @JvmStatic
    fun applySkullTexture(itemStack: ItemStack, texture: String): ItemStack {

        val skullMeta = (itemStack.itemMeta as SkullMeta)

        if (texture.length < 16) {
            skullMeta.owner = texture
            itemStack.itemMeta = skullMeta
            return itemStack
        }

        val gameProfile = GameProfile(UUID.randomUUID(), null)

        gameProfile.properties.put("textures", Property("textures", texture))

        val field: Field?

        try {
            field = skullMeta.javaClass.getDeclaredField("profile")
            field.isAccessible = true
            field.set(skullMeta, gameProfile)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        itemStack.itemMeta = skullMeta

        return itemStack
    }

    @JvmStatic
    fun isSimilar(first: ItemStack, second: ItemStack): Boolean {
        if (first.type != second.type) {
            return false
        }

        if (first.durability != second.durability) {
            return false
        }

        if (first.hasItemMeta() != second.hasItemMeta()) {
            return false
        }

        if (first.itemMeta.hasDisplayName() != second.itemMeta.hasDisplayName()) {
            return false
        }

        if (first.itemMeta.hasDisplayName()) {
            if (first.itemMeta.displayName != second.itemMeta.displayName) {
                return false
            }
        }

        if (first.itemMeta.hasLore() != second.itemMeta.hasLore()) {
            return false
        }

        return true
    }

    @JvmStatic
    fun hasSameLore(first: ItemStack, second: ItemStack): Boolean {
        if (first.hasItemMeta() != second.hasItemMeta()) {
            return false
        }

        if (!first.hasItemMeta()) {
            return true
        }

        if (first.enchantments.isEmpty() && second.enchantments.isEmpty()) {
            return true
        }

        val firstLore = first.itemMeta.lore!!
        val secondLore = second.itemMeta.lore!!

        if (firstLore.isEmpty() && secondLore.isEmpty()) {
            return true
        }

        if (firstLore.size != secondLore.size) {
            return false
        }

        for (i in firstLore.indices) {
            if (firstLore[i] != secondLore[i]) {
                return false
            }
        }

        return true
    }

    @JvmStatic
    fun hasSameEnchantments(first: ItemStack, second: ItemStack): Boolean {
        if (first.enchantments.isEmpty() && second.enchantments.isEmpty()) {
            return true
        }

        if (first.enchantments.size != second.enchantments.size) {
            return false
        }

        for (enchantment in first.enchantments) {
            if (!second.containsEnchantment(enchantment.key)) {
                return false
            }

            if (enchantment.value != second.getEnchantmentLevel(enchantment.key)) {
                return false
            }
        }

        return true
    }

    @JvmStatic
    fun itemTagHasKey(itemStack: ItemStack, key: String): Boolean {
        val nmsCopy = getNmsCopy(itemStack)

        if (!NBTUtil.hasTag(nmsCopy)) {
            return false
        }

        val tag = NBTUtil.getOrCreateTag(nmsCopy)
        return NBTUtil.hasKey(tag, key)
    }

    @JvmStatic
    fun readItemTagKey(itemStack: ItemStack, key: String): Any {
        val nmsCopy = getNmsCopy(itemStack)

        if (!NBTUtil.hasTag(nmsCopy)) {
            return false
        }

        val tag = NBTUtil.getOrCreateTag(nmsCopy)
        return NBTUtil.get(tag, key)
    }

    @JvmStatic
    fun addToItemTag(itemStack: ItemStack, key: String, value: String, preserve: Boolean = false): ItemStack {
        val nmsCopy = getNmsCopy(itemStack)
        val tag = NBTUtil.getOrCreateTag(nmsCopy)

        NBTUtil.setString(tag, key, value)

        if (preserve) {
            preserveItemNBT(nmsCopy, tag, key)
        }

        NBTUtil.setTag(nmsCopy, tag)

        return getBukkitCopy(nmsCopy)
    }

    @JvmStatic
    fun addToItemTag(itemStack: ItemStack, pairs: List<Pair<String, String>>, preserve: Boolean = false): ItemStack {
        val nmsCopy = getNmsCopy(itemStack)
        val tag = NBTUtil.getOrCreateTag(nmsCopy)

        for (pair in pairs) {
            NBTUtil.setString(tag, pair.first, pair.second)

            if (preserve) {
                preserveItemNBT(nmsCopy, tag, pair.first)
            }
        }

        NBTUtil.setTag(nmsCopy, tag)

        return getBukkitCopy(nmsCopy)
    }

    @JvmStatic
    fun addUUIDToItemTag(itemStack: ItemStack, key: String, uuid: UUID, preserve: Boolean = false): ItemStack {
        val nmsCopy = getNmsCopy(itemStack)
        val tag = NBTUtil.getOrCreateTag(nmsCopy)

        NBTUtil.setUUID(tag, key, uuid)

        if (preserve) {
            preserveItemNBT(nmsCopy, tag, "${key}Most")
            preserveItemNBT(nmsCopy, tag, "${key}Least")
        }

        NBTUtil.setTag(nmsCopy, tag)

        return getBukkitCopy(nmsCopy)
    }

    @JvmStatic
    fun preserveItemNBT(itemStack: ItemStack, key: String): ItemStack {
        val nmsCopy = getNmsCopy(itemStack)
        val tag = NBTUtil.getOrCreateTag(nmsCopy)

        preserveItemNBT(nmsCopy, tag, key)

        return getBukkitCopy(nmsCopy)
    }

    @JvmStatic
    fun preserveItemNBT(item: Any, tag: Any, key: String) {
        if (NBTUtil.hasKey(tag, "PreserveNBT")) {
            val currentKeys = NBTUtil.getString(tag, "PreserveNBT").split(",").toHashSet()
            currentKeys.add(key)

            NBTUtil.setString(tag, "PreserveNBT", currentKeys.joinToString(separator = ","))
        } else {
            NBTUtil.setString(tag, "PreserveNBT", key)
        }

        NBTUtil.setTag(item, tag)
    }

    /**
     * Gets a player head item with the skin of the given [owner].
     */
    @JvmStatic
    fun getPlayerHeadItem(owner: String): ItemStack {
        val itemStack = ItemStack(Material.SKULL_ITEM, 1, 3)
        val itemMeta = itemStack.itemMeta as SkullMeta

        itemMeta.owner = owner
        itemStack.itemMeta = itemMeta

        return itemStack
    }

    private val LEATHER_ARMOR_TYPES = listOf(Material.LEATHER_BOOTS, Material.LEATHER_LEGGINGS, Material.LEATHER_CHESTPLATE, Material.LEATHER_HELMET)

    /**
     * Applies the given [color] to the given [itemStack]'s metadata if the [itemStack] is a leather armor type.
     */
    @JvmStatic
    fun colorLeatherArmor(itemStack: ItemStack, color: Color): ItemStack {
        if (!LEATHER_ARMOR_TYPES.contains(itemStack.type)) {
            throw IllegalArgumentException("Item stack is not of leather armor type!")
        }

        val meta = itemStack.itemMeta as LeatherArmorMeta
        meta.color = color
        itemStack.itemMeta = meta
        return itemStack
    }

    @JvmStatic
    fun getEquipmentSlot(type: Material): EquipmentSlot? {
        return EQUIPMENT_ITEMS[type]
    }

    @JvmStatic
    fun isArmorEquipment(type: Material): Boolean {
        return ARMOR_EQUIPMENT_SLOTS.contains(getEquipmentSlot(type))
    }

    @JvmStatic
    fun getSwords(): List<Material> {
        return SWORDS
    }

    @JvmStatic
    fun isSword(type: Material): Boolean {
        return SWORDS.contains(type)
    }

    @JvmStatic
    fun isSword(item: ItemStack): Boolean {
        return SWORDS.contains(item.type)
    }

}