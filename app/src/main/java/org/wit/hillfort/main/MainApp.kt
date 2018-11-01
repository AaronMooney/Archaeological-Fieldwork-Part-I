package org.wit.hillfort.main

import android.app.Application
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.wit.hillfort.models.HillfortJSONStore
import org.wit.hillfort.models.HillfortStore

class MainApp : Application(), AnkoLogger {

    lateinit var hillforts: HillfortStore

    override fun onCreate() {
        super.onCreate()
        hillforts = HillfortJSONStore(applicationContext)
        info("Hillfort started")

//        hillforts.create(HillfortModel(name = "IR1041 Rathmoylan, Waterford",description = "the promontory can be described as a triangular area with steep grassy slopes on either flank.",
//            image = "", lat = 52.135, lng = -7.035, zoom = 15f))
//        hillforts.create(HillfortModel(name = "IR0983 Islandikane East/Islandikane South, Waterford",description = "this stack is currently being heavily eroded and is inaccessible from the land.",
//            image = "", lat = 52.132, lng = -7.220, zoom = 15f))

    }
}