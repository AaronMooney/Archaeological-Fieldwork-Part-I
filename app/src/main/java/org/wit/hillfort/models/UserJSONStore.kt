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

    constructor (context: Context) {
        this.context = context
        if (exists(context, JSON_FILE_USERS)) {
            deserialize()
        }
    }

    override fun getUsers(): List<UserModel> {
        return users
    }

    override fun addUser(user: UserModel) {
        user.id = generateRandomId()
        users.add(user)
        serialize()
    }

    override fun updateUser(user: UserModel){
        info("updating")
        var foundUser: UserModel? = users.find { u -> u.id == user.id}
        if (foundUser != null){
            foundUser.name = user.name
            foundUser.email = user.email
            foundUser.password = user.password
            serialize()
        }
    }

    override fun deleteUser(user: UserModel) {
        users.remove(user)
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