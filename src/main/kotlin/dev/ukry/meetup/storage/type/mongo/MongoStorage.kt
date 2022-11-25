package dev.ukry.meetup.storage.type.mongo

import com.google.gson.JsonObject
import dev.ukry.meetup.storage.Storage

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class MongoStorage : Storage {

    var connection : MongoConnection? = null

    override fun init() {
        connection = MongoConnection().also { it.init() }
    }

    override fun stop() {
        if(connection != null) {
            connection!!.close()
        }
    }

    override fun save() {
    }

    override fun save(key: String, value: JsonObject) {
        TODO("Not yet implemented")
    }

    override fun reload() {
    }


}