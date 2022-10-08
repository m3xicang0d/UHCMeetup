package dev.mexican.meetup.lobby.chunk

import org.bukkit.Location
import org.bukkit.World
import org.bukkit.generator.BlockPopulator
import org.bukkit.generator.ChunkGenerator
import java.util.*

/**
 * @author UKry
 * Created: 04/10/2022
 * Project UHCMeetup
 **/

class EmptyChunkGenerator : ChunkGenerator() {

    override fun getDefaultPopulators(world: World?): List<BlockPopulator> {
        return listOf(*arrayOf())
    }

    @Deprecated("Deprecated in Java", ReplaceWith("byteArrayOf(0x10000)"))
    override fun generate(world: World?, random: Random?, x: Int, z: Int): ByteArray {
        return byteArrayOf(0x10000.toByte())
    }

    override fun canSpawn(world: World?, x: Int, z: Int): Boolean {
        return true
    }

    override fun getFixedSpawnLocation(world: World?, random: Random?): Location {
        return Location(world, 0.0, 100.0, 0.0)
    }
}