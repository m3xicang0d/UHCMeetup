package dev.ukry.meetup.storage

import dev.ukry.meetup.config.SettingsFile
import dev.ukry.meetup.storage.type.json.JsonStorage
import dev.ukry.meetup.storage.type.mongo.MongoStorage
import dev.ukry.meetup.storage.type.redis.RedisStorage
import dev.ukry.meetup.util.CC
import kotlin.system.exitProcess

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class StorageHandler {
    
    lateinit var storage : Storage

    fun load() {
        val config = SettingsFile.getConfig()
        val storageType = config.getString("STORAGE-TYPE", "JSON")
        storage = when(storageType.uppercase()) {
            "MONGO" -> MongoStorage()
            "JSON" -> JsonStorage()
            "REDIS" -> RedisStorage()
            else -> {
                CC.log("&cError in settings.yml, bad storage type $storageType")
                exitProcess(0)
            }
            //---> This also is only to print before to init <----//
        }.also { CC.log("&aStorage system configured in &e$storageType") }.also { it.init() }
    }
}