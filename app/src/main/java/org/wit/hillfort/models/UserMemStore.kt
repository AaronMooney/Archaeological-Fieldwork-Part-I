package org.wit.hillfort.models

var lastUserId = 0L

internal fun getUserId(): Long {
    return lastUserId++
}

class UserMemStore : UserStore {

    val users = ArrayList<UserModel>()

    override fun getUsers(): List<UserModel> {
        return users
    }

    override fun addUser(user: UserModel) {
        user.id = getId()
        users.add(user)
    }

    override fun updateUser(user: UserModel){
        var foundUser: UserModel? = users.find { u -> u.id == user.id}
        if (foundUser != null){
            foundUser.name = user.name
            foundUser.email = user.email
            foundUser.password = user.password
        }
    }

    override fun deleteUser(user: UserModel) {
        users.remove(user)
    }
}