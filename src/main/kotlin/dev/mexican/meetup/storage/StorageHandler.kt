package dev.mexican.meetup.storage

import dev.mexican.meetup.Burrito
import dev.mexican.meetup.config.SettingsFile
import dev.mexican.meetup.storage.type.json.JsonStorage
import dev.mexican.meetup.storage.type.mongo.MongoStorage
import dev.mexican.meetup.storage.type.redis.RedisStorage
import dev.mexican.meetup.util.CC
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
        Burrito.getInstance().storage = when(storageType.uppercase()) {
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