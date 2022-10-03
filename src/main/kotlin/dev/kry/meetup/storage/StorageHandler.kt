package dev.kry.meetup.storage

import dev.kry.meetup.UHCMeetup
import dev.kry.meetup.config.SettingsFile
import dev.kry.meetup.storage.type.json.JsonStorage
import dev.kry.meetup.storage.type.mongo.MongoStorage
import dev.kry.meetup.storage.type.redis.RedisStorage
import kotlin.system.exitProcess

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class StorageHandler {

    fun load() {
        val config = SettingsFile.getConfig()
        val storageType = config.getString("STORAGE-TYPE", "JSON")
        UHCMeetup.getInstance().storage = when(storageType.uppercase()) {
            "MONGO" -> MongoStorage()
            "JSON" -> JsonStorage()
            "REDIS" -> RedisStorage()
            else -> {
                println("Error in settings.yml, bad storage type $storageType")
                exitProcess(0)
            }
            //---> This also is only to print before to init <----//
        }.also { println("Storage system configured in $storageType") }.also { it.init() }
    }
}