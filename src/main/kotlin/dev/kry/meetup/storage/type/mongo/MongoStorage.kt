package dev.kry.meetup.storage.type.mongo

import dev.kry.meetup.storage.Storage

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

    override fun reload() {
    }


}