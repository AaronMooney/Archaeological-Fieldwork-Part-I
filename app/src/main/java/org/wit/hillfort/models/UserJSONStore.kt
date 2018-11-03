package org.wit.hillfort.models

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.helpers.*
import java.util.*

val JSON_FILE_USERS = "users.json"
val gsonBuilderUser = GsonBuilder().setPrettyPrinting().create()
val listTypeUser = object : TypeToken<java.util.ArrayList<UserModel>>() {}.type

class UserJSONStore : UserStore, AnkoLogger {

    val context: Context
    var users = ArrayList<UserModel>()
    var hillforts = ArrayList<HillfortModel>()

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE_USERS)) {
            deserialize()
        }
    }

    override fun getUsers(): List<UserModel> {
        return users
    }

    override fun findAllHillforts(): ArrayList<HillfortModel> {
        return hillforts
    }

    override fun addUser(user: UserModel, toVisit: ArrayList<HillfortModel>) {
        hillforts = toVisit
        toVisit.forEach {
            it.id = generateRandomId()
        }
        user.id = generateRandomId()
        users.add(user)
        serialize()
    }

    override fun addUser(user: UserModel) {
        user.id = generateRandomId()
        var hillfortsToVisit = ArrayList<HillfortModel>()
        hillfortsToVisit.add(
            HillfortModel(name = "IR1041 Rathmoylan, Waterford",description = "the promontory can be described as a triangular area with steep grassy slopes on either flank."
                , lat = 52.135, lng = -7.035, zoom = 15f))
        hillfortsToVisit.add(
            HillfortModel(name = "IR0983 Islandikane East/Islandikane South, Waterford",description = "this stack is currently being heavily eroded and is inaccessible from the land."
                , lat = 52.132, lng = -7.220, zoom = 15f))
        hillfortsToVisit.forEach {
            it.id = generateRandomId()
        }
        user.hillforts = hillfortsToVisit
        users.add(user)
        serialize()
    }

    override fun updateUser(user: UserModel, hillfort: HillfortModel){
        info("updating")
        var foundUser: UserModel? = users.find { u -> u.id == user.id}
        var foundHillfort: HillfortModel? = user.hillforts.find { h -> h.id == hillfort.id}
        if (foundUser != null && foundHillfort!= null){
            info("blalu")
            foundHillfort.name = hillfort.name
            foundHillfort.description = hillfort.description
            foundHillfort.images = ArrayList(hillfort.images)
            foundHillfort.lat = hillfort.lat
            foundHillfort.lng = hillfort.lng
            foundHillfort.zoom = hillfort.zoom
            foundHillfort.visited = hillfort.visited
            foundHillfort.notes = hillfort.notes
            foundUser.name = user.name
            foundUser.email = user.email
            foundUser.password = user.password
            foundUser.hillforts = user.hillforts
            serialize()
        }
    }

    override fun updateUser(user: UserModel){
        info("updating")
        var foundUser: UserModel? = users.find { u -> u.id == user.id}
        if (foundUser != null) {
            foundUser.name = user.name
            foundUser.email = user.email
            foundUser.password = user.password
            foundUser.hillforts = user.hillforts
            serialize()
        }
    }

    override fun deleteUser(user: UserModel) {
        users.remove(user)
        serialize()
    }

    override fun deleteHillfort(user: UserModel, hillfort: HillfortModel) {
        user.hillforts.remove(hillfort)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilderUser.toJson(users, listTypeUser)
        write(context, JSON_FILE_USERS, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE_USERS)
        users = Gson().fromJson(jsonString, listTypeUser)
    }

    fun generateRandomId(): Long {
        return Random().nextLong()
    }
}