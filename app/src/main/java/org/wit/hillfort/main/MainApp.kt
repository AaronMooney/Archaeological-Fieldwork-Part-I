package org.wit.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.models.*

class MainApp : Application(), AnkoLogger {

    lateinit var users: UserStore
    lateinit var hillforts: HillfortStore
    lateinit var currentUser: UserModel

    override fun onCreate() {
        super.onCreate()

        val toVisit = ArrayList<HillfortModel>()
        toVisit.add(
            HillfortModel(name = "IR1041 Rathmoylan, Waterford",description = "the promontory can be described as a triangular area with steep grassy slopes on either flank."
            , lat = 52.135, lng = -7.035, zoom = 15f))
        toVisit.add(
            HillfortModel(name = "IR0983 Islandikane East/Islandikane South, Waterford",description = "this stack is currently being heavily eroded and is inaccessible from the land."
            , lat = 52.132, lng = -7.220, zoom = 15f))

        users = UserJSONStore(applicationContext)
        hillforts = HillfortJSONStore(applicationContext, toVisit)
        info("Hillfort started")
    }
}