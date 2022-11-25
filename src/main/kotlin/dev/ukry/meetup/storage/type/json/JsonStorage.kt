package dev.ukry.meetup.storage.type.json

import com.google.gson.Gson
import com.google.gson.JsonObject
import com.google.gson.JsonParser
import com.mongodb.BasicDBObject
import com.mongodb.util.JSON
import dev.ukry.meetup.Burrito
import dev.ukry.meetup.storage.Storage
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.File.separator

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/


//This use mongo db api
class JsonStorage : Storage {

    private lateinit var file : File
    lateinit var objs : BasicDBObject

    override fun init() {
        file = File(Burrito.getInstance().dataFolder, "data.json")
        objs = if(!file.exists()) {
            file.createNewFile()
            FileUtils.write(file, "{}")
            BasicDBObject()
        } else {
            JSON.parse(FileUtils.readFileToString(file)) as BasicDBObject
        }
    }

    override fun stop() {
        save()
    }

    override fun save() {
        FileUtils.write(file, Gson().toJson(JsonParser().parse(objs.toString())))
    }

    override fun save(key: String, value: JsonObject) {
        objs[key] = value.toString()
    }

    override fun reload() {
    }
}