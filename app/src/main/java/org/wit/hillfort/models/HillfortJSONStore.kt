package org.wit.hillfort.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.helpers.*
import java.util.*

val JSON_FILE = "hillforts.json"
val gsonBuilder = GsonBuilder().setPrettyPrinting().create()
val listType = object : TypeToken<java.util.ArrayList<HillfortModel>>() {}.type

class HillfortJSONStore : HillfortStore, AnkoLogger {

    val context: Context
    var hillforts = ArrayList<HillfortModel>()

    constructor (context: Context, toVisit: ArrayList<HillfortModel>) {
        this.context = context
        if (exists(context, JSON_FILE)) {
            deserialize()
        } else {
            toVisit.forEach{
                create(it.copy())
            }
        }
        info(toVisit.toString())
    }

    override fun findAll(): ArrayList<HillfortModel> {
        return hillforts
    }

    override fun create(hillfort: HillfortModel) {
        info("creating")
        hillfort.id = generateRandomId()
        hillforts.add(hillfort)
        serialize()
    }


    override fun update(hillfort: HillfortModel) {
        info("updating")
        var foundHillfort: HillfortModel? = hillforts.find { h -> h.id == hillfort.id}
        if (foundHillfort != null){
            foundHillfort.name = hillfort.name
            foundHillfort.description = hillfort.description
            foundHillfort.images = ArrayList(hillfort.images)
            foundHillfort.lat = hillfort.lat
            foundHillfort.lng = hillfort.lng
            foundHillfort.zoom = hillfort.zoom
            foundHillfort.visited = hillfort.visited
            foundHillfort.notes = hillfort.notes
            serialize()
            logAll()
        }
    }

    override fun delete(hillfort: HillfortModel) {
        hillforts.remove(hillfort)
        serialize()
    }

    private fun serialize() {
        info("serializing")
        val jsonString = gsonBuilder.toJson(hillforts, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        info("deserialize")
        val jsonString = read(context, JSON_FILE)
        hillforts = Gson().fromJson(jsonString, listType)
        info(hillforts.toString())
    }

    fun logAll(){
        hillforts.forEach { info("${it}") }
    }

    fun generateRandomId(): Long {
        return Random().nextLong()
    }
}