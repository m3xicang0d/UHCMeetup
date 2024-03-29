package dev.ukry.meetup.storage.type.mongo

import com.google.common.base.Preconditions
import com.mongodb.client.MongoCollection
import com.mongodb.client.model.UpdateOptions
import dev.ukry.meetup.Burrito
import org.bson.Document


/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

object Mongo {
    @JvmStatic
    fun getCollection(input : String) : MongoCollection<Document> {
        val storage = Burrito.getInstance().storageHandler.storage
        Preconditions.checkArgument(storage is MongoStorage, "Error, config system no is mongodb")
        return (storage as MongoStorage).connection!!.mongoDatabase.getCollection(input)
    }
    @JvmField
    val UPSERT_OPTIONS = UpdateOptions().upsert(true)
}