package dev.ukry.api.json

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject

/**
 * @author UKry
 * Created: 27/09/2022
 * Project UHCMeetup
 **/

class JsonChain() {
    private val gson = Gson()
    private var json = JsonObject()

    constructor(`object`: JsonObject) : this() {
        json = `object`
    }

    fun addProperty(property: String, value: String): JsonChain {
        json.addProperty(property, value)
        return this
    }

    fun addProperty(property: String, `object`: JsonObject): JsonChain {
        json.addProperty(property, `object`.asString)
        return this
    }

    fun addProperty(property: String, value: Number): JsonChain {
        json.addProperty(property, value)
        return this
    }

    fun addProperty(property: String, value: Boolean): JsonChain {
        json.addProperty(property, value)
        return this
    }

    fun addProperty(property: String, value: Any): JsonChain {
        json.add(property, gson.toJsonTree(value))
        return this
    }

    fun addProperty(property: String, value: Char): JsonChain {
        json.addProperty(property, value)
        return this
    }

    fun add(property: String, element: JsonElement): JsonChain {
        json.add(property, element)
        return this
    }

    fun get(): JsonObject {
        return json
    }
}