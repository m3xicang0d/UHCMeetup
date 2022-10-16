package dev.mexican.meetup.storage.type.mongo

import com.mongodb.*
import com.mongodb.client.MongoDatabase
import dev.mexican.meetup.config.DatabaseFile
import dev.mexican.meetup.util.CC
import org.bukkit.Bukkit
import org.bukkit.ChatColor.GREEN
import java.io.Closeable
import java.util.logging.Level
import java.util.logging.Logger
import kotlin.system.exitProcess

/**
 * @author UKry
 * Created: 26/09/2022
 * Project UHCMeetup
 **/

class MongoConnection : Closeable {

    private val config = DatabaseFile.getConfig()
    var client : MongoClient? = null
    lateinit var mongoDatabase : MongoDatabase

    fun init() {
        val mongoLogger = Logger.getLogger("org.mongodb.driver")
        mongoLogger.level = Level.SEVERE // e.g. or Log.WARNING, etc.
        try {
            Bukkit.getConsoleSender().sendMessage("${GREEN}Loading MongoDB")
            val database = config.getString("MONGO.DATABASE")
            CC.log("&7Trying connect to database $database...")
            if (config.getBoolean("MONGO.URI-MODE")) {
                val uri = MongoClientURI(config.getString("MONGO.URI.CONNECTION-STR"))
                client = MongoClient(uri)
                CC.log("&aSuccessfully connected to database with URI")
            } else {
                val host = config.getString("MONGO.HOST")
                val port = config.getInt("MONGO.PORT")
                val username = config.getString("MONGO.AUTH.USERNAME")
                val password = config.getString("MONGO.AUTH.PASSWORD")
                if (username == null || password == null) {
                    client = MongoClient(ServerAddress(host, port))
                } else {
                    client = MongoClient(
                        ServerAddress(host, port), MongoCredential.createCredential(
                            username,
                            config.getString("MONGO.AUTH.AUTH_DATABASE"),
                            password.toCharArray()
                        ),
                        MongoClientOptions.builder().build()
                    )
                    CC.log("&aSuccessfully connected to database with credentials")
                }
            }
            mongoDatabase = client!!.getDatabase(database)
        } catch (e : Exception) {
            e.printStackTrace()
            CC.log("&cError connecting with mongo")
            exitProcess(0)
        }
    }

    override fun close() {
        if(this.client != null) {
            this.client!!.close()
            CC.log("&aMongo says bye :)")
        }
    }
}