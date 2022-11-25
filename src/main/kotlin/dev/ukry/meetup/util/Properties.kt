package dev.ukry.meetup.util

import net.minecraft.server.v1_8_R3.DedicatedServer
import net.minecraft.server.v1_8_R3.MinecraftServer

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

object Properties {
    fun savePropertiesFile() {
        (MinecraftServer.getServer() as DedicatedServer).propertyManager.savePropertiesFile()
    }

    fun setServerProperty(property: String, value: Any?) {
        (MinecraftServer.getServer() as DedicatedServer).propertyManager.setProperty(property, value)
    }
}