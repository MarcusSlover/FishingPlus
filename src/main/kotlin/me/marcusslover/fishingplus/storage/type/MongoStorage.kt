/*
 * Copyright (c) 2021.
 * Under GNU General Public License v3.0.
 */

package me.marcusslover.fishingplus.storage.type

import com.mongodb.MongoClient
import com.mongodb.MongoClientOptions
import com.mongodb.MongoCredential
import com.mongodb.ServerAddress
import com.mongodb.client.MongoDatabase
import com.mongodb.client.model.UpdateOptions
import me.marcusslover.fishingplus.FishingPlus
import me.marcusslover.fishingplus.fisher.Fisher
import me.marcusslover.fishingplus.storage.AbstractStorage
import me.marcusslover.fishingplus.storage.StorageCenter.Credential
import me.marcusslover.fishingplus.storage.StorageCenter.Type
import org.bson.Document
import java.util.*


class MongoStorage(plugin: FishingPlus, type: Type, credential: Credential) : AbstractStorage(plugin, type) {

    private var client: MongoClient
    private var db: MongoDatabase

    init {
        val host = this.validate(credential.host, "Host")
        val port = this.validate(credential.port, "Port")
        val user = this.validate(credential.user, "User")
        val database = this.validate(credential.database, "Database")
        val password = this.validate(credential.password, "Password")
        val address = ServerAddress(host, port)

        val mongoCredential = MongoCredential.createCredential(
            user,
            database,
            password.toCharArray()
        )
        client = MongoClient(address, mongoCredential, MongoClientOptions.builder().build())
        db = client.getDatabase(database)

        val collectionExists = db.listCollectionNames().firstOrNull { name -> name == this.collectionName } != null
        if (!collectionExists) {
            this.info("Creating \"${this.collectionName}\" collection...")
            db.createCollection(this.collectionName)
            this.info("Created \"${this.collectionName}\" collection!")
        }
    }

    override fun load(uuid: UUID, name: String): Fisher {
        val collection = db.getCollection(this.collectionName)
        val result = collection.find(Document("uuid", uuid.toString())).first()
        val fisher: Fisher

        if (result == null) {
            fisher = Fisher.empty(uuid, name)
            val json = this.toJson(fisher)
            // Add new user
            collection.insertOne(Document.parse(json.toString()))
        } else {
            fisher = this.fromJson(result.toJson())
        }

        return fisher
    }

    override fun save(fisher: Fisher) {
        val collection = db.getCollection(this.collectionName)
        val filter = Document("uuid", fisher.uuid.toString())

        val json = this.toJson(fisher)
        val document = Document.parse(json.toString())

        val updateOptions = UpdateOptions().upsert(true)
        collection.updateOne(filter, Document("\$set", document), updateOptions)
    }
}