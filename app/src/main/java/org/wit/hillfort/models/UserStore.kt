package org.wit.hillfort.models

interface UserStore {
    fun getUsers() : List<UserModel>
    fun addUser(user: UserModel)
    fun updateUser(user: UserModel)
    fun deleteUser(user:UserModel)
}