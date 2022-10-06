package dev.mexican.meetup.game.world.generation.type.map

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.game.world.generation.Generator
import dev.mexican.meetup.game.world.generation.type.map.task.MapGeneratorTask
import net.minecraft.server.v1_8_R3.BiomeBase
import org.bukkit.Bukkit
import java.lang.reflect.Field
import java.lang.reflect.Modifier
import kotlin.system.exitProcess

/**
 * @author UKry
 * Created: 06/10/2022
 * Project UHCMeetup
 **/

class MapGenerator : Generator() {

    override fun preConfiguration() {
        try {
            val clazz = BiomeBase::class.java
            val field = clazz.getDeclaredField("biomes")
            field.isAccessible = true
            val modifiersField = Field::class.java.getDeclaredField("modifiers")
            modifiersField.isAccessible = true
            modifiersField.setInt(field, field.modifiers and Modifier.FINAL.inv())
            @Suppress("UNCHECKED_CAST") val array = field.get(null) as Array<BiomeBase>
            val biomes = BiomeBase.o
            val changes = mutableMapOf<String, BiomeBase>()
            val excludes = mutableSetOf<String>()
            for(s in config.getStringList("GENERATION.REPLACEMENTS")) {
                if(s.contains("=")) {
                    excludes.add(s.replace("=", ""))
                    continue
                }
                val input = s.split("->")
                if(input[0].equals("OTHERS", true)) {
                    val to = biomes[input[1]]
                    if(to == null) {
                        Bukkit.getConsoleSender().sendMessage("Error, ${input[1]} no is biome")
                        continue
                    }
                    for(key in biomes.keys) {
                        if(excludes.contains(key)) {
                            Bukkit.getConsoleSender().sendMessage("Excluding $key")
                            continue
                        }
                        val biome = biomes[key] ?: continue
                        if(changes.containsKey(key)) {
                            continue
                        }
                        changes[key] = to
                        array[biome.id] = to
                    }
                } else {
                    val from = biomes[input[0]]
                    if (from == null) {
                        Bukkit.getConsoleSender().sendMessage("Error, ${input[0]} no is biome")
                        continue
                    }
                    val to = biomes[input[1]]
                    if (to == null) {
                        Bukkit.getConsoleSender().sendMessage("Error, ${input[1]} no is biome")
                        continue
                    }
                    changes[input[0]] = to
                    array[from.id] = to
                }
            }
            changes.forEach { (k, v) ->
                Bukkit.getConsoleSender().sendMessage("Biome change: $k -> ${v.ah}")
            }
            field.set(null, array)
            Bukkit.getConsoleSender().sendMessage("Preconfigurations successful")
        } catch (e : Exception) {
            Bukkit.getConsoleSender().sendMessage("Error with preconfigurations!")
            e.printStackTrace()
            exitProcess(0)
        }
    }

    override fun generateWorld() {
        MapGeneratorTask().runTaskTimer(Burrito.getInstance(), 0L, 20L)
    }

}