package com.proyecto.services.database

import com.proyecto.utils.Property
import org.koin.core.annotation.Single
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.coroutine.CoroutineDatabase
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo


@Single
class DatabaseContext {
    private var mongoClient: CoroutineClient
    var mongoDatabase: CoroutineDatabase

    init{
        val property= Property("mongo.properties")
        println(property.getKey("database.uri"))
        mongoClient = KMongo.createClient(property.getKey("database.uri"))
            .coroutine
        mongoDatabase = mongoClient.getDatabase(property.getKey("database.database"))
    }
}
