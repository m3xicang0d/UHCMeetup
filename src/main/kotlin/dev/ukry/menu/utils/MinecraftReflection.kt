/*
 * Copyright (c) 2020. Joel Evans
 *
 * Use and or redistribution of compiled JAR file and or source code is permitted only if given
 * explicit permission from original author: Joel Evans
 */

package dev.ukry.menu.utils

import org.bukkit.Bukkit
import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.entity.Entity
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import java.lang.reflect.Field
import java.lang.reflect.Method
import java.util.*

object MinecraftReflection {

    private var SERVER_VERSION: Int = -1

    private val GAME_PROFILE_CLASS: Class<*>? = Reflection.getClassSuppressed("com.mojang.authlib.GameProfile")
    private val GAME_PROFILE_CLASS_LEGACY: Class<*>? = Reflection.getClassSuppressed("net.minecraft.util.com.mojang.authlib.GameProfile")

    private val PROPERTY_MAP_CLASS: Class<*>? = Reflection.getClassSuppressed("net.minecraft.util.com.mojang.authlib.properties.PropertyMap")
    private val PROPERTY_MAP_CLASS_LEGACY: Class<*>? = Reflection.getClassSuppressed("com.mojang.authlib.properties.PropertyMap")

    private val PROPERTY_CLASS: Class<*>? = Reflection.getClassSuppressed("net.minecraft.util.com.mojang.authlib.properties.Property")
    private val PROPERTY_CLASS_LEGACY: Class<*>? = Reflection.getClassSuppressed("com.mojang.authlib.properties.Property")
    private val PROPERTY_VALUE_FIELD: Field = Reflection.getDeclaredField((PROPERTY_CLASS ?: PROPERTY_CLASS_LEGACY)!!, "value")!!
    private val PROPERTY_SIGNATURE_FIELD: Field = Reflection.getDeclaredField((PROPERTY_CLASS ?: PROPERTY_CLASS_LEGACY)!!, "signature")!!

    private val PROPERTY_MAP_CONTAINS_KEY_METHOD: Method = Reflection.getMethodSuppressed(getPropertyMapClass(), "containsKey", Any::class.java)!!
    private val PROPERTY_MAP_GET_METHOD: Method = Reflection.getMethodSuppressed(getPropertyMapClass(), "get", Any::class.java)!!
    private val PROPERTY_MAP_PUT_METHOD: Method = Reflection.getMethodSuppressed(getPropertyMapClass(), "put", Any::class.java, Any::class.java)!!

    private val MINECRAFT_SERVER_CLASS: Class<*> = getNMSClass("MinecraftServer")!!
    private val MINECRAFT_SERVER_WORLD_SERVER_FIELD: Field = Reflection.getDeclaredField(MINECRAFT_SERVER_CLASS, "worlds")!!

    private val ENUM_ITEM_SLOT_CLASS: Class<*>? = getNMSClass("EnumItemSlot")
    @JvmStatic val ENUM_ITEM_SLOT_HEAD: Any? = getEnumItemSlot("HEAD")
    @JvmStatic val ENUM_ITEM_SLOT_CHEST: Any? = getEnumItemSlot("CHEST")
    @JvmStatic val ENUM_ITEM_SLOT_LEGS: Any? = getEnumItemSlot("LEGS")
    @JvmStatic val ENUM_ITEM_SLOT_FEET: Any? = getEnumItemSlot("FEET")

    private val ENUM_PARTICLE_CLASS: Class<*>? = getNMSClass("EnumParticle")

    private val ENTITY_HUMAN_CLASS: Class<*> = getNMSClass("EntityHuman")!!
    private val ENTITY_HUMAN_GAME_PROFILE_FIELD: Field? = Reflection.getDeclaredField(ENTITY_HUMAN_CLASS, "bH")

    private val MATH_HELPER_CLASS: Class<*> = getNMSClass("MathHelper")!!
    private val MATH_HELPER_FLOOR_METHOD: Method = Reflection.getDeclaredMethod(MATH_HELPER_CLASS, "floor", Double::class.java)!!

    private val CRAFT_INVENTORY_CUSTOM_CLASS: Class<*> = getCraftBukkitClass("inventory.CraftInventoryCustom")!!

    @JvmStatic
    fun getCraftBukkitVersion(): String {
        return "org.bukkit.craftbukkit." + getVersion() + "."
    }

    @JvmStatic
    fun getNmsVersion(): String {
        return "net.minecraft.server." + getVersion() + "."
    }

    @JvmStatic
    fun getVersion(): String {
        val pkg = Bukkit.getServer().javaClass.getPackage().name
        return pkg.substring(pkg.lastIndexOf(".") + 1)
    }

    @JvmStatic
    fun getMinecraftServer(): Any {
        return Reflection.callMethod(Bukkit.getServer(), "getServer")!!
    }

    @JvmStatic
    fun getWorldServers(): List<*> {
        return MINECRAFT_SERVER_WORLD_SERVER_FIELD.get(getMinecraftServer()) as List<*>
    }

    @JvmStatic
    fun getServerVersion(): Int {
        if (SERVER_VERSION != -1) {
            return SERVER_VERSION
        }

        SERVER_VERSION = when {
            getVersion().startsWith("v1_7_") -> {
                5
            }
            getVersion().startsWith("v1_8_") -> {
                47
            }
            getVersion().startsWith("v1_12_") -> {
                340
            }
            else -> {
                throw IllegalStateException("Unknown server version")
            }
        }

        return SERVER_VERSION
    }

    @JvmStatic
    fun getTPS(): Double {
        return (Reflection.getFieldValue(getMinecraftServer(), "recentTps") as DoubleArray)[0]
    }

    @JvmStatic
    fun getPing(player: Player): Int {
        return Reflection.getDeclaredFieldValue(getHandle(player), "ping") as Int
    }

    @JvmStatic
    fun getHandle(entity: Entity): Any {
        return Reflection.callMethod(entity, "getHandle")!!
    }

    @JvmStatic
    fun getHandle(world: World): Any {
        return Reflection.callMethod(world, "getHandle")!!
    }

    @JvmStatic
    fun getHandle(block: Block): Any {
        return Reflection.callDeclaredMethod(block, "getNMSBlock")!!
    }

    @JvmStatic
    fun getNMSClass(name: String): Class<*>? {
        return Reflection.getClassSuppressed(getNmsVersion() + name)
    }

    @JvmStatic
    fun getCraftBukkitClass(name: String): Class<*>? {
        return Reflection.getClassSuppressed(getCraftBukkitVersion() + name)
    }

    @JvmStatic
    fun getGameProfileClass(): Class<*> {
        return GAME_PROFILE_CLASS ?: GAME_PROFILE_CLASS_LEGACY!!
    }

    @JvmStatic
    fun getPropertyMapClass(): Class<*> {
        return PROPERTY_MAP_CLASS ?: PROPERTY_MAP_CLASS_LEGACY!!
    }

    @JvmStatic
    fun getPropertyClass(): Class<*> {
        return PROPERTY_CLASS ?: PROPERTY_CLASS_LEGACY!!
    }

    @JvmStatic
    fun newGameProfile(uuid: UUID, name: String): Any {
        return Reflection.callConstructor(getGameProfileClass(), uuid, name)!!
    }

    @JvmStatic
    fun getGameProfile(player: Player): Any {
        return ENTITY_HUMAN_GAME_PROFILE_FIELD!!.get(getHandle(player))
    }

    @JvmStatic
    fun setGameProfileProperty(profile: Any, propertyName: String, property: Any) {
        val properties = Reflection.getDeclaredFieldValue(profile, "properties")!!
        PROPERTY_MAP_PUT_METHOD.invoke(properties, propertyName, property)
    }

    @JvmStatic
    fun getGameProfileProperty(profile: Any, propertyName: String): Any {
        val properties = Reflection.getDeclaredFieldValue(profile, "properties")!!
        return (PROPERTY_MAP_GET_METHOD.invoke(properties, propertyName) as Collection<*>).iterator().next()!!
    }

    @JvmStatic
    fun getGameProfileTexture(profile: Any): Pair<String, String> {
        val property = getGameProfileProperty(profile, "textures")
        return Pair(PROPERTY_VALUE_FIELD.get(property) as String, PROPERTY_SIGNATURE_FIELD.get(property) as String)
    }

    @JvmStatic
    fun setGameProfileTexture(profile: Any, textureValue: String, textureSignature: String) {
        val property = Reflection.callDeclaredConstructor(getPropertyClass(), "textures", textureValue, textureSignature)!!
        setGameProfileProperty(profile, "textures", property)
    }

    @JvmStatic
    fun getGameProfileUUID(profile: Any): UUID {
        return Reflection.getDeclaredFieldValue(profile, "id")!! as UUID
    }

    @JvmStatic
    fun getGameProfileName(profile: Any): String {
        return Reflection.getDeclaredFieldValue(profile, "name")!! as String
    }

    @JvmStatic
    fun getEnumItemSlot(name: String): Any? {
        if (ENUM_ITEM_SLOT_CLASS == null) {
            return null
        }
        return Reflection.getEnum(ENUM_ITEM_SLOT_CLASS, name)
    }

    @JvmStatic
    fun getEnumParticle(name: String): Any? {
        if (ENUM_PARTICLE_CLASS == null) {
            return null
        }
        return Reflection.getEnum(ENUM_PARTICLE_CLASS, name)
    }

    @JvmStatic
    fun mathFloor(value: Double): Int {
        return MATH_HELPER_FLOOR_METHOD.invoke(null, value) as Int
    }

    @JvmStatic
    fun isCustomInventory(inventory: Inventory): Boolean {
        return inventory.javaClass == CRAFT_INVENTORY_CUSTOM_CLASS
    }

}