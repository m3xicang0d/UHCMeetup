package dev.mexican.meetup.storage.type.mongo

import com.mongodb.MongoClient
import com.mongodb.MongoClientURI
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoDatabase
import dev.mexican.meetup.config.DatabaseFile
import dev.mexican.meetup.config.SettingsFile
import org.bukkit.Bukkit
import org.bukkit.ChatColor.GREEN
import org.bukkit.ChatColor.RED
import java.io.Closeable
import java.util.*
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
        try {
            Bukkit.getConsoleSender().sendMessage("${GREEN}Loading MongoDB");
            val database = config.getString("MONGO.DATABASE")
            Bukkit.getConsoleSender().sendMessage("${GREEN}Trying connect to database $database...")
            if (config.getBoolean("MONGO.URI-MODE")) {
                val uri = MongoClientURI(config.getString("MONGO.URI.CONNECTION-STR"))
                client = MongoClient(uri)
                Bukkit.getConsoleSender().sendMessage("${GREEN}Successfully connected to database with URI")
            } else {
                val host = config.getString("MONGO.HOST")
                val port = config.getInt("MONGO.PORT")
                val username = config.getString("MONGO.AUTH.USERNAME")
                val password = config.getString("MONGO.AUTH.PASSWORD")
                if (username == null || password == null) {
                    client = MongoClient(ServerAddress(host, port))
                } else {
                    client = MongoClient(
                        ServerAddress(host, port), Collections.singletonList(
                            MongoCredential.createCredential(
                                username,
                                config.getString("MONGO.AUTH.AUTH_DATABASE"),
                                password.toCharArray()
                            )
                        )
                    )
                    Bukkit.getConsoleSender().sendMessage("${GREEN}Successfully connected to database with credentials")
                }
            }
            mongoDatabase = client!!.getDatabase(database)
        } catch (e : Exception) {
            e.printStackTrace()
            Bukkit.getConsoleSender().sendMessage("${RED}Error connecting with mongo");
            exitProcess(0)
        }
    }

    override fun close() {
        if(this.client != null) {
            this.client!!.close();
            Bukkit.getConsoleSender().sendMessage("Mongo says bye :)");
        }
    }
}